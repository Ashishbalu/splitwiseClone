package com.splitwise.splitwise.services;

import com.splitwise.splitwise.dtos.request.CreateExpenseRequest;
import com.splitwise.splitwise.entities.Expense;

public interface ExpenseService {

   Expense createExpense(String userId,String groupId, CreateExpenseRequest createExpenseRequest);
}
