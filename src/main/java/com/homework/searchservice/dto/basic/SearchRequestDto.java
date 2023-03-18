package com.homework.searchservice.dto.basic;

import com.homework.searchservice.source.SearchSourceType;
import lombok.Builder;
import lombok.Data;

@Data
public class SearchRequestDto {
    private SearchSourceType searchSourceType;
    private String query;
    private String sort;

    @Builder(builderClassName = "BasicSearchDtoBuilder", builderMethodName = "BasicSearchDtoBuilder")
    public SearchRequestDto(SearchSourceType searchSourceType, String query, String sort) {
        this.searchSourceType = searchSourceType;
        this.query = query;
        this.sort = sort;
    }
}
