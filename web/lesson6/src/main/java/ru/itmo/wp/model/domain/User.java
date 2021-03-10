package ru.itmo.wp.model.domain;

import java.io.Serializable;
import java.util.Date;

public class User extends Entity {
    private String login;
    private String email;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
