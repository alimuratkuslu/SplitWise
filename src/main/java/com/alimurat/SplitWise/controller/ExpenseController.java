package com.alimurat.SplitWise.controller;

import com.alimurat.SplitWise.dto.*;
import com.alimurat.SplitWise.model.Expense;
import com.alimurat.SplitWise.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Integer id){
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(){
        return ResponseEntity.ok(expenseService.getExpenses());
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> saveExpense(@RequestBody SaveExpenseRequest request){
        return ResponseEntity.ok(expenseService.saveExpense(request));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ExpenseResponse> addExpenseToUser(@PathVariable Integer id, @RequestBody AddExpenseToUserDto request){
        return ResponseEntity.ok(expenseService.addExpenseToUser(id, request.getEmail()));
    }

    @PostMapping("/future")
    public void addFutureExpense(@RequestBody SaveExpenseRequest request){
        expenseService.addFutureExpense(request);

        expenseService.checkExpenses();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Integer id, @RequestBody SaveExpenseRequest request){
        return ResponseEntity.ok(expenseService.updateExpense(id, request));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ExpenseResponse> deleteExpenseFromUser(@PathVariable Integer id, @RequestBody AddExpenseToUserDto request){
        return ResponseEntity.ok(expenseService.deleteExpenseFromUser(id, request.getEmail()));
    }
    @DeleteMapping("/{id}")
    public void deleteExpenseById(@PathVariable Integer id){
        expenseService.deleteExpenseById(id);
    }

    @GetMapping("/summary")
    public ResponseEntity<ExpenseSummary> expenseSummaryByUser(@RequestBody AddExpenseToUserDto request){
        return ResponseEntity.ok(expenseService.expenseSummaryOfUser(request.getEmail()));
    }

    @GetMapping("/shared/{id}")
    public void divideExpenses(@PathVariable Integer id){
        expenseService.divideExpenses(id);
    }
}
