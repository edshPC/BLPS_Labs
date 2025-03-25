package edsh.blps.service;

import edsh.blps.entity.User;
import edsh.blps.dto.UserDTO;
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
            File file = new File("users.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("user");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Element element = (Element) nodeList.item(temp);
                if (element.getAttributeNode("username").equals(username)) {
                    String password = element.getAttribute("password");
                    String roles = element.getAttribute("roles");
                    String telephone = element.getAttribute("telephone");
                    List<String> roleList = new ArrayList<>();
                    for (String role : roles.split(",")) {
                        roleList.add(role.trim());
                    }
                    return new User(username, password, telephone, roleList);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
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

            Element root = doc.getDocumentElement();

            Element userElement = doc.createElement("user");
            userElement.setAttribute("username", user.getUsername());
            userElement.setAttribute("password", user.getPassword());
            List<String> roles = user.getRoles();
            StringBuilder rolesList = new StringBuilder();

            for(int i=0; i<roles.size(); i++){
                if(i!=0){
                    rolesList.append(",");
                }
                rolesList.append(roles.get(i));
            }
            userElement.setAttribute("roles", rolesList.toString());
            userElement.setAttribute("telephone",user.getTelephone());

            root.appendChild(userElement);

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
