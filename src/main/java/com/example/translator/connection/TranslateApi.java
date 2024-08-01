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

    private static final String API_KEY = "trnsl.1.1.20180705T112238Z.ee1aa33bef8dd54f.4897d26d7b48f73345651f22e1403332f48cc3c4";
    private static final String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";

    private static final String EMPTY_STRING = "";

    private final RestTemplate restTemplate;

    public TranslateApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    public String translate(String text, String sourceLang, String targetLang) {
//        String url = String.format("%s?key=%s&text=%s&lang=%s-%s", API_URL, API_KEY, text, sourceLang, targetLang);
//        return restTemplate.getForObject(url, String.class);
//    }
    public String translate(String text, String sourceLang, String targetLang) {
        String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl={sourceLanguage}&tl={targetLanguage}&dt=t&q={word}";
        var response = restTemplate.getForEntity(url, String.class, sourceLang, targetLang, text);
//        logger.info("Translated text: {} ", response.getBody());
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
