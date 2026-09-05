package com.splitwise.splitwise.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(
            name = "group_name",
            nullable = false,
            length = 100
    )
    private String groupName;


    private String description;
}
