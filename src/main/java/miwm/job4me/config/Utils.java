package miwm.job4me.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Utils {
    public static <T> T jsonStringToObject(final String jsonString, Class<T> classa) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(jsonString, classa);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
