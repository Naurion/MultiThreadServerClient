package Server_part;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserList {
    private ObjectInputStream messageFromClient = null;
    private ObjectOutputStream messageToClient = null;
    private Socket socket;
    private String login;

    public UserList(String login, Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.login = login;
        this.socket = socket;
        this.messageFromClient = inputStream;
        this.messageToClient = outputStream;
    }

    public ObjectInputStream getMessageFromClient() {
        return messageFromClient;
    }

    public ObjectOutputStream getMessageToClient() {
        return messageToClient;
    }

    public Socket getSocket() {
        return socket;
    }
}
