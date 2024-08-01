package com.example.translator.service;

import com.example.translator.repository.TranslationRepository;
import com.example.translator.connection.TranslateApi;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class TranslationService {

    private final TranslateApi translator;
    private final TranslationRepository translationRepository;

    public TranslationService(TranslateApi translator, TranslationRepository translationRepository) {
        this.translator = translator;
        this.translationRepository = translationRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(TranslationService.class);

    public String translateText(String text, String sourceLang, String targetLang, HttpServletRequest request) {
        List<String> words = Arrays.asList(text.split(" "));
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<String>> futures = words.stream()
                .map(word -> executor.submit(() -> translator.translate(word, sourceLang, targetLang)))
                .toList();
        logger.info("Translated {} words", futures.size());

        String translatedText = futures.stream()
                .map(future -> {
                    try {
                        logger.info("Future result: {} ", future.get());
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        logger.info("Exception: ", e);
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.joining(" "));

        String ipAddress = request.getRemoteAddr();
        logger.info("Saving request: {}, {}, {} ", ipAddress, text, translatedText);
        translationRepository.saveRequest(ipAddress, text, translatedText);
        executor.shutdown();
        return translatedText;
    }
}

