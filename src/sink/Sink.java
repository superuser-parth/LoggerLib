package sink;

import model.LogMessage;

public interface Sink {
    void writeMessage(LogMessage message);
}
