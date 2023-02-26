package com.technical.testmandiri.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse {

    List<UserResponse> user_data;
    private Integer max_records;
    private Integer offset;

}
