package com.example.translator.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Repository
public class TranslationRepository {

    private final JdbcTemplate jdbcTemplate;

    public TranslationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveRequest(String ipAddress, String inputString, String translatedString) {
        String query = "INSERT INTO t_users (ip_address, input_string, translated_string) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, ipAddress, inputString, translatedString);
    }
}
