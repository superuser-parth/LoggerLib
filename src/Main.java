import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import config.FileSinkConfig;
import core.Logger;
import enums.LogLevel;
import model.LogMessage;
import model.LoggerConfig;
import sink.ConsoleSink;
import sink.FileSink;
import sink.Sink;
import utils.LogFormatter;

public class Main {
    public static void main(String[] args) throws IOException{
        LogFormatter formatter = new LogFormatter("dd:MM:yyyy HH:mm:ss");
        
        FileSinkConfig fileConfig = new FileSinkConfig(500, 3 );

        Sink fileSink = new FileSink(fileConfig, formatter);

        Sink consoleSink = new ConsoleSink(formatter);


        Map<LogLevel, Sink> sinks = new EnumMap<>(LogLevel.class);


        sinks.put(LogLevel.DEBUG, consoleSink);
        sinks.put(LogLevel.INFO, fileSink);
        sinks.put(LogLevel.WARN, fileSink);
        sinks.put(LogLevel.ERROR, fileSink);    
        sinks.put(LogLevel.FATAL, fileSink);

        LoggerConfig config = new LoggerConfig("dd:mm:yyyy HH:mm:ss", sinks);

        Logger logger = new Logger(config);
        
        logger.log(LogLevel.INFO, "Application Started", "Main");
        logger.log(LogLevel.WARN, "Database connection taking longer than expected", "Database");
        logger.log(LogLevel.ERROR, "Connection pool failed", "ConnectionPool");
        logger.log(LogLevel.FATAL, "Critical failure", "Application");

        logger.log(LogLevel.DEBUG, "Application reached method A", "A");

        for(int i=0;i<400;i++){
            logger.log(LogLevel.INFO, "Application Started", "Main");
            logger.log(LogLevel.WARN, "Database connection taking longer than expected", "Database");
            logger.log(LogLevel.ERROR, "Connection pool failed", "ConnectionPool");
            logger.log(LogLevel.FATAL, "Critical failure", "Application");
        }
    }
}
















