package Client_part;

import java.io.*;
import java.net.Socket;

public class Client {
    private static String login = "";
    private static ObjectOutputStream messageToServer = null;
    private static BufferedReader messageFromClient = new BufferedReader(new InputStreamReader(System.in));
    private static Socket socket = null;
    private static Message input;
    private static ObjectInputStream messageFromServer = null;


    public static void main(String[] args) throws IOException {

        //Вводим логин пользователя...
        while (login.equals("")) {
            System.out.println("Input login: ");
            try {
                login = messageFromClient.readLine();
            } catch (IOException e) {
                System.out.println("Input login: ");
            }
        }
        //...и выводим его
        System.out.println("Your login is " + login);


        //Подключаемся к серверу
        try {
            socket = new Socket("localhost", 1234);
        } catch (IOException e) {
            System.out.println("Server_part.Server is close");
        }



        //Приветственное сообщение от пользователя. Создаем экземпляр класса Message.
        messageToServer = new ObjectOutputStream(socket.getOutputStream());
        messageToServer.writeObject(new Message(login, "JOIN"));

        //Создаем новый экземпляр листнера
        Listner listner = new Listner();
        listner.start();

        //Посылаем сообщения, пока подключены к серверу
        while (true) {
            sendMessage();
        }
    }


    //Посылаем сообщение
    private static void sendMessage() {
        try {
            messageToServer.writeObject(new Message(login, messageFromClient.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Вложенный класс, создающий нить для прослушивания сообщений от сервера
    private static class Listner extends Thread {

        @Override
        public void run() {
            try {
                messageFromServer = new ObjectInputStream(socket.getInputStream());
                while (true) {
                    input = (Message) messageFromServer.readObject();
                    System.out.println(input.getTime() + "[" + input.getLogin() + "]: " + input.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
