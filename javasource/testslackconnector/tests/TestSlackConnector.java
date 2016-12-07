package testslackconnector.tests;

import ca.szc.configparser.Ini;
import ca.szc.configparser.exceptions.NoOptionError;
import ca.szc.configparser.exceptions.NoSectionError;
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
    public void testPostMessage() throws IOException, SlackConnectorException, NoSectionError, NoOptionError {
        // get configuration
        Path input = Paths.get(System.getProperty("user.home") + "/.slackconnector.cfg");
        Ini ini = new Ini().read(input);
        String auth1 = ini.getValue("UnitTesting","authToken");
        SlackConnector connector = new SlackConnector(auth1);
        connector.postMessage("mx-connectors", "Hi there");
    }

    @Test
    public void testOnMessageListener() throws IOException, SlackConnectorException, NoSectionError, NoOptionError, InterruptedException {
        // get configuration
        Path input = Paths.get(System.getProperty("user.home") + "/.slackconnector.cfg");
        Ini ini = new Ini().read(input);
        String auth1 = ini.getValue("UnitTesting","authToken");
        SlackConnector connector = new SlackConnector(auth1);
        connector.registeringAListener("myMF");
        Thread.sleep(60000);
    }
}
