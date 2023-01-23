package com.alimurat.SplitWise.dto;

import com.alimurat.SplitWise.model.ExpenseType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SaveExpenseRequest {

    private Float amount;
    private String description;
    private LocalDate date;
    private ExpenseType expenseType;
}
