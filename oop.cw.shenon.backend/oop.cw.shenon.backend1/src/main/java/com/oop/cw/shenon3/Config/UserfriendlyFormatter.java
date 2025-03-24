package com.oop.cw.shenon3.Config;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Custom log formatter to provide a user-friendly log message format.
 * Formats log records in a simplified and readable format.
 * Maps Java logging levels (e.g., `SEVERE`, `WARNING`, `INFO`) to custom labels.
 * Generates a formatted string containing the log level and message.**/

public class UserfriendlyFormatter extends Formatter {

/**
 * Formats a log record into a user-friendly string.
 *
 * @param record The log record to format, which contains information such as the log message and level.
 * @return A formatted string in the format `[LEVEL] message`.**/

    @Override
    public String format(LogRecord record){
        String message = record.getMessage();
        String level = record.getLevel().getName();

        switch(level){
           case "WARNING":
               level = "WARNING";
               break;
           case "SEVERE":
               level = "ERROR";
               break;
           case "INFO":
               level = "INFO";
               break;
           default:
               level = "LOG";
        }
        return String.format("[%s] %s",level,message);
    }
}
