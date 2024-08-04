package com.example.translator.connection;

public interface TranslateClient {
    String translate(String text, String sourceLang, String targetLang);
}
