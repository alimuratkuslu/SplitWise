package com.alimurat.SplitWise.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveHouseRequest {

    private String name;
    private Integer residentNumber;
}
