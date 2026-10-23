package model;

import java.time.Instant;

import enums.LogLevel;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class LogMessage {
    private final String content;
    private final LogLevel level;
    private final String namespace;   
    private final Instant timestamp;

    public LogMessage(String content, LogLevel level, String namespace){
        this.content = content;
        this.level = level;
        this.namespace = namespace;
        this.timestamp = Instant.now();
    }
}
