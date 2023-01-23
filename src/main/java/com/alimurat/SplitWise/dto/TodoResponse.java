package com.alimurat.SplitWise.dto;

import com.alimurat.SplitWise.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoResponse {

    private Integer id;

    private String description;
    private Boolean checked;

    private User user;
}
