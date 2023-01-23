package com.alimurat.SplitWise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
// @Builder
@RequiredArgsConstructor
public class AddUserToHouseDto {

    private String email;
}
