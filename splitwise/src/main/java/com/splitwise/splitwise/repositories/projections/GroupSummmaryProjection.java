package com.splitwise.splitwise.repositories.projections;



import java.math.BigDecimal;

public interface GroupSummmaryProjection {
    String getGroupId();
    String getGroupName();
    String getGroupDescription();
    BigDecimal getTotalPaid();
    BigDecimal getTotalOwed();
}
