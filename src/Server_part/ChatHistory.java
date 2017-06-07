package Server_part;

import Client_part.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatHistory implements Serializable {

    List<Message> history;

    public ChatHistory() {
        this.history = new ArrayList<>(10);
    }

    public void addMessage(Message message) {
        if (history.size() > 10) {
            history.remove(0);
        }
        this.history.add(message);
    }

    public List<Message> getHistory() {
        return this.history;
    }
}
