package com.alimurat.SplitWise.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
// @Builder
@RequiredArgsConstructor
public class AddTodoToUserDto {

    private String email;
}
