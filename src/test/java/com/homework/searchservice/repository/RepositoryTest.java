package com.homework.searchservice.repository;

import com.homework.searchservice.entity.Search;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@DataJpaTest
@RunWith(SpringRunner.class)
public class RepositoryTest {
    @Autowired
    SearchRepository searchRepository;

    @Before
    public void init() {
        for(int i =1; i <= 10 ; i ++) {
            Search search = Search.SearchBuilder()
                    .searchCnt(1L)
                    .keyword("data"+i)
                    .build();
            searchRepository.save(search);
        }

        for(int i = 2 ; i <= 10 ; i++)
            searchRepository.increaseSearchCnt("data10");
        for(int i = 2 ; i <= 9 ; i++)
            searchRepository.increaseSearchCnt("data9");
        for(int i = 2 ; i <= 8 ; i++)
            searchRepository.increaseSearchCnt("data8");
        for(int i = 2 ; i <= 7 ; i++)
            searchRepository.increaseSearchCnt("data7");
        for(int i = 2 ; i <= 6 ; i++)
            searchRepository.increaseSearchCnt("data6");
        for(int i = 2 ; i <= 5 ; i++)
            searchRepository.increaseSearchCnt("data5");
        for(int i = 2 ; i <= 4 ; i++)
            searchRepository.increaseSearchCnt("data4");
        for(int i = 2 ; i <= 3 ; i++)
            searchRepository.increaseSearchCnt("data3");
        for(int i = 2 ; i <= 2 ; i++)
            searchRepository.increaseSearchCnt("data2");
        for(int i = 2 ; i <= 1 ; i++)
            searchRepository.increaseSearchCnt("data1");
    }

    @Test
    public void 검색기록_증가_및_조회_테스트() {

        for(int i =1; i <= 10 ; i ++) {
            Search findSearch = searchRepository.findByKeyword("data"+i);
            long cnt = findSearch.getSearchCnt();
            Assert.assertEquals(cnt, i);
        }

    }

    @Test
    public void 검색기록_탑10_조회_테스트() {
        Sort sort = Sort.by(Sort.Direction.DESC, "searchCnt");
        Pageable pageable = PageRequest.of(0,10, sort);
        List<Search> topSearch= searchRepository.findAll(pageable).getContent();

        Assert.assertEquals(topSearch.size(), 10);
        Assert.assertEquals(topSearch.get(0).getKeyword() , "data10");
        Assert.assertEquals(topSearch.get(9).getKeyword() , "data1");

    }

    @Test
    public void 검색기록_증가_테스트() {
        Search search = Search.SearchBuilder()
                .searchCnt(1L)
                .keyword("data")
                .build();
        searchRepository.save(search);

        searchRepository.increaseSearchCnt("data");

        Search findSearch = searchRepository.findByKeyword("data");
        Assert.assertEquals((long)findSearch.getSearchCnt() , 2);
    }


}
