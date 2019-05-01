// This file was generated by Mendix Modeler.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package testslackconnector.proxies;

public class SlackMessages
{
	private final com.mendix.systemwideinterfaces.core.IMendixObject slackMessagesMendixObject;

	private final com.mendix.systemwideinterfaces.core.IContext context;

	/**
	 * Internal name of this entity
	 */
	public static final java.lang.String entityName = "TestSlackConnector.SlackMessages";

	/**
	 * Enum describing members of this entity
	 */
	public enum MemberNames
	{
		ChannelName("ChannelName"),
		Message("Message"),
		DirectMessage("DirectMessage"),
		DirectMessageUsername("DirectMessageUsername");

		private java.lang.String metaName;

		MemberNames(java.lang.String s)
		{
			metaName = s;
		}

		@java.lang.Override
		public java.lang.String toString()
		{
			return metaName;
		}
	}

	public SlackMessages(com.mendix.systemwideinterfaces.core.IContext context)
	{
		this(context, com.mendix.core.Core.instantiate(context, "TestSlackConnector.SlackMessages"));
	}

	protected SlackMessages(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject slackMessagesMendixObject)
	{
		if (slackMessagesMendixObject == null)
			throw new java.lang.IllegalArgumentException("The given object cannot be null.");
		if (!com.mendix.core.Core.isSubClassOf("TestSlackConnector.SlackMessages", slackMessagesMendixObject.getType()))
			throw new java.lang.IllegalArgumentException("The given object is not a TestSlackConnector.SlackMessages");

		this.slackMessagesMendixObject = slackMessagesMendixObject;
		this.context = context;
	}

