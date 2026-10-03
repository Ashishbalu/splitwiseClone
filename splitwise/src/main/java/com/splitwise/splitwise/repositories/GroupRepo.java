package com.splitwise.splitwise.repositories;

import com.splitwise.splitwise.entities.SplitGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<SplitGroup, String> {
}
