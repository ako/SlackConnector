package slackconnector.impl;

import com.google.common.collect.ImmutableMap;
import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by ako on 16-6-2016.
 */
public class SlackConnector {
    public static String LOGNODE = "SlackConnector";
    private String authenticationToken = null;
    private SlackSession session = null;
    private ILogNode logger;
    private static final SlackConnector instance = new SlackConnector();


    public static SlackConnector getInstance(String authToken, ILogNode logger) throws IOException {
        synchronized (instance) {
            if (instance.authenticationToken == null) {
                if (authToken == null) {
                    throw new SlackConnectorException("Authentication token cannot be empty");
                } else {
                    instance.logger = logger;
                    instance.authenticationToken = authToken;
                }
            }
            if (instance.authenticationToken != null && !instance.authenticationToken.equals(authToken)) {
                throw new SlackConnectorException("The slackconnector does not support multiple sessions");
            }
        }
        return instance;
    }

    private SlackConnector() {
    }


    private SlackSession getSession() throws IOException {
        try {
            synchronized (instance) {
                if (session == null) {
                    info("Creating new slack session for auth " + this.authenticationToken);
                    //session = SlackSessionFactory.createWebSocketSlackSession(this.authenticationToken,5,TimeUnit.SECONDS);
                    session = SlackSessionFactory.createWebSocketSlackSession(this.authenticationToken);
                    session.setHeartbeat(30, TimeUnit.SECONDS);
//                    session.addSlackConnectedListener((slackConnected, slackSession) -> {
//                        info(String.format("Slack connected listener: %s, %s", slackConnected.getConnectedPersona().getUserName(), slackSession.isConnected()));
//                    });
                    session.connect();
                }
                info("Using session: " + session.toString() + ", is connected: " + session.isConnected());
                if (!session.isConnected()) {
                    info("Reconnecting slack session");
                    try {
                        session.connect();
                    } catch (Exception e) {
                        warn(String.format("Failed to reconnect slack session: %s %s", e.toString(), e.getMessage()));
                    }
                }
//                session.getBots().forEach(bot -> {
//                    info(String.format("Bot found: %s", bot.getUserName()));
//                });
            }
        } catch (Exception e) {
            info(String.format("getSession failed: %s", e.getMessage()));
            throw e;
        }
        return session;
    }

    /**
     * Post message on specified slack channel
     *
     * @param channelName
     * @param message
     * @throws IOException
     * @throws SlackConnectorException
     */
    public void postMessage(String channelName, String message) throws IOException, SlackConnectorException {
        info(String.format("postMessage: %s, %s", channelName, message));
        SlackSession session = getSession();
        SlackChannel channel = session.findChannelByName(channelName);
        if (channel == null) {
            info("channel not found");
            throw new SlackConnectorException(String.format("Channel %s not found", channelName));
        }
        info(String.format("Channel: %s", channel.getName()));
        session.sendMessage(channel, message);
        info("done postingMessage");
    }

    public String getChannelName(String channelId) throws IOException {
        SlackSession session = getSession();
        return session.findChannelById(channelId).getName();
    }

    public String getUsername(String userId) throws IOException {
        SlackSession session = getSession();
        return session.findUserById(userId).getUserName();
    }

    /**
     * Register a microflow to be called when a slack message is received
     *
     * @param onMessageMicroflow
     * @throws IOException
     */
    public void registeringAListener(final String onMessageMicroflow) throws IOException {
        // first define the listener
        info(String.format("Registering new slack listener microflow: %s", onMessageMicroflow));
        String instanceIndex = System.getenv("CF_INSTANCE_INDEX");
        info(String.format("Running slack listener on instance: %s", instanceIndex));
        SlackMessagePostedListener messagePostedListener = (event, session1) -> {
            info(String.format("SlackMessagePostedListener: %s", event.getJsonSource()));
            String mf = onMessageMicroflow;
            try {
                SlackChannel messageChannel = event.getChannel();
                String messageContent = event.getMessageContent();
                SlackUser messageSender = event.getSender();

                Date ts = new Date(new BigDecimal(event.getTimestamp()).multiply(new BigDecimal(1000)).longValue());

                final ImmutableMap map = ImmutableMap.of(
                        "ChannelId", messageChannel.getId(),
                        "SenderId", messageSender.getId(),
                        "Content", messageContent, "Timestamp", ts, "EventJson", event.getJsonSource());
                info("Parameter map: " + map);
                Core.executeAsync(Core.createSystemContext(), mf, true, map);
                info(String.format("Message received: %s, %s, %s", messageContent, messageChannel, messageSender));
            } catch (Exception e) {
                warn(String.format("Failed to call Slack message microflow %s: %s", mf, e.getMessage()));
            }
        };
        //add it to the session
        SlackSession session = getSession();
        session.addMessagePostedListener(messagePostedListener);
        //session.setHeartbeat(10, TimeUnit.SECONDS);
        info(String.format("Done registering new slack listener microflow: %s", onMessageMicroflow));
    }

    public void setLogger(ILogNode logger) {
        this.logger = logger;
    }

    private void info(String message) {
        if (logger != null) {
            logger.info(message);
        } else {
            System.err.println(message);
        }
    }

    private void warn(String message) {
        if (logger != null) {
            logger.warn(message);
        } else {
            System.err.println(message);
        }
    }

    /**
     * Send a direct message to a slack user
     *
     * @param username
     * @param message
     * @throws IOException
     * @throws SlackConnectorException
     */
    public void sendDirectMessage(String username, String message) throws IOException, SlackConnectorException {
        info(String.format("sendDirectMessage: %s, %s", username, message));
        SlackSession session = getSession();

        SlackUser user = session.findUserByUserName(username);
        if (user == null) {
            info("user not found");
            throw new SlackConnectorException(String.format("User %s not found", username));
        }
        info(String.format("User: %s", user.getRealName()));
        session.sendMessageToUser(user, message, null);
        info("done sendDirectMessage");
    }
}
