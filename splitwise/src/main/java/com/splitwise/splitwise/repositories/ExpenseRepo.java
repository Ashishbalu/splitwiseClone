package com.splitwise.splitwise.repositories;

import com.splitwise.splitwise.entities.Expense;
import com.splitwise.splitwise.repositories.projections.GroupSummmaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepo extends JpaRepository<Expense, String> {

    @Query("""
            SELECT
                g.id AS groupId,
                g.groupName AS groupName,
                g.description AS groupDescription,
                COALESCE((
                    SELECT SUM(e.amount)
                    FROM Expense e
                    WHERE e.group = g
                      AND e.paidBy.id = :userId
                ), 0) AS totalPaid,
                COALESCE((
                    SELECT SUM(es.amount)
                    FROM ExpenseSplit es
                    WHERE es.expense.group = g
                      AND es.user.id = :userId
                ), 0) AS totalOwed
            FROM User u
            JOIN u.groups g
            WHERE u.id = :userId
            """)
    List<GroupSummmaryProjection> getGroupSummaryForUser(@Param("userId") String userId);
}
