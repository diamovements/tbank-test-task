package com.example.translator.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleTranslateClient implements TranslateClient{

    private static final String EMPTY_STRING = "";

    @Value("${spring.translator.google.url}")
    private String googleTranslateUrl;

    private final RestTemplate restTemplate;

    public String translate(String text, String sourceLang, String targetLang) {
        var response = restTemplate.getForEntity(googleTranslateUrl, String.class, sourceLang, targetLang, text);
        if (response.getBody() != null) {
            try {
                var objectMapper = new ObjectMapper();
                var jsonNode = objectMapper.readTree(response.getBody());
                var arrayNode = (ArrayNode) jsonNode.get(0);
                log.info("Translated text: {} ", arrayNode.get(0).get(0).asText());
                return arrayNode.get(0).get(0).asText();

            } catch (JsonProcessingException exception) {
                exception.getMessage();
            }
        }
        return EMPTY_STRING;
    }
}
