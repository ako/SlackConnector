package system;

import com.mendix.core.actionmanagement.IActionRegistrator;

public class UserActionsRegistrar
{
  public void registerActions(IActionRegistrator registrator)
  {
    registrator.bundleComponentLoaded();
    registrator.registerUserAction(appcloudservices.actions.GenerateRandomPassword.class);
    registrator.registerUserAction(appcloudservices.actions.LogOutUser.class);
    registrator.registerUserAction(appcloudservices.actions.StartSignOnServlet.class);
    registrator.registerUserAction(slackconnector.actions.GetChannelNameById.class);
    registrator.registerUserAction(slackconnector.actions.GetUserNameById.class);
    registrator.registerUserAction(slackconnector.actions.PostMessageToChannel.class);
    registrator.registerUserAction(slackconnector.actions.RegisterMessageListener.class);
    registrator.registerUserAction(slackconnector.actions.SendDirectMessage.class);
    registrator.registerUserAction(system.actions.VerifyPassword.class);
  }
}
