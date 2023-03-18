package com.homework.searchservice.vo;

import com.homework.searchservice.dto.basic.SearchRequestDto;
import com.homework.searchservice.dto.KakaoRequestDto;
import com.homework.searchservice.dto.NaverRequestDto;
import com.homework.searchservice.source.SearchSourceType;
import lombok.Data;

@Data
public class SearchRequest {
    //공통
    private String keyword;
    private String sort;

    //카카오 전용
    private Integer page;
    private Integer size;

    //네이버 전용
    private Integer start;
    private Integer display;

    //새로운 요소가 추가되면 아래에 추가...
    private SearchSourceType searchSourceType;


    public SearchRequestDto toDto(SearchSourceType searchSourceType) {

        switch (searchSourceType) {
            case KAKAO :
                return KakaoRequestDto.KakaoSearchDtoBuilder()
                        .query(keyword)
                        .sort(sort)
                        .page(page)
                        .size(size)
                        .searchSourceType(searchSourceType)
                        .build();
            case NAVER :
                return NaverRequestDto.NaverSearchDtoBuilder()
                        .query(keyword)
                        .sort(sort)
                        .start(start)
                        .display(display)
                        .searchSourceType(searchSourceType)
                        .build();
            default:
                throw new RuntimeException();
        }

    }
}
