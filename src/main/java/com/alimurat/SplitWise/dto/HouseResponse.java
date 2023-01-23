package com.alimurat.SplitWise.dto;

import com.alimurat.SplitWise.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HouseResponse {

    private Integer id;
    private String name;
    private Integer residentNumber;
    private List<User> users;
}
