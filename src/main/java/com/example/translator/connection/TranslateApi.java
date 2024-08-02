package com.example.translator.connection;

import com.example.translator.repository.TranslationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TranslateApi {

    private static final Logger logger = LoggerFactory.getLogger(TranslateApi.class);

    private static final String EMPTY_STRING = "";

    private final RestTemplate restTemplate;

    public TranslateApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String translate(String text, String sourceLang, String targetLang) {
        String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl={sourceLanguage}&tl={targetLanguage}&dt=t&q={word}";
        var response = restTemplate.getForEntity(url, String.class, sourceLang, targetLang, text);
        if (response.getBody() != null) {
            try {
                var objectMapper = new ObjectMapper();
                var jsonNode = objectMapper.readTree(response.getBody());
                var arrayNode = (ArrayNode) jsonNode.get(0);
                logger.info("Translated text: {} ", arrayNode.get(0).get(0).asText());
                return arrayNode.get(0).get(0).asText();

            } catch (JsonProcessingException exception) {
                exception.getMessage();
            }
        }
        return EMPTY_STRING;
    }
}
