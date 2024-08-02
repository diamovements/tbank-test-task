package com.example.translator;

import com.example.translator.repository.TranslationRepository;
import com.example.translator.connection.TranslateApi;
import com.example.translator.service.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TranslationServiceTest {

    @Mock
    private TranslateApi translator;

    @Mock
    private TranslationRepository translationRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private TranslationService translationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void translateText_success() throws Exception {
        String text = "hello world";
        String sourceLang = "en";
        String targetLang = "es";
        String translatedWord1 = "hola";
        String translatedWord2 = "mundo";
        String ipAddress = "127.0.0.1";

        when(translator.translate("hello", sourceLang, targetLang)).thenReturn(translatedWord1);
        when(translator.translate("world", sourceLang, targetLang)).thenReturn(translatedWord2);
        when(request.getRemoteAddr()).thenReturn(ipAddress);

        String result = translationService.translateText(text, sourceLang, targetLang, request);

        assertEquals("hola mundo", result);
        verify(translationRepository).saveRequest(ipAddress, text, "hola mundo");
    }

    @Test
    void translateText_withExecutionException() {
        when(translator.translate(anyString(), anyString(), anyString()))
                .thenAnswer(invocation -> {
                    throw new ExecutionException("Error during translation: ", new Throwable());
                });

        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            translationService.translateText("hello world", "en", "es", request);
        });
        assertTrue(thrownException.getMessage().contains("Error during translation: "));
    }

    @Test
    void translateText_withInterruptedException() {
        when(translator.translate(anyString(), anyString(), anyString()))
                .thenAnswer(invocation -> {
                    throw new InterruptedException("Error during translation: ");
                });

        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            translationService.translateText("hello world", "en", "es", request);
        });
        assertTrue(thrownException.getMessage().contains("Error during translation: "));
    }
}
