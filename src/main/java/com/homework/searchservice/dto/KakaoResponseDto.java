package com.homework.searchservice.dto;

import com.homework.searchservice.dto.basic.SearchResponseDto;
import com.homework.searchservice.dto.part.DocumentDto;
import com.homework.searchservice.dto.part.MetaDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class KakaoResponseDto extends SearchResponseDto {
   private MetaDto meta;
   private List<DocumentDto> documents;

   @Builder(builderMethodName = "KakaoResponseDtoBuilder", builderClassName = "KakaoResponseDtoBuilder")
    public KakaoResponseDto(MetaDto meta, List<DocumentDto> documents) {
        this.meta = meta;
        this.documents = documents;
    }



}

