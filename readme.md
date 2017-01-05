# Mendix Slack Connector

## Introduction

This connector enables you to post message to Slack channels and Slack users.

## Usage

You need a authentication token provided by Slack. For development and testing you can find
login token in the Slack documentation: [OAuth Tokens for Testing and Development][1].

[Bot Users][7] describes how to register a new bot user and receive a token for authentication of your app.

### Send message to channel

Example of a microflow that will post a message to a Slack channel:

![Post message to slack channel][3]

Here's how you configure the action:

![Configure post message to slack channel][4]

### Send direct message to user

Example of a microflow that will send a direct message to a Slack user:

![Send direct message to a slack user][5]

Here's how you configure the action:

![Configure send direct message to slack user][6]

### Listener to new messages on a slack channel

Register a listener:

![Register new listener][9]

Microflow to handle new message received:

![On message microflow][10]

### Determine user name for user id

### Determine channel name for channel id

## Development

The sourcecode for this the Mendix Slack Connector can be found on Github: [Mendix SlackConnector][8]

All java jar dependencies are managed using an ivy file. You can download all
dependencies by running runivy.cmd. This will save all jars in the userlib folder. There are two different
scripts to run ivy:
* runivy.cmd - downloads all dependencies required for running and testing the project
* runivy-export.cmd - downloads only the dependencies required for distributing the connector mpk.

Before you start to develop the connector you need to run runivy.cmd. After you validate everything works, run runivy-export.cmd.
This will delete all jars in the userlib folder and only download the jars required for creating the connector mpk.

This connector uses the [Simple Slack api][2] library.

For the unit tests you need to provide an authentication information configuration file $HOME/.slackconnector.cfg:

    [UnitTesting]
    auth_token=XXXX

## License

This connector is licensed under the Apache v2 license.

## Changelog

2016-jun-16 - revision 1.0

 * Send message to channel
 * Send direct message to user

2016-dec-16 - revision 1.1

 * Registener message listener microflow
 * Upgrade to Mendix 6.10.0
 
2017-jan- - revision 1.2

 * Upgrade to Mendix 7.0.0 Beta
 * Get username for user id
 * Get channelname for channel id

  [1]: https://api.slack.com/docs/oauth-test-tokens
  [2]: https://github.com/Ullink/simple-slack-api
  [3]: docs/images/send_to_channel_from_microflow.png
  [4]: docs/images/send_to_channel_configuration.png
  [5]: docs/images/direct_message_microflow.png
  [6]: docs/images/direct_slack_message_configuration.png
  [7]: https://api.slack.com/bot-users
  [8]: https://github.com/ako/SlackConnector
  [9]: docs/images/register-new-slack-listener.png
  [10]: docs/images/on-message-listener-microflow.png
  

