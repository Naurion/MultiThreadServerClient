package Client_part;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Message implements Serializable {
    private String login;
    private String message;
    private Date time;
    private String[] users;

    public Message(String login, String message) {
        this.login = login;
        this.message = message;
        this.time = Calendar.getInstance().getTime();
    }
    public Message(String login, String message, String[] users) {
        this.login = login;
        this.message = message;
        this.time = Calendar.getInstance().getTime();
        this.users = users;
    }

    public String getLogin() {
        return login;
    }

    public String getMessage() {
        return message;
    }

    public Date getTime() {
        return time;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }
}
