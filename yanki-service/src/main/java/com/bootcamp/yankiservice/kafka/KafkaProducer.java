package com.bootcamp.yankiservice.kafka;

import com.bootcamp.yankiservice.documents.dto.CustomerCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

@Service
public class KafkaProducer {

    private static final String TOPIC= "topic_RegisterUser";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void writeMessage(CustomerCommand customer){

        this.kafkaTemplate.send(TOPIC, customer);
    }
}