package edsh.blps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edsh.blps.dto.AddressDTO;
import edsh.blps.dto.PickPointDTO;
import edsh.blps.entity.primary.PickPoint;
import edsh.blps.service.DeliveryService;
import edsh.blps.service.PickPointService;
import edsh.blps.service.WarehouseService;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@TransactionManagement(value= TransactionManagementType.BEAN)
public class MessageListener {

    @Autowired
    private JmsTemplate jmsTemplate;
    private final DeliveryService deliveryService;
    private final PickPointService pickPointService;
    @Autowired
    private Queue responseCalculator;
    @Autowired
    private Queue responsePickPoint;


    @JmsListener(destination = "request.calculator")
    public void receiveMessage(String address) {
        System.out.println("Received message: " + address);
        // Обработка сообщения
        jmsTemplate.convertAndSend(responseCalculator, deliveryService.calculatePrice(address));
    }
    @JmsListener(destination = "request.PickPoint")
    public void receivePickPoint(String message) throws JsonProcessingException {
        //System.out.println("Received message: " + address);
        // Обработка сообщения
        List<PickPoint> pickPointList=  pickPointService.getAllPickPoints();
        List<PickPointDTO> pickPointDTOS = new ArrayList<>();
        for(PickPoint i: pickPointList){
            pickPointDTOS.add(new PickPointDTO(i.getId(), new AddressDTO(i.getAddress().getId(),i.getAddress().getAddress(),i.getAddress().getLatitude(),i.getAddress().getLongitude())));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(pickPointDTOS);
        System.out.println(pickPointDTOS);
        jmsTemplate.convertAndSend(responsePickPoint,jsonResponse);
    }
}

