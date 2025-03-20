package com.pplbo.loggingservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Service
@Slf4j
public class LoggingService {

    public static final String LOG_FILE_PATH = "/logs/book-service.log";

    public LoggingService() {
        log.info("LoggingService initialized..");
    }

    @Scheduled(fixedRate = 150000)
    public void processLog() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("Processing log: {}", line);
            }
        } catch (Exception e) {
            log.error("error {}", e.getMessage());
        }

    }
}
