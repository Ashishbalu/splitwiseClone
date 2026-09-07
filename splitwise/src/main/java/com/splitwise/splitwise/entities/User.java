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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(
            nullable = false,
            length = 100
    )
    private String name;

    @Column(
            nullable = false,
            unique = true,
            length = 150
    )
    private String email;

    @Column(
            nullable = false
    )
    private String password;

    @ManyToMany(mappedBy = "users", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @Builder.Default
    private Set<SplitGroup> groups = new HashSet<>();

    public void addGroup(SplitGroup splitGroup){
        this.groups.add(splitGroup);
        splitGroup.getUsers().add(this);
    }

    public void removeGroup(SplitGroup splitGroup){
        if (this.groups.contains(splitGroup)){
            this.groups.remove(splitGroup);
            splitGroup.getUsers().remove(this);
        }
    }
}
