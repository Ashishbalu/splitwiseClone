package com.splitwise.splitwise.controllers;

import com.splitwise.splitwise.dtos.request.CreateExpenseRequest;
import com.splitwise.splitwise.dtos.response.CreateExpenseResponse;
import com.splitwise.splitwise.entities.Expense;
import com.splitwise.splitwise.payload.ApiResponse;
import com.splitwise.splitwise.services.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("{groupId}/create")
    public ResponseEntity<ApiResponse<CreateExpenseResponse>> createExpense(
            @RequestHeader("x-user-id") String userId,
            @PathVariable String groupId,
            @RequestBody @Valid CreateExpenseRequest createExpenseRequest
    ){
        Expense expense = expenseService.createExpense(userId, groupId, createExpenseRequest);
        CreateExpenseResponse createExpenseResponse = new CreateExpenseResponse(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount()
        );
            return ResponseEntity.ok(ApiResponse.success("expense created successfully", createExpenseResponse));

    }
}
