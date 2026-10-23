package model;

import java.util.Map;

import enums.LogLevel;
import lombok.Getter;
import lombok.Setter;
import sink.Sink;

@Getter 
@Setter 
public class LoggerConfig {
    private final String timestampFormat;
    private final Map<LogLevel, Sink> sink;

    public LoggerConfig( String timestampFormat, Map<LogLevel, Sink> sink){
        this.timestampFormat = timestampFormat;
        this.sink = sink;
    }
}
