package notifications.sender.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class DataMapper {

    private final ObjectMapper objectMapper;

    public DataMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @SneakyThrows
    public <T> T deserialize(String text, Class<T> valueType) {
        return objectMapper.readValue(text, valueType);
    }

    @SneakyThrows
    public String serialize(final Object value) {
        return objectMapper.writeValueAsString(value);
    }
}
