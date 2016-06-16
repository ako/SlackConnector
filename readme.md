# Mendix Slack connector

## Introduction

This connector enables you to post message to Slack channels and Slack users.

## Usage

You need a authentication token provided by Slack. For development and testing you can find
login token in the Slack documentation: [OAuth Tokens for Testing and Development][1]

### Send message to channel

![][3]

![][4]

### Send direct message to user

![][5]

![][6]

## Development

Development of this connector is done through a git project on Github.

All java jar dependencies are managed using an ivy file. You can download all
dependencies by running runivy.cmd. This will save all jars in the userlib folder.

This connector uses the [simple slack api][2] library.

For the unit tests you need to provide an authentication information configuration file $HOME/.slackconnector.cfg:

    [UnitTesting]
    auth_token=XXXX

## License

This connector is licensed under the Apache v2 license.

## Changelog

2016-jun-16 - revision 1.0-snapshot

* Send message to channel
* Send direct message to user

  [1]: https://api.slack.com/docs/oauth-test-tokens
  [2]: https://github.com/Ullink/simple-slack-api
  [3]: docs/images/send_to_channel_from_microflow.png
  [4]: docs/images/send_to_channel_configuration.png
  [5]: docs/images/direct_message_microflow.png
  [6]: docs/images/direct_slack_message_configuration.png

