package com.homework.searchservice.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.searchservice.dto.KakaoRequestDto;
import com.homework.searchservice.dto.KakaoResponseDto;
import com.homework.searchservice.dto.part.DocumentDto;
import com.homework.searchservice.dto.part.MetaDto;
import com.homework.searchservice.dto.basic.SearchRequestDto;
import com.homework.searchservice.dto.basic.SearchResponseDto;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchServiceImpl implements SearchService{
    Environment env;

    @Override
    public SearchResponseDto search(SearchRequestDto searchDto) {
        switch (searchDto.getSearchSourceType()) {
            case KAKAO :
                return searchViaKakao((KakaoRequestDto)searchDto);
        }
        return null;
    }

    private SearchResponseDto searchViaKakao(KakaoRequestDto searchDto)  {

        try {
            //TODO : api 연동 후 값 받와야 함. 일단 임시 값 셋팅
            String requestUrl = String.format("%s?query=%s&sort=%s&page=%d&size=%d", env.getProperty("search.kakao.url"), searchDto.getQuery(), searchDto.getSort(), searchDto.getPage(),searchDto.getSize());
            URL url = new URL(requestUrl);

            InputStreamReader is = null;
            BufferedReader br = null;

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "KakaoAK " + env.getProperty("search.kakao.admin-key"));

            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");

            final int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200) {
                is = new InputStreamReader(urlConnection.getInputStream());
            } else {
                is = new InputStreamReader(urlConnection.getErrorStream());
            }
            br = new BufferedReader(is);
            final StringBuffer buffer = new StringBuffer();
            String line;
            while((line = br.readLine())!= null) {
                buffer.append(line);
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String response = buffer.toString();
            JSONObject resultJson = new JSONObject(response);

            JSONObject metaJson = resultJson.getJSONObject("meta");
            MetaDto meta =  new MetaDto(metaJson.getInt("total_count"), metaJson.getInt("pageable_count"), metaJson.getBoolean("is_end"));
            //mapper.readValue(metaJson.toString(), MetaDto.class);

            JSONArray documentsJson = resultJson.getJSONArray("documents");
            List<DocumentDto> documents = Arrays.asList(mapper.readValue(documentsJson.toString(), DocumentDto[].class));
            return KakaoResponseDto.KakaoResponseDtoBuilder()
                    .meta(meta)
                    .documents(documents)
                    .build();
        }catch (IOException e){
            throw new RuntimeException();
        }

    }
}
