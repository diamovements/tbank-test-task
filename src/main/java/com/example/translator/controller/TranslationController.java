package com.example.translator.controller;

import com.example.translator.dto.TranslationRequest;
import com.example.translator.dto.TranslationResponse;
import com.example.translator.service.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class TranslationController {
    private static final Pattern LANGUAGE_CODE_PATTERN = Pattern.compile("^[a-zA-Z]{2}$");

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

    @GetMapping("/translate")
    public TranslationResponse translateWithParams(
            @RequestParam String inputString,
            @RequestParam String sourceLang,
            @RequestParam String targetLang,
            HttpServletRequest httpRequest) {
        validateLanguageCode(sourceLang);
        validateLanguageCode(targetLang);

        String translatedText = translationService.translateText(
                inputString,
                sourceLang,
                targetLang,
                httpRequest
        );
        log.info("Translated text: {}", translatedText);
        return new TranslationResponse(translatedText);
    }

    private void validateLanguageCode(String langCode) {
        if (!LANGUAGE_CODE_PATTERN.matcher(langCode).matches()) {
            log.debug("Invalid language: {} ", langCode);
            throw new IllegalArgumentException("Invalid language usage: " + langCode);
        }
    }
}

