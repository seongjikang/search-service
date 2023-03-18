package com.homework.searchservice.dto;

import com.homework.searchservice.dto.basic.SearchRequestDto;
import com.homework.searchservice.source.SearchSourceType;
import lombok.Builder;

public class KakaoRequestDto extends SearchRequestDto {
    private Integer page;
    private Integer size;

    @Builder(builderClassName = "KakaoSearchDtoBuilder", builderMethodName = "KakaoSearchDtoBuilder")
    public KakaoRequestDto(SearchSourceType searchSourceType, String query, String sort, Integer page, Integer size) {
        super(searchSourceType,query, sort);
        this.page = page;
        this.size = size;
    }
}