	/**
	 * @deprecated Use 'SlackMessages.load(IContext, IMendixIdentifier)' instead.
	 */
	@java.lang.Deprecated
	public static testslackconnector.proxies.SlackMessages initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		return testslackconnector.proxies.SlackMessages.load(context, mendixIdentifier);
	}

	/**
	 * Initialize a proxy using context (recommended). This context will be used for security checking when the get- and set-methods without context parameters are called.
	 * The get- and set-methods with context parameter should be used when for instance sudo access is necessary (IContext.createSudoClone() can be used to obtain sudo access).
	 */
	public static testslackconnector.proxies.SlackMessages initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject mendixObject)
	{
		return new testslackconnector.proxies.SlackMessages(context, mendixObject);
	}

	public static testslackconnector.proxies.SlackMessages load(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		com.mendix.systemwideinterfaces.core.IMendixObject mendixObject = com.mendix.core.Core.retrieveId(context, mendixIdentifier);
		return testslackconnector.proxies.SlackMessages.initialize(context, mendixObject);
	}

	public static java.util.List<testslackconnector.proxies.SlackMessages> load(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String xpathConstraint) throws com.mendix.core.CoreException
	{
		java.util.List<testslackconnector.proxies.SlackMessages> result = new java.util.ArrayList<testslackconnector.proxies.SlackMessages>();
		for (com.mendix.systemwideinterfaces.core.IMendixObject obj : com.mendix.core.Core.retrieveXPathQuery(context, "//TestSlackConnector.SlackMessages" + xpathConstraint))
			result.add(testslackconnector.proxies.SlackMessages.initialize(context, obj));
		return result;
	}

	/**
	 * Commit the changes made on this proxy object.
	 */
	public final void commit() throws com.mendix.core.CoreException
	{
		com.mendix.core.Core.commit(context, getMendixObject());
	}

	/**
	 * Commit the changes made on this proxy object using the specified context.
	 */
	public final void commit(com.mendix.systemwideinterfaces.core.IContext context) throws com.mendix.core.CoreException
	{
		com.mendix.core.Core.commit(context, getMendixObject());
	}

	/**
	 * Delete the object.
	 */
	public final void delete()
	{
		com.mendix.core.Core.delete(context, getMendixObject());
	}

	/**
	 * Delete the object using the specified context.
	 */
	public final void delete(com.mendix.systemwideinterfaces.core.IContext context)
	{
		com.mendix.core.Core.delete(context, getMendixObject());
	}
	/**
	 * @return value of ChannelName
	 */
	public final java.lang.String getChannelName()
	{
		return getChannelName(getContext());
	}

	/**
	 * @param context
	 * @return value of ChannelName
	 */
	public final java.lang.String getChannelName(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.ChannelName.toString());
	}

	/**
	 * Set value of ChannelName
	 * @param channelname
	 */
	public final void setChannelName(java.lang.String channelname)
	{
		setChannelName(getContext(), channelname);
	}

	/**
	 * Set value of ChannelName
	 * @param context
	 * @param channelname
	 */
	public final void setChannelName(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String channelname)
	{
		getMendixObject().setValue(context, MemberNames.ChannelName.toString(), channelname);
	}

	/**
	 * @return value of Message
	 */
	public final java.lang.String getMessage()
	{
		return getMessage(getContext());
	}

	/**
	 * @param context
	 * @return value of Message
	 */
	public final java.lang.String getMessage(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.Message.toString());
	}

	/**
	 * Set value of Message
	 * @param message
	 */
	public final void setMessage(java.lang.String message)
	{
		setMessage(getContext(), message);
	}

	/**
	 * Set value of Message
	 * @param context
	 * @param message
	 */
	public final void setMessage(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String message)
	{
		getMendixObject().setValue(context, MemberNames.Message.toString(), message);
	}

	/**
	 * @return value of DirectMessage
	 */
	public final java.lang.String getDirectMessage()
	{
		return getDirectMessage(getContext());
	}

	/**
	 * @param context
	 * @return value of DirectMessage
	 */
	public final java.lang.String getDirectMessage(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.DirectMessage.toString());
	}

	/**
	 * Set value of DirectMessage
	 * @param directmessage
	 */
	public final void setDirectMessage(java.lang.String directmessage)
	{
		setDirectMessage(getContext(), directmessage);
	}

	/**
	 * Set value of DirectMessage
	 * @param context
	 * @param directmessage
	 */
	public final void setDirectMessage(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String directmessage)
	{
		getMendixObject().setValue(context, MemberNames.DirectMessage.toString(), directmessage);
	}

	/**
	 * @return value of DirectMessageUsername
	 */
	public final java.lang.String getDirectMessageUsername()
	{
		return getDirectMessageUsername(getContext());
	}

	/**
	 * @param context
	 * @return value of DirectMessageUsername
	 */
	public final java.lang.String getDirectMessageUsername(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.DirectMessageUsername.toString());
	}

	/**
	 * Set value of DirectMessageUsername
	 * @param directmessageusername
	 */
	public final void setDirectMessageUsername(java.lang.String directmessageusername)
	{
		setDirectMessageUsername(getContext(), directmessageusername);
	}

	/**
	 * Set value of DirectMessageUsername
	 * @param context
	 * @param directmessageusername
	 */
	public final void setDirectMessageUsername(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String directmessageusername)
	{
		getMendixObject().setValue(context, MemberNames.DirectMessageUsername.toString(), directmessageusername);
	}

	/**
	 * @return the IMendixObject instance of this proxy for use in the Core interface.
	 */
	public final com.mendix.systemwideinterfaces.core.IMendixObject getMendixObject()
	{
		return slackMessagesMendixObject;
	}

	/**
	 * @return the IContext instance of this proxy, or null if no IContext instance was specified at initialization.
	 */
	public final com.mendix.systemwideinterfaces.core.IContext getContext()
	{
		return context;
	}

	@java.lang.Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;

		if (obj != null && getClass().equals(obj.getClass()))
		{
			final testslackconnector.proxies.SlackMessages that = (testslackconnector.proxies.SlackMessages) obj;
			return getMendixObject().equals(that.getMendixObject());
		}
		return false;
	}

	@java.lang.Override
	public int hashCode()
	{
		return getMendixObject().hashCode();
	}

	/**
	 * @return String name of this class
	 */
	public static java.lang.String getType()
	{
		return "TestSlackConnector.SlackMessages";
	}

	/**
	 * @return String GUID from this object, format: ID_0000000000
	 * @deprecated Use getMendixObject().getId().toLong() to get a unique identifier for this object.
	 */
	@java.lang.Deprecated
	public java.lang.String getGUID()
	{
		return "ID_" + getMendixObject().getId().toLong();
	}
}
