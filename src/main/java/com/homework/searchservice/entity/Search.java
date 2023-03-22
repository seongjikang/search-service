package com.homework.searchservice.entity;

import com.homework.searchservice.dto.basic.SearchResponseDto;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "search_tbl")
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String keyword;

    private Long searchCnt;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value="CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    public Search() {
    }

    @Builder(builderMethodName = "SearchBuilder", builderClassName = "SearchBuilder")
    public Search(String keyword, Long searchCnt) {
        this.keyword = keyword;
        this.searchCnt = searchCnt;
    }

    public SearchResponseDto toDto(){
        return  SearchResponseDto.SearchResponseDtoBuilder()
                .searchCnt(searchCnt)
                .keyword(keyword)
                .build();
    }

}
