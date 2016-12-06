package slackconnector.impl;

import com.google.common.collect.ImmutableMap;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.MendixRuntimeException;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

import java.io.IOException;

/**
 * Created by ako on 16-6-2016.
 */
public class SlackConnector {
    public static String LOGNODE = "SlackConnector";
    private static SlackSession session;
    private static String authenticationToken = null;
    private ILogNode logger;

    public SlackConnector(String authToken) throws IOException {
        synchronized (this) {
            if (authenticationToken != null && !authenticationToken.equals(authToken)) {
                throw new SlackConnectorException("The slackconnector does not support multiple sessions");
            } else {
                authenticationToken = authToken;
            }
        }
    }

    private SlackSession getSession() throws IOException {
        synchronized (this) {
            if (session == null) {
                logger.info("Creating new slack session");
                session = SlackSessionFactory.createWebSocketSlackSession(this.authenticationToken);
            }
            if (!session.isConnected()){
                logger.info("Reconnecting slack session");
                session.connect();
            }
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

    /**
     * Register a microflow to be called when a slack message is received
     *
     * @param onMessageMicroflow
     * @throws IOException
     */
    public void registeringAListener(final String onMessageMicroflow) throws IOException {
        // first define the listener
        logger.info(String.format("Registering new slack listener microflow: %s",onMessageMicroflow));
        SlackMessagePostedListener messagePostedListener = (event, session1) -> {
            String mf = onMessageMicroflow;
            try {
                SlackChannel messageChannel = event.getChannel();
                String messageContent = event.getMessageContent();
                SlackUser messageSender = event.getSender();

                logger.info(String.format("Calling onMessage microflow: %s, %s", mf, messageChannel));
                final ImmutableMap map = ImmutableMap.of("Channel", messageChannel, "Sender", messageSender, "Content", messageContent);
                logger.info("Parameter map: " + map);
                Core.execute(Core.createSystemContext(), mf, true, map);
                info(String.format("Message received: %s, %s, %s", messageContent, messageChannel, messageSender));
            } catch (CoreException e) {
                logger.warn(String.format("Failed to call Slack message microflow %s: %s", mf, e.getMessage()));
            }
        };
        //add it to the session
        SlackSession session = getSession();
        session.addMessagePostedListener(messagePostedListener);
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
