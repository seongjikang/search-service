package com.homework.searchservice.controller;

import com.homework.searchservice.dto.basic.SearchRequestDto;
import com.homework.searchservice.dto.basic.SearchResponseDto;
import com.homework.searchservice.service.SearchService;
import com.homework.searchservice.source.SearchSourceType;
import com.homework.searchservice.vo.SearchRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/search")
public class SearchController {

    SearchService searchService;

    @PostMapping
    public ResponseEntity<SearchResponseDto> requestSearch(@RequestBody SearchRequest request) {
        if(request.getSearchSourceType() == null) {
            request.setSearchSourceType(SearchSourceType.KAKAO);
        }
        SearchRequestDto requestDto = request.toDto(request.getSearchSourceType());
        SearchResponseDto responseDto = searchService.search(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/top/{size}")
    public ResponseEntity<List<SearchResponseDto>> getTopSearch(@PathVariable("size") Integer size) {
        List<SearchResponseDto> responseDtoList = searchService.getTopKeyword(size);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }
}
