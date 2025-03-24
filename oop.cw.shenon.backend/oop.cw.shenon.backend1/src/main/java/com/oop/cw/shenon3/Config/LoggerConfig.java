package com.oop.cw.shenon3.Config;

import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.logging.*;

/**
 * Configuration class for setting up logging in the application.
 *
 * Purpose:
 * - Configures the root logger to log messages to the console, a file, and a custom front-end handler.
 * - Applies consistent formatting for log messages and manages log levels.
 **/

@Configuration
public class LoggerConfig {

/**
 * Configures the root logger with custom logging handlers and settings.
 * File logging uses append mode to preserve previous logs across application restarts.
 * Ensure proper file permissions for `application.log` to avoid unauthorized access.
 **/

    public static void configurelogger(){
        try{
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            for(Handler handler : handlers){
                rootLogger.removeHandler(handler);
            }

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(consoleHandler);

            FileHandler fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger("").addHandler(fileHandler);
            rootLogger.addHandler(fileHandler);

            FrontLogHandler frontLogHandler = new FrontLogHandler();
            frontLogHandler.setFormatter(new UserfriendlyFormatter());
            rootLogger.addHandler(frontLogHandler);

            Logger.getLogger("").setLevel(Level.INFO);
        }catch (IOException e){
            System.out.println("Configuration load failed");
        }
    }

}
