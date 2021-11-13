package com.bootcamp.yankiservice.kafka;

import com.bootcamp.yankiservice.documents.dto.CustomerCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;


import java.util.Map;

@Slf4j
public class CustomerCommandSerializer implements Serializer<CustomerCommand> {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, CustomerCommand data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            log.error("Unable to serialize object {}", data, e);
            return null;
        }
    }

    @Override
    public void close() {
    }
}
