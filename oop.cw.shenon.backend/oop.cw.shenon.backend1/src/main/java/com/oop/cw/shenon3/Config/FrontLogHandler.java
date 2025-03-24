package com.oop.cw.shenon3.Config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * A custom logging handler that captures log messages and stores them in a thread-safe blocking queue.
 * This handler is designed to facilitate real-time retrieval of log messages, particularly for front-end applications.
 **/
public class FrontLogHandler extends Handler {
    private static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();

/**
 * Publishes a log record by formatting it and adding it to the log queue.
 *
 * @param record The log record to be published. If null, the method will not perform any action.
 *
 * Notes:
 * - Uses the handler's formatter to format the log message.
 * - Adds the formatted log message to a blocking queue for asynchronous consumption.
 **/

    @Override
    public void publish(LogRecord record) {
        if (record != null) {
            String message = getFormatter().format(record);
            logQueue.offer(message);
        }
    }

/**
 * Flushes any buffered output (if applicable).
 **/

    @Override
    public void flush() {}

/**
 * Closes the handler and clears the log queue.
 *
 * @throws SecurityException If the handler is not allowed to be closed due to security restrictions.
 **/

    @Override
    public void close() throws SecurityException {
        logQueue.clear();
    }
    public static BlockingQueue<String> getLogqueue() {return logQueue;}
}
