package sink;

import model.LogMessage;
import utils.LogFormatter;

public class ConsoleSink implements Sink{
    LogFormatter formatter;

    public ConsoleSink(LogFormatter formatter){
        this.formatter = formatter;
    }   
    @Override 
    public void writeMessage(LogMessage message){
        System.out.println(formatter.format(message));
    }
}
