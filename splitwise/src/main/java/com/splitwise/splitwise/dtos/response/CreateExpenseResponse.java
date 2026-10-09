package com.splitwise.splitwise.dtos.response;

import java.math.BigDecimal;

public record CreateExpenseResponse(
        String id,
        String description,
        BigDecimal amount
) {
}
