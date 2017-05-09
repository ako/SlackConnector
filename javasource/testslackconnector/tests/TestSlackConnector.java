package testslackconnector.tests;

import ca.szc.configparser.Ini;
import ca.szc.configparser.exceptions.NoOptionError;
import ca.szc.configparser.exceptions.NoSectionError;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import slackconnector.impl.SlackConnector;
import slackconnector.impl.SlackConnectorException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by ako on 16-6-2016.
 */
public class TestSlackConnector {
    @Test
    public void testPostMessage() throws IOException, SlackConnectorException, NoSectionError, NoOptionError {
        // get configuration
        Path input = Paths.get(System.getProperty("user.home") + "/.slackconnector.cfg");
        Ini ini = new Ini().read(input);
        String auth1 = ini.getValue("UnitTesting", "authToken");
        SlackConnector connector = SlackConnector.getInstance(auth1, null);
        connector.postMessage("mx-connectors", "Hi there");
    }

    @Test
    public void testConcurrentPostMessage() throws IOException, SlackConnectorException, NoSectionError, NoOptionError, InterruptedException {
        // get configuration
        System.err.println("testConcurrentPostMessage");
        Path input = Paths.get(System.getProperty("user.home") + "/.slackconnector.cfg");
        Ini ini = new Ini().read(input);
        String auth1 = ini.getValue("UnitTesting", "authToken");
        Runnable task = () -> {
            try {
                SlackConnector connector = SlackConnector.getInstance(auth1, null);
                connector.postMessage("mx-connectors", "Yo, it's " + (new Date()).toString());
            } catch (Exception e) {
                System.err.print(e.getMessage());
            }
        };
        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0;i<10;i++)
            es.execute(task);
        es.shutdown();
        boolean finshed = es.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    public void testOnMessageListener() throws IOException, SlackConnectorException, NoSectionError, NoOptionError, InterruptedException {
        // get configuration
        Path input = Paths.get(System.getProperty("user.home") + "/.slackconnector.cfg");
        Ini ini = new Ini().read(input);
        String auth1 = ini.getValue("UnitTesting", "authToken");
        SlackConnector connector = SlackConnector.getInstance(auth1, null);
        connector.registeringAListener("myMF");
        Thread.sleep(60000);
    }

    @Test
    public void testOnMessageListenerAndPost() throws IOException, SlackConnectorException, NoSectionError, NoOptionError, InterruptedException {
        // get configuration
        Path input = Paths.get(System.getProperty("user.home") + "/.slackconnector.cfg");
        Ini ini = new Ini().read(input);
        String auth1 = ini.getValue("UnitTesting", "authToken");
        SlackConnector connector1 = SlackConnector.getInstance(auth1, null);
        connector1.postMessage("rekognition", "123");
        SlackConnector connector2 = SlackConnector.getInstance(auth1, null);
        connector2.registeringAListener("myMF");
        SlackConnector connector3 = SlackConnector.getInstance(auth1, null);
        connector3.postMessage("rekognition", "123");
        Thread.sleep(60000);
    }

}
