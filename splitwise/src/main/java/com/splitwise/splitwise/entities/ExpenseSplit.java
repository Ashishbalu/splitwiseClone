package com.splitwise.splitwise.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ExpenseSplit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "expense_id",
            nullable = false)
    private Expense expense;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @Column(nullable = false)
    private BigInteger amount;
}
