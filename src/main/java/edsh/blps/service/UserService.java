package edsh.blps.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import edsh.blps.entity.User;
import edsh.blps.dto.UserDTO;
import edsh.blps.security.Users;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username + " not found");
        return user;
    }

    @SneakyThrows
    public User findByUsername(String username) {
        XmlMapper xmlMapper = new XmlMapper();
        Users users = xmlMapper.readValue(new File("users.xml"), Users.class);
        for (User user : users.getUser()) {
            if(user.getUsername().equals(username)) {
                return user;
            }
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
        var token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authenticationProvider.authenticate(token);
        return findByUsername(request.getUsername());
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
