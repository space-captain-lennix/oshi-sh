package be.lennix.oshish.cookie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.Cookie;

public class OshiShCookie extends Cookie {
    public static String name = "OshiSH";
    private ObjectMapper objectMapper;

    public OshiShCookie(String name, String value){
        super(name, value);
    }

    public OshiShCookie(OshiShSaveDto value) throws JsonProcessingException {
        super(OshiShCookie.name, "");
        setupObjectMapper();
        this.setValue(objectMapper.writeValueAsString(value));
    }

    private void setupObjectMapper() {
        objectMapper = new ObjectMapper();
    }

    public OshiShSaveDto getOshiShSaveDto() throws JsonProcessingException {
        return objectMapper.readValue(this.getValue(), OshiShSaveDto.class);
    }
}
