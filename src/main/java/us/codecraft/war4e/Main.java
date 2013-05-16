package us.codecraft.war4e;


import org.apache.commons.cli.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

/**
 * User: cairne
 * Date: 13-5-16
 * Time: 上午5:57
 */
public class Main {

    private static int port = 8080;

    private static String path;

    public static void main(String[] args) {
        try {
            parseArgs(args);
            startJetty();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            System.exit(-1);
        }
    }

    private static void startJetty() throws Exception {
        Server server = new Server(port);
        WebAppContext webapp = new WebAppContext();

        webapp.setContextPath("/");
        webapp.setWar(path);
        server.setHandler(webapp);
        server.start();
        server.join();
    }

    private static void parseArgs(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(new Option("p", "port", true, "port"));
        CommandLineParser commandLineParser = new PosixParser();
        CommandLine commandLine = commandLineParser.parse(options, args);
        readOptions(commandLine);
    }

    private static void readOptions(CommandLine commandLine) {
        if (commandLine.getArgList().size() < 1) {
            System.out.println("Usage: java -jar war3e.jar path-Of-Your-WebApp ");
            System.exit(0);
        }
        path = commandLine.getArgList().get(0).toString();
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("Path \"" + path + "\" not found");
            System.exit(-1);
        }
        if (commandLine.hasOption("p")) {
            port = Integer.parseInt(commandLine.getOptionValue("p"));
        }
    }
}
