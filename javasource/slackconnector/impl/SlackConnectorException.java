package slackconnector.impl;

import com.mendix.systemwideinterfaces.MendixRuntimeException;

/**
 * Created by ako on 16-6-2016.
 */
public class SlackConnectorException extends MendixRuntimeException {
    public SlackConnectorException(String message) {
        super(message);
    }
}
