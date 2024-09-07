package com.example.translator.controller;

import com.example.translator.configuration.SupportedLanguages;
import com.example.translator.dto.TranslationRequest;
import com.example.translator.dto.TranslationResponse;
import com.example.translator.service.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class TranslationController {
    //private static final Pattern LANGUAGE_CODE_PATTERN = Pattern.compile("^[a-zA-Z]{2}$");

    private final TranslationService translationService;


    @PostMapping("/translate")
    public TranslationResponse translate(@RequestBody TranslationRequest request, HttpServletRequest httpRequest) {
        validateLanguageCode(request.sourceLang());
        validateLanguageCode(request.targetLang());

        String translatedText = translationService.translateText(
                request.inputString(),
                request.sourceLang(),
                request.targetLang(),
                httpRequest
        );
        log.info("Translated text: {} ", translatedText);
        return new TranslationResponse(translatedText);
    }

    private void validateLanguageCode(String langCode) {
        if (!SupportedLanguages.VALID_LANGUAGE_CODES.contains(langCode)) {
            log.debug("Invalid language: {}", langCode);
            throw new IllegalArgumentException(langCode);
        }
    }
}

