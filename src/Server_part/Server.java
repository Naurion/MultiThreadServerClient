package Server_part;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static ArrayList<ServerThread> clients = new ArrayList<>();
    public static int counter;
    public static List<UserList> users = new ArrayList<>();
    public static ChatHistory history = new ChatHistory();



    public static void main(String[] args) throws Exception {
        ServerSocket server = null;
        Socket socket = null;

        server = new ServerSocket(1234);
        System.out.println("Waiting for connection...");
            while(true) {
                socket = server.accept();
                System.out.println("Connected.");
                clients.add(new ServerThread(socket));
            }
    }
}
