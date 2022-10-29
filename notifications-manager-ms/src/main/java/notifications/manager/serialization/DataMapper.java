package notifications.manager.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class DataMapper {

    private final ObjectMapper objectMapper;

    public DataMapper() {
        objectMapper = new ObjectMapper();
    }

    public <T> T deserialize(String text, Class<T> valueType) {
        try {
            return objectMapper.readValue(text, valueType);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    public String serialize(final Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }
}
