package com.splitwise.splitwise.dtos.response;

import java.math.BigDecimal;

public record GroupSummaryResponse(
        String groupId,
        String groupName,
        String description,
        BigDecimal totalBalance,
        String balanceType
) {
}
