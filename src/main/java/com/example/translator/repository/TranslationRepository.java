package com.example.translator.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Repository
public class TranslationRepository {

    private static final Logger logger = LoggerFactory.getLogger(TranslationRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public TranslationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void saveRequest(String ipAddress, String inputString, String translatedString) {
        if (ipAddress == null || inputString == null || translatedString == null) {
            logger.debug("Null parameters detected");
            throw new IllegalArgumentException("None of the input parameters can be null");
        }
        if (ipAddress.length() > 45 || inputString.length() > 255 || translatedString.length() > 255) {
            logger.debug("Input values exceed max length");
            throw new IllegalArgumentException("Input values exceed maximum length");
        }
        String query = "INSERT INTO t_users (ip_address, input_string, translated_string) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, ipAddress, inputString, translatedString);
    }
}
