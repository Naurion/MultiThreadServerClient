package Server_part;

import Client_part.Message;

import java.io.*;
import java.net.Socket;

import static Server_part.Server.clientStreams;

public class ServerThread extends Thread {
    private ObjectInputStream messageFromClient = null;
    private ObjectOutputStream messageToClient = null;
    private Socket socket;
    private Message input;
    private UserList users;
    private Message joinMessage = new Message("Server_bot", "Welcome to chat.");

    public ServerThread(Socket socket) {

        //Запускаем нить
        super("Thread num: " + Server.counter++);
        this.socket = socket;
        start();
    }

    public void run() {
        try {
            messageFromClient = new ObjectInputStream(this.socket.getInputStream());
            messageToClient = new ObjectOutputStream(socket.getOutputStream());

            //Добавляем поток вывода для дальнейшей передачи сообщений пользователю
            clientStreams.add(messageToClient);

            //Пока клиент подключен, отправляем ему сообщения
            while (socket.isConnected()) {
                input = (Message) messageFromClient.readObject();
                System.out.println(input.getMessage());
                if (input.getMessage().equals("JOIN")) {
                    messageToClient.writeObject(joinMessage);
                } else {
                    sendMessage();
                }
            }
        } catch (IOException e) {
            System.out.println("Client is disconnected");
            int index = 0;
            for (ObjectOutputStream stream : clientStreams) {
                if (stream.equals(socket)) {
                    clientStreams.remove(index);
                    System.out.println("Client is remove");
                    break;
                } else {
                    index++;
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound");
        }
    }

    //Метод посылает сообщение всем пользователям.
    private void sendMessage() {
        try {
            for (ObjectOutputStream stream : clientStreams) {
                stream.writeObject(input);
            }
        } catch (IOException e) {
            System.out.println("Client is OUT");
        }
    }

}
