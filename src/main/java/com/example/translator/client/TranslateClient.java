package com.example.translator.client;

public interface TranslateClient {
    String translate(String text, String sourceLang, String targetLang);
}
