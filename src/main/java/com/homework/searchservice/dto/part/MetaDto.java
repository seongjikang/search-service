package com.homework.searchservice.dto.part;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class MetaDto {
    private int total_count;
    private int pageable_count;
    private boolean is_end;

}
