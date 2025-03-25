package edsh.blps.security;

import edsh.blps.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class XmlUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            File file = new File("users.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder =documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("user");
            for(int temp = 0; temp < nodeList.getLength(); temp++){
                Element element = (Element) nodeList.item(temp);

                if(element.getAttributeNode("username").equals(username)){
                    String password = element.getAttribute("password");
                    String roles = element.getAttribute("roles");
                    String telephone = element.getAttribute("telephone");
                    List<String> roleList = new ArrayList<>();
                    for(String role : roles.split(",")){
                        roleList.add(role.trim());
                    }
                    return new User(username,password,telephone,roleList);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}