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
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"})
    )
    @Builder.Default
    private Set<User> users = new HashSet<>();

    public void addUser(User user){
        this.users.add(user);
        user.getGroups().add(this);
    }

    public void removeGroup(User user){
        if (this.users.contains(user)){
            this.users.remove(user);
            user.getGroups().remove(this);
        }
    }
}
