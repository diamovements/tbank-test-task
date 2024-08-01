package com.example.translator.controller;

import com.example.translator.dto.TranslationRequest;
import com.example.translator.dto.TranslationResponse;
import com.example.translator.repository.TranslationRepository;
import com.example.translator.service.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/translate")
public class TranslationController {
    private static final Logger logger = LoggerFactory.getLogger(TranslationController.class);

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping
    public TranslationResponse translate(@RequestBody TranslationRequest request, HttpServletRequest httpRequest) {
        String translatedText = translationService.translateText(request.getInputString(),
                request.getSourceLang(), request.getTargetLang(), httpRequest);
        logger.info("Translated text: {} ", translatedText);
        return new TranslationResponse(translatedText);
    }
}

