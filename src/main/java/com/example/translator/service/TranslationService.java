package com.example.translator.service;

import com.example.translator.client.TranslateClient;
import com.example.translator.repository.TranslationRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationService {

    private final TranslateClient translator;

    private final TranslationRepository translationRepository;

    @Transactional
    public String translateText(String text, String sourceLang, String targetLang, HttpServletRequest request) {
        List<String> words = Arrays.asList(text.split(" "));
        log.info("List: {} ", words.toString());
        if (words.size() > 10) {
            throw new RuntimeException("Too many words.....");
        }
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<String>> futures = words.stream()
                .map(word -> executor.submit(() -> translator.translate(word, sourceLang, targetLang)))
                .toList();
        log.info("Translated {} words", futures.size());
        String translatedText = futures.stream()
                .map(future -> {
                    try {
                        log.info("Future result: {} ", future.get());
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        log.info("Exception: ", e);
                        throw new RuntimeException("Error during translation: " + e.getMessage(), e);
                    }
                })
                .collect(Collectors.joining(" "));

        String ipAddress = request.getRemoteAddr();
        log.info("Saving request: {}, {}, {} ", ipAddress, text, translatedText);
        translationRepository.saveRequest(ipAddress, text, translatedText);
        executor.shutdown();
        return translatedText;
    }
}

