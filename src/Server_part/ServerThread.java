package Server_part;

import Client_part.Message;

import java.io.*;
import java.net.Socket;

import static Server_part.Server.history;
import static Server_part.Server.users;


public class ServerThread extends Thread {
    private ObjectInputStream messageFromClient = null;
    private ObjectOutputStream messageToClient = null;
    private Socket socket;
    private Message input;
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

            //Пока клиент подключен, отправляем ему сообщения
            while (socket.isConnected()) {
                input = (Message) messageFromClient.readObject();
                //Выводим сообщение в консоль сервера
//                System.out.println(input.getMessage());
                if (input.getMessage().equals("JOIN")) {
                    //Добавляем пользователя в список и отправляем приветственное сообщение
                    users.add(new UserList(input.getLogin(), socket, messageFromClient, messageToClient));
                    messageToClient.writeObject(joinMessage);
                    sendHistory();
                } else {
                    history.addMessage(input);
                    sendMessage();
                }
            }
        } catch (IOException e) {
            System.out.println("Client is disconnected");
            //Удаляем пользователя из списка пользователей
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getSocket().equals(socket)) {
                    users.remove(i);
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound");
        }
    }

    //Метод посылает сообщение всем пользователям.
    private void sendMessage() {
        try {
            for (int i = 0; i < users.size(); i++) {
                users.get(i).getMessageToClient().writeObject(input);
            }
        } catch (IOException e) {
            System.out.println("Client is OUT");
        }
    }

    private void sendHistory() {
        try {
            for (Message message : history.getHistory()) {
                messageToClient.writeObject(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
