package com.homework.searchservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.searchservice.dto.KakaoRequestDto;
import com.homework.searchservice.dto.KakaoResponseDto;
import com.homework.searchservice.dto.NaverRequestDto;
import com.homework.searchservice.dto.NaverResponseDto;
import com.homework.searchservice.dto.part.DocumentDto;
import com.homework.searchservice.dto.part.ItemDto;
import com.homework.searchservice.dto.part.MetaDto;
import com.homework.searchservice.dto.basic.SearchRequestDto;
import com.homework.searchservice.dto.basic.SearchResponseDto;
import com.homework.searchservice.entity.Search;
import com.homework.searchservice.repository.SearchRepository;
import com.homework.searchservice.source.SearchSourceType;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchServiceImpl implements SearchService{
    Environment env;

    SearchRepository searchRepository;

    @Override
    @Transactional
    public SearchResponseDto search(SearchRequestDto searchDto) {
        switch (searchDto.getSearchSourceType()) {
            case NAVER :
                return searchViaNaver((NaverRequestDto)searchDto);
            default:
                return searchViaKakao((KakaoRequestDto)searchDto);
        }
    }
    private SearchResponseDto searchViaKakao(KakaoRequestDto searchDto) {

        URI uri = UriComponentsBuilder
                .fromUriString(env.getProperty("search.kakao.url"))
                .queryParam("query", searchDto.getQuery())
                .queryParam("page", searchDto.getPage())
                .queryParam("size", searchDto.getSize())
                .queryParam("sort", searchDto.getSort())
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("Authorization", "KakaoAK " + env.getProperty("search.kakao.admin-key"))
                .header("Content-Type", "application/json")
                .header("charset", "utf-8")
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        if(result.getStatusCode().equals(HttpStatus.OK)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String response = result.getBody();
            JSONObject resultJson = new JSONObject(response);

            JSONObject metaJson = resultJson.getJSONObject("meta");
            MetaDto meta =  new MetaDto(metaJson.getInt("total_count"), metaJson.getInt("pageable_count"), metaJson.getBoolean("is_end"));

            JSONArray documentsJson = resultJson.getJSONArray("documents");
            List<DocumentDto> documents = null;
            try {
                documents = Arrays.asList(mapper.readValue(documentsJson.toString(), DocumentDto[].class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            Search findSearch = searchRepository.findByKeyword(searchDto.getQuery());

            if(findSearch == null) {
                Search newSearch = Search.SearchBuilder()
                        .searchCnt(1L)
                        .keyword(searchDto.getQuery())
                        .build();

                searchRepository.save(newSearch);
            } else {
                searchRepository.increaseSearchCnt(findSearch.getKeyword());
            }

            return KakaoResponseDto.KakaoResponseDtoBuilder()
                    .meta(meta)
                    .documents(documents)
                    .build();
        } else {
            NaverRequestDto naverRequestDto = NaverRequestDto.NaverSearchDtoBuilder()
                    .query(searchDto.getQuery())
                    .searchSourceType(SearchSourceType.NAVER)
                    .start(searchDto.getPage()+1)
                    .sort("sim")
                    .display(searchDto.getSize())
                    .build();

            SearchResponseDto search = searchViaNaver(naverRequestDto);
            return search;
        }
    }


    private SearchResponseDto searchViaNaver(NaverRequestDto searchDto) {
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(searchDto.getQuery());
        String encode = StandardCharsets.UTF_8.decode(buffer).toString();

        URI uri = UriComponentsBuilder
                .fromUriString(env.getProperty("search.naver.url"))
                .queryParam("query", encode)
                .queryParam("display", searchDto.getDisplay())
                .queryParam("start", searchDto.getStart())
                .queryParam("sort", searchDto.getSort())
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", env.getProperty("search.naver.client-id"))
                .header("X-Naver-Client-Secret", env.getProperty("search.naver.client-secret"))
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        if(result.getStatusCode().equals(HttpStatus.OK)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String response = result.getBody();
            JSONObject resultJson = new JSONObject(response);

            String lastBuildDate = resultJson.getString("lastBuildDate");
            Integer total = resultJson.getInt("total");
            Integer start = resultJson.getInt("start");
            Integer display = resultJson.getInt("display");

            JSONArray documentsJson = resultJson.getJSONArray("items");
            List<ItemDto> items = null;
            try {
                items = Arrays.asList(mapper.readValue(documentsJson.toString(), ItemDto[].class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            Search findSearch = searchRepository.findByKeyword(searchDto.getQuery());

            if(findSearch == null) {
                Search newSearch = Search.SearchBuilder()
                        .searchCnt(1L)
                        .keyword(searchDto.getQuery())
                        .build();

                searchRepository.save(newSearch);
            } else {
                searchRepository.increaseSearchCnt(findSearch.getKeyword());
            }

            return NaverResponseDto.NaverResponseDtoBuilder()
                    .lastBuildDate(lastBuildDate)
                    .total(total)
                    .start(start)
                    .display(display)
                    .items(items)
                    .build();
        } else {
            KakaoRequestDto kakaoRequestDto = KakaoRequestDto.KakaoSearchDtoBuilder()
                    .query(searchDto.getQuery())
                    .sort("accuracy")
                    .searchSourceType(SearchSourceType.KAKAO)
                    .page(searchDto.getStart() -1)
                    .size(searchDto.getDisplay())
                    .build();

            SearchResponseDto search = searchViaKakao(kakaoRequestDto);
            return search;
        }
    }

    @Transactional(readOnly =true)
    @Override
    public List<SearchResponseDto> getTopKeyword(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "searchCnt");
        Pageable pageable = PageRequest.of(0,size, sort);
        List<Search> topSearch= searchRepository.findAll(pageable).getContent();
        List<SearchResponseDto> responseDtos = new ArrayList<>();
        topSearch.forEach(s -> responseDtos.add(s.toDto()));
        return responseDtos;
    }

}
