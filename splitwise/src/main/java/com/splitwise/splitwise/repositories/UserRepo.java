package com.splitwise.splitwise.repositories;

import com.splitwise.splitwise.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    List<User> findByEmailIn(List<String> emails);
}
