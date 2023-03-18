package com.homework.searchservice.vo;

import com.homework.searchservice.dto.part.DocumentDto;
import com.homework.searchservice.vo.basic.SearchResponse;
import lombok.Builder;

import java.util.List;

public class KakaoResponse extends SearchResponse {
    private Integer totalCnt;
    private Integer pageCnt;
    private Boolean isEnd;

    List<DocumentDto> documents;

    @Builder(builderClassName = "KakaoResponseBuilder", builderMethodName = "KakaoResponseBuilder")
    public KakaoResponse(Integer totalCnt, Integer pageCnt, Boolean isEnd, List<DocumentDto> documents) {
        this.totalCnt = totalCnt;
        this.pageCnt = pageCnt;
        this.isEnd = isEnd;
        this.documents = documents;
    }

}
