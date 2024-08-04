package com.example.translator.dto;
public record TranslationRequest(String inputString, String sourceLang, String targetLang) {
}