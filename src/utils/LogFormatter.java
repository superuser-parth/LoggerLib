package utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import model.LogMessage;

public class LogFormatter {
    private final DateTimeFormatter timeStampFormatter;
    
    public LogFormatter(String timeStampFormat){
        this.timeStampFormatter  = DateTimeFormatter.ofPattern(timeStampFormat);
    }

    public String format(LogMessage message){
        String timestamp = timeStampFormatter.format(
            message.getTimestamp().atZone(ZoneId.systemDefault())
        );

        return String.format(
            "%s [%s] [%s] %s",
            message.getLevel(),
            timestamp,
            message.getNamespace(),
            message.getContent()
        );
    }
}
