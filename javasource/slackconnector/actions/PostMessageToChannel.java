// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package slackconnector.actions;

import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import slackconnector.impl.SlackConnector;

public class PostMessageToChannel extends CustomJavaAction<java.lang.Boolean>
{
	private java.lang.String AuthenticationToken;
	private java.lang.String ChannelName;
	private java.lang.String SlackMessage;

	public PostMessageToChannel(IContext context, java.lang.String AuthenticationToken, java.lang.String ChannelName, java.lang.String SlackMessage)
	{
		super(context);
		this.AuthenticationToken = AuthenticationToken;
		this.ChannelName = ChannelName;
		this.SlackMessage = SlackMessage;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		// BEGIN USER CODE
        try {
            SlackConnector connector = SlackConnector.getInstance(this.AuthenticationToken, logger);
            connector.postMessage(this.ChannelName, this.SlackMessage);
        } catch (Exception e) {
            logger.info(String.format("Failed to post message: %s", e.getMessage()));
            throw e;
        }
        return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "PostMessageToChannel";
	}

	// BEGIN EXTRA CODE
    private ILogNode logger = Core.getLogger(SlackConnector.LOGNODE);
	// END EXTRA CODE
}
