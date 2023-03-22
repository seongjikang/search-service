package com.homework.searchservice.repository;

import com.homework.searchservice.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface SearchRepository extends JpaRepository<Search, Long> {

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update Search s set s.searchCnt = s.searchCnt + 1 where s.keyword = :keyword")
    void increaseSearchCnt(@Param("keyword") String keyword);

    Search findByKeyword(String keyword);
}
