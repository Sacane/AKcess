package fr.pentagone.akcess.repository.sql;

import jakarta.persistence.Entity;

@Entity(name = "users")
public class User extends AbstractEntity<Integer>{
    private String username;
    private byte[] password;
    private String login;

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
}
