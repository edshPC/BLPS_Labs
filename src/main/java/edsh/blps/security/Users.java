package edsh.blps.security;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import edsh.blps.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class Users {
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<User> user;
}