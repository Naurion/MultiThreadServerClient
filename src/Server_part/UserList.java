package Server_part;

import Client_part.Client;

import java.util.List;

public class UserList {
    List<ServerThread> users;

    public void addUser(ServerThread user) {
        this.users.add(user);
    }

    public List<ServerThread> getUsers() {
        return this.users;
    }

}
