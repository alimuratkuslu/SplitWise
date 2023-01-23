package com.alimurat.SplitWise.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveUserRequest {

    private String name;
    private String surname;
    private String email;
    private String password;
}
