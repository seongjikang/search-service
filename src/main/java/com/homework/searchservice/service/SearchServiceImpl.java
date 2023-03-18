package com.homework.searchservice.service;

import com.homework.searchservice.dto.KakaoResponseDto;
import com.homework.searchservice.dto.part.DocumentDto;
import com.homework.searchservice.dto.part.MetaDto;
import com.homework.searchservice.dto.basic.SearchRequestDto;
import com.homework.searchservice.dto.basic.SearchResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchServiceImpl implements SearchService{
    Environment env;

    @Override
    public SearchResponseDto search(SearchRequestDto searchDto) {
        switch (searchDto.getSearchSourceType()) {
            case KAKAO :
                return searchViaKakao(searchDto);
        }
        return null;
    }

    private SearchResponseDto searchViaKakao(SearchRequestDto searchDto) {
        //TODO : api 연동 후 값 받와야 함. 일단 임시 값 셋팅
        MetaDto meta = new MetaDto(897323, 775, false);
        List<DocumentDto> documents = new ArrayList<>();
        DocumentDto documentDto1 = new DocumentDto("title1", "contents1", "url1", LocalDateTime.now());
        DocumentDto documentDto2 = new DocumentDto("title2", "contents2", "url2", LocalDateTime.now());
        documents.add(documentDto1);
        documents.add(documentDto2);

        return KakaoResponseDto.KakaoResponseDtoBuilder()
                .meta(meta)
                .documents(documents)
                .build();
    }
}
