package org.example.order_service.security;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;
import org.example.order_service.entity.primary.User;

import java.util.List;

@Data
public class Users {
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<User> user;
}