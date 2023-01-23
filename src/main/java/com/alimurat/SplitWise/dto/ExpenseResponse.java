package com.alimurat.SplitWise.dto;

import com.alimurat.SplitWise.model.ExpenseType;
import com.alimurat.SplitWise.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ExpenseResponse {

    private Integer id;

    private Float amount;
    private String description;
    private LocalDate date;
    private ExpenseType expenseType;

    private User user;
}
