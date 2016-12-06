package slackconnector.impl;

import java.io.IOException;

import slackconnector.proxies.Message;

import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.logging.ILogNode;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

/**
 * Created by ako on 16-6-2016.
 */
public class SlackConnector {
    private ILogNode logger;
    private static SlackSession session;

    public SlackConnector (String authToken) throws IOException {
    	session = SlackSessionFactory.createWebSocketSlackSession(authToken);
        session.connect();
    }
    
    /**
     * Post message on specified slack channel
     *
     * @param authToken
     * @param channelName
     * @param message
     * @throws IOException
     * @throws SlackConnectorException
     */
    public void postMessage(String channelName, String message) throws IOException, SlackConnectorException {
        info(String.format("postMessage: %s, %s", channelName, message));
        SlackChannel channel = session.findChannelByName(channelName);
        if (channel == null) {
            info("channel not found");
            throw new SlackConnectorException(String.format("Channel %s not found", channelName));
        }
        info(String.format("Channel: %s", channel.getName()));
        session.sendMessage(channel, message);
        info("done postingMessage");
    }
    
    public void registeringAListener(String authToken) throws IOException
    {
        // first define the listener
        SlackMessagePostedListener messagePostedListener = new SlackMessagePostedListener()
        {
            @Override
            public void onEvent(SlackMessagePosted event, SlackSession session)
            {
                SlackChannel channelOnWhichMessageWasPosted = event.getChannel();
                String messageContent = event.getMessageContent();
                SlackUser messageSender = event.getSender();
                
                Message msg = new Message(Core.createSystemContext());
                msg.setSender(messageSender.getUserName());
                msg.setText(messageContent);
                msg.setChannel(channelOnWhichMessageWasPosted.getName());
                try {
					msg.commit();
				} catch (CoreException e) {
					info("woops big error"+e);
				}
                
                info("Message received: "+messageContent+channelOnWhichMessageWasPosted+messageSender);
            }
        };
        //add it to the session
        session.addMessagePostedListener(messagePostedListener);
        //that's it, the listener will get every message post events the bot can get notified on
        //(IE: the messages sent on channels it joined or sent directly to it)
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
    public void sendDirectMessage(String username, String message) throws IOException, SlackConnectorException {
        info(String.format("sendDirectMessage: %s, %s", username, message));
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
