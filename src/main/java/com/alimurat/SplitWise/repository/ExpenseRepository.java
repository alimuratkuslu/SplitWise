package com.alimurat.SplitWise.repository;

import com.alimurat.SplitWise.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
}
