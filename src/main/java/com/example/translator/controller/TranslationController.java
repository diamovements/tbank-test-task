package com.example.translator.controller;

import com.example.translator.dto.TranslationRequest;
import com.example.translator.dto.TranslationResponse;
import com.example.translator.service.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/translate")
public class TranslationController {
    private static final Logger logger = LoggerFactory.getLogger(TranslationController.class);

    private static final Pattern LANGUAGE_CODE_PATTERN = Pattern.compile("^[a-zA-Z]{2}$");

    @Autowired
    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping
    public TranslationResponse translate(@RequestBody TranslationRequest request, HttpServletRequest httpRequest) {
        validateLanguageCode(request.getSourceLang());
        validateLanguageCode(request.getTargetLang());

        String translatedText = translationService.translateText(request.getInputString(),
                request.getSourceLang(), request.getTargetLang(), httpRequest);
        logger.info("Translated text: {} ", translatedText);
        return new TranslationResponse(translatedText);
    }

    private void validateLanguageCode(String langCode) {
        if (!LANGUAGE_CODE_PATTERN.matcher(langCode).matches()) {
            logger.debug("Invalid language: {} ", langCode);
            throw new IllegalArgumentException("Invalid language usage: " + langCode);
        }
    }
}

