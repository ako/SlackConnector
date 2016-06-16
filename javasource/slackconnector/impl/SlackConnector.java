package slackconnector.impl;

import com.mendix.logging.ILogNode;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.IOException;

/**
 * Created by ako on 16-6-2016.
 */
public class SlackConnector {
    private ILogNode logger;

    /**
     * Post message on specified slack channel
     *
     * @param authToken
     * @param channelName
     * @param message
     * @throws IOException
     * @throws SlackConnectorException
     */
    public void postMessage(String authToken, String channelName, String message) throws IOException, SlackConnectorException {
        info(String.format("postMessage: %s, %s", channelName, message));
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(authToken);
        session.connect();
        SlackChannel channel = session.findChannelByName(channelName);
        if (channel == null) {
            info("channel not found");
            throw new SlackConnectorException(String.format("Channel %s not found", channelName));
        }
        info(String.format("Channel: %s", channel.getName()));
        session.sendMessage(channel, message);
        session.disconnect();
        info("done postingMessage");
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
     * @param authenticationToken
     * @param username
     * @param message
     * @throws IOException
     * @throws SlackConnectorException
     */
    public void sendDirectMessage(String authenticationToken, String username, String message) throws IOException, SlackConnectorException {
        info(String.format("sendDirectMessage: %s, %s", username, message));
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(authenticationToken);
        session.connect();
        SlackUser user = session.findUserByUserName(username);
        if (user == null) {
            info("user not found");
            throw new SlackConnectorException(String.format("User %s not found", username));
        }
        info(String.format("User: %s", user.getRealName()));
        session.sendMessageToUser(user, message, null);
        session.disconnect();
        info("done sendDirectMessage");
    }
}
