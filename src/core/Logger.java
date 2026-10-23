package core;

import enums.LogLevel;
import model.LogMessage;
import model.LoggerConfig;
import sink.Sink;

public class Logger {
    private final LoggerConfig config;

    public Logger(LoggerConfig config){
        this.config = config;
    }

    public void log(LogLevel level, String content, String namespace){
        Sink sink = config.getSink().get(level);

        if(sink == null){
            throw new IllegalArgumentException("Invalid Sink");
        }

        LogMessage message = new LogMessage(content, level, namespace);

        sink.writeMessage(message);
        
    }
}
