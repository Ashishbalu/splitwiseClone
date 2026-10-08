package com.splitwise.splitwise.controllers;

import com.splitwise.splitwise.dtos.request.CreateExpenseRequest;
import com.splitwise.splitwise.dtos.response.CreateExpenseResponse;
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

    @PostMapping
    public ResponseEntity<ApiResponse<CreateExpenseResponse>> createExpense(
            @RequestHeader("x-user-id") String userId,
            @RequestBody @Valid CreateExpenseRequest createExpenseRequest
    ){
        CreateExpenseResponse createExpenseResponse = expenseService.createExpense(){
            return ResponseEntity.ok(ApiResponse.success("expense created successfully"));
        }
    }
}
