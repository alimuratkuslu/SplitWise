package com.alimurat.SplitWise.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseSummary {

    private Float bill;
    private Float grocery;
    private Float rent;
    private Float miscellaneous;
}
