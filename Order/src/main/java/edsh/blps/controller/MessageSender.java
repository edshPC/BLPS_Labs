package edsh.blps.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edsh.blps.dto.PickPointDTO;
import jakarta.jms.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.jms.core.JmsTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class MessageSender {

    private final JmsTemplate jmsTemplate;

    public MessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    @Autowired
    private Queue requestCalculator;
    @Autowired
    private Queue responseCalculator;
    @Autowired
    private Queue requestPickPoint;
    @Autowired
    private Queue responsePickPoint;
    public Double sendCalculator(String message) {
        jmsTemplate.convertAndSend(requestCalculator, message);
        Double b= (Double) jmsTemplate.receiveAndConvert(responseCalculator);
        return b;
    }
    public List<PickPointDTO> getAllPickPoint() throws IOException {
        jmsTemplate.convertAndSend(requestPickPoint, " е");
        Object o = jmsTemplate.receiveAndConvert(responsePickPoint);
        if (o instanceof String) {
            String jsonString = (String) o;
            System.out.println(jsonString);

            ObjectMapper objectMapper = new ObjectMapper();
            List<PickPointDTO> responseList = objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, PickPointDTO.class));
            return responseList;
        } else {
            throw new IOException("Received message is not a String");
        }
    }
}

