package com.splitwise.splitwise.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SplitGroup {

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

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"group_id", "user_id"}
            )
    )
    @Builder.Default
    private Set<User> user = new HashSet<>();

    public void addUser(User user){
        this.user.add(user);
        user.getGroups().add(this);
    }

    public void removeGroup(User user){
        if (this.user.contains(user)){
            this.user.remove(user);
            user.getGroups().remove(this);
        }
    }
}
