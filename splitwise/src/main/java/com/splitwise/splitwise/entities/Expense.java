package com.splitwise.splitwise.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(
            nullable = false
    )
    private String description;

    @Column(nullable = false)
    private BigInteger amount;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "group_id",
            nullable = false
    )
    private SplitGroup group;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "paid_by_user_id",
            nullable = false
    )
    private User paidBy;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ExpenseSplit> splits = new HashSet<>();
}
