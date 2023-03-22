package com.homework.searchservice.dto.part;

import lombok.Data;

@Data
public class ItemDto {
    private String title;
    private String link;
    private String description;
    private String bloggername;
    private String bloggerlink;
    private String postdate;

    public ItemDto() {
    }

    public ItemDto(String title, String link, String description, String bloggername, String bloggerlink, String postdate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.bloggername = bloggername;
        this.bloggerlink = bloggerlink;
        this.postdate = postdate;
    }
}
