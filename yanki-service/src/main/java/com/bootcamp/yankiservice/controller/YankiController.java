package com.bootcamp.yankiservice.controller;

import com.bootcamp.yankiservice.documents.dto.CustomerCommand;
import com.bootcamp.yankiservice.kafka.KafkaProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
public class YankiController {

    private final KafkaProducer producer;

    public YankiController(KafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/publish")
    public void writeMessageToTopic(){
        CustomerCommand c = CustomerCommand.builder()
                .name("Francisco C. Paredes")
                .identityNumber("71710442")
                .identityType("DNI")
                .phoneNumber("926910444")
                .build();
        this.producer.writeMessage(c);
    }

}