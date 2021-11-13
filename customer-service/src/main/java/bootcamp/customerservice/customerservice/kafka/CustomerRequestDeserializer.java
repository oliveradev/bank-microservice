package bootcamp.customerservice.customerservice.kafka;

import bootcamp.customerservice.customerservice.documents.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

@Slf4j
public class CustomerRequestDeserializer  implements Deserializer<Customer> {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Customer deserialize(String topic, byte[] data) {
        try {
            objectMapper.findAndRegisterModules();
            return objectMapper.readValue(new String(data, "UTF-8"), Customer.class);
        } catch (Exception e) {
            log.error("Unable to deserialize message {}", data, e);
            return null;
        }
    }

    @Override
    public void close() {
    }
}
