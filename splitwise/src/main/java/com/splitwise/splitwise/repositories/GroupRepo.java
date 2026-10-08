package com.splitwise.splitwise.repositories;

import com.splitwise.splitwise.entities.SplitGroup;
import com.splitwise.splitwise.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface GroupRepo extends JpaRepository<SplitGroup, String> {

    @Query(
            """
                SELECT g FROM SplitGroup g
                LEFT JOIN FETCH g.user u
                WHERE g.id = :groupId
            """
    )
    Optional<SplitGroup> findByIdWithMembers(String groupId);

    boolean existsByIdAndUser(String groupId, User user);
}
