package com.example.translator.configuration;

import java.util.Set;

public final class SupportedLanguages {

    private SupportedLanguages() {
    }

    public static final Set<String> VALID_LANGUAGE_CODES = Set.of(
            "fr", "de", "ru", "en", "es"
    );
}


