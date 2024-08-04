package com.example.translator;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.translator.controller.TranslationController;
import com.example.translator.dto.TranslationRequest;
import com.example.translator.service.TranslationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TranslationController.class)
public class TranslationControllerTest {

    @MockBean
    private TranslationService translationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("TranslationController success translation test")
    public void translate_validRequest_shouldReturnTranslatedText() throws Exception {
        TranslationRequest request = new TranslationRequest("hello", "en", "ru");
        Mockito.when(translationService.translateText(any(), any(), any(), any())).thenReturn("привет");
        mockMvc.perform(post("/api/v1/translate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
