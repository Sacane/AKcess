package fr.pentagone.akcess.repository.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.util.Arrays;

@Entity(name = "users")
public class User extends AbstractEntity<Integer>{
    private String username;
    private byte[] password;
    private String login;
    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;

    public User(){}

    public User(String username, String login, byte[] password){
        this.username = username;
        this.password = password;
        this.login = login;
    }

    public byte[] getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getUsername() {
        return username;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password=" + Arrays.toString(password) +
                ", login='" + login + '\'' +
                '}';
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
