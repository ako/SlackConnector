package testslackconnector.tests;

import ca.szc.configparser.Ini;
import org.junit.Test;
import slackconnector.impl.SlackConnector;
import slackconnector.impl.SlackConnectorException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by ako on 16-6-2016.
 */
public class TestSlackConnector {
    @Test
    public void testPostMessage() throws IOException, SlackConnectorException {
        // get configuration
        Path input = Paths.get(System.getProperty("user.home") + "/.slackconnector.cfg");
        Ini ini = new Ini().read(input);
        Map<String, String> configs = ini.getSections().get("UnitTesting");
        SlackConnector connector = new SlackConnector(configs.get("authToken"));
        connector.postMessage("mx-connectors", "Hi there");
    }
}
