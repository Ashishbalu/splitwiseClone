package com.splitwise.splitwise.services.impl;

import com.splitwise.splitwise.dtos.request.CreateExpenseRequest;
import com.splitwise.splitwise.entities.Expense;
import com.splitwise.splitwise.entities.ExpenseSplit;
import com.splitwise.splitwise.entities.SplitGroup;
import com.splitwise.splitwise.entities.User;
import com.splitwise.splitwise.exceptions.ResourceDoesNotExist;
import com.splitwise.splitwise.repositories.ExpenseRepo;
import com.splitwise.splitwise.repositories.GroupRepo;
import com.splitwise.splitwise.repositories.UserRepo;
import com.splitwise.splitwise.services.ExpenseService;
import com.splitwise.splitwise.utilities.ExpenseUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepo expenseRepo;
    private final GroupRepo groupRepo;
    private final UserRepo userRepo;

    @Override
    @Transactional
    public Expense createExpense(String userId, String groupId, CreateExpenseRequest createExpenseRequest) {
        //verify user
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceDoesNotExist("user with this: '" + userId + "' userId does not found"));

        //verify group
        SplitGroup splitGroup = groupRepo.findByIdWithMembers(groupId)
                .orElseThrow(()-> new ResourceDoesNotExist("group with this: '" + groupId + "' does not found"));

        //verify user access to the group
        if (!groupRepo.existsByIdAndUser(groupId, user)){
            throw new ResourceDoesNotExist("user with id: '" + userId + "' is not a member of the group");
        }
        //create expense entity
        BigDecimal amount= ExpenseUtility.convertToPaisa(createExpenseRequest.amountInRupees());
        Expense expense = Expense.builder()
                .description(createExpenseRequest.description())
                .amount(amount)
                .paidBy(user)
                .group(splitGroup)
                .build();
        //save entity
        expenseRepo.save(expense);
        //split expense among group members
        splitAmountEqually(splitGroup, expense, amount);
        //return expense
        return expense;
    }
    private void splitAmountEqually(SplitGroup splitGroup, Expense expense, BigDecimal amount){
        Set<User> groupMembers = splitGroup.getUser();
        BigDecimal[] splits = amount.divideAndRemainder(BigDecimal.valueOf(groupMembers.size()));

        BigDecimal share = splits[0];
        BigDecimal reminder = splits[1];


        List<ExpenseSplit> expenseSplits = new ArrayList<>();
        for (User member : groupMembers){
            BigDecimal shareAmount = share;
            if (reminder.signum() > 0){
                shareAmount = shareAmount.add(BigDecimal.ONE);
                reminder.subtract(BigDecimal.ONE);
            }
            expenseSplits.add(
                    ExpenseSplit.builder()
                            .expense(expense)
                            .user(member)
                            .amount(shareAmount)
                            .build()
            );
        }
        expense.getSplits().addAll(expenseSplits);
        expenseRepo.save(expense);
    }
}
