package edsh.blps.controller;

import edsh.blps.entity.primary.Address;
import edsh.blps.entity.primary.PickPoint;
import jakarta.jms.Queue;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

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
        return (Double) jmsTemplate.receiveAndConvert(responseCalculator);
    }
    public List<PickPoint> getAllPickPoint() {
        jmsTemplate.convertAndSend(requestPickPoint, "");
        return (List<PickPoint>) jmsTemplate.receiveAndConvert(responsePickPoint);
    }
}

