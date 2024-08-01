package com.example.translator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class TranslationRequest {
    private String inputString;
    private String sourceLang;
    private String targetLang;
}
