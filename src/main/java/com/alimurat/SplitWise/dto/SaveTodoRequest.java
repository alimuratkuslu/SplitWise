package com.alimurat.SplitWise.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveTodoRequest {

    private String description;
    private Boolean checked;
}
