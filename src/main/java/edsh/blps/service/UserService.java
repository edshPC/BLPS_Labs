package edsh.blps.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import edsh.blps.entity.User;
import edsh.blps.dto.UserDTO;
import edsh.blps.security.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            Users users = xmlMapper.readValue(new File("users.xml"),Users.class);
            for (User user : users.getUsers()) {
                if(user.getUsername().equals(username)) {
                    return new User(user.getUsername(), user.getPassword(), user.getTelephone(), user.getRoles());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void save(User user) {
        try {
            File file = new File("users.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            if (file.exists()) {
                doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();
            } else {
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("users");
                doc.appendChild(rootElement);
            }

            Element userElement = doc.createElement("user");
            userElement.setAttribute("username", user.getUsername());
            userElement.setAttribute("password", user.getPassword());
            userElement.setAttribute("telephone", user.getTelephone());
            Element rolesElement = doc.createElement("roles");
            List<String> roles = user.getRoles();
            for (String role : roles) {
                Element roleElement = doc.createElement("role");
                roleElement.appendChild(doc.createTextNode(role));
                rolesElement.appendChild(roleElement);
            }
            userElement.appendChild(rolesElement);
            userElement.setAttribute("telephone",user.getTelephone());
            doc.getDocumentElement().appendChild(userElement);


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User login(UserDTO request) {
        User user = findByUsername(request.getUsername());
        if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return user;
        }
        throw new IllegalArgumentException("Wrong password");
    }

    public User register(UserDTO request) {
        if (findByUsername(request.getUsername())!=null) {
            throw new IllegalArgumentException("User already exists");
        }
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .telephone(request.getTelephone())
                .roles(roles)
                .build();
        save(user);
        return user;
    }

}
