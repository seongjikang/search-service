package com.homework.searchservice.service;

import com.homework.searchservice.dto.basic.SearchRequestDto;
import com.homework.searchservice.dto.basic.SearchResponseDto;

import java.util.List;

public interface SearchService {
    SearchResponseDto search(SearchRequestDto searchDto);
    List<SearchResponseDto> getTopKeyword(Integer size);
}
