package com.homework.searchservice.dto.part;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DocumentDto {
    private String title;
    private String contents;
    private String url;
    private LocalDateTime dateTime;
}
