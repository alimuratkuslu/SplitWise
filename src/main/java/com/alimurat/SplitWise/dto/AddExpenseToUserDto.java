package com.alimurat.SplitWise.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
// @Builder
@RequiredArgsConstructor
public class AddExpenseToUserDto {

    private String email;
}
