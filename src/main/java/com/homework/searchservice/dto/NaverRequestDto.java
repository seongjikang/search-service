package com.homework.searchservice.dto;

import com.homework.searchservice.dto.basic.SearchRequestDto;
import com.homework.searchservice.source.SearchSourceType;
import lombok.Builder;
import lombok.Data;

@Data
public class NaverRequestDto extends SearchRequestDto {
    private Integer display;
    private Integer start;

    @Builder(builderMethodName = "NaverSearchDtoBuilder", builderClassName = "NaverSearchDtoBuilder")
    public NaverRequestDto(SearchSourceType searchSourceType, String query, String sort, Integer display, Integer start) {
        super(searchSourceType, query, sort);
        this.display = display;
        this.start = start;
    }
}
