package fr.pentagone.akcess.repository.sql;

import jakarta.persistence.Entity;

@Entity
public class Manager extends AbstractEntity<Integer>{

    public Manager(){}

    private String name;
    private String login;
    private byte[] password;

    public Manager(String name, String login, byte[] password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }
}
