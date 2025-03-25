package edsh.blps.security;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import edsh.blps.entity.User;

import java.util.List;

public class Users {
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<User> user;

    // Геттеры и сеттеры
    public List<User> getUsers() {
        return user;
    }

    public void setUsers(List<User> user) {
        this.user = user;
    }
}