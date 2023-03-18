package com.homework.searchservice.service;

import com.homework.searchservice.dto.basic.SearchRequestDto;
import com.homework.searchservice.dto.basic.SearchResponseDto;

public interface SearchService {
    SearchResponseDto search(SearchRequestDto searchDto);
}
