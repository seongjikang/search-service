package com.homework.searchservice.dto.part;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class DocumentDto {
    private String title;
    private String contents;
    private String url;
    private LocalDateTime date_time;

    public DocumentDto() {
    }

    public DocumentDto(String title, String contents, String url, LocalDateTime date_time) {
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.date_time = date_time;
    }
}
