package com.homework.searchservice.dto.basic;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponseDto {
    private String keyword;
    private Long searchCnt;


    public SearchResponseDto() {
    }

    @Builder(builderClassName = "SearchResponseDtoBuilder", builderMethodName = "SearchResponseDtoBuilder")
    public SearchResponseDto(String keyword, Long searchCnt) {
        this.keyword = keyword;
        this.searchCnt = searchCnt;
    }
}
