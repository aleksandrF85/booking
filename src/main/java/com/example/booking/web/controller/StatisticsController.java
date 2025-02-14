package com.example.booking.web.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import com.example.booking.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService databaseStatisticsService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadStatistics() {

        try {

            // Получаем данные в формате CSV
            String csvData = databaseStatisticsService.exportStatisticsToCSV();

            // Преобразуем строку в InputStreamResource
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(csvData.getBytes());
            InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

            // Настраиваем заголовки для скачивания файла
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statistics.csv");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(csvData.getBytes().length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new InputStreamResource(new ByteArrayInputStream("Ошибка записи файла CSV".getBytes())));
        }
    }
}
