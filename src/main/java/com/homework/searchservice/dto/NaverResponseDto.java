package com.homework.searchservice.dto;

import com.homework.searchservice.dto.basic.SearchResponseDto;
import com.homework.searchservice.dto.part.ItemDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class NaverResponseDto extends SearchResponseDto {
    private String lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<ItemDto> items;

    @Builder(builderMethodName = "NaverResponseDtoBuilder", builderClassName = "NaverResponseDtoBuilder")
    public NaverResponseDto(String keyword, Long searchCnt, String lastBuildDate, Integer total, Integer start, Integer display, List<ItemDto> items) {
        super(keyword, searchCnt);
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.start = start;
        this.display = display;
        this.items = items;
    }
}
