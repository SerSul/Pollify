package ru.coursework.pollify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coursework.pollify.entity.security.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsernameAndEmail(String username, String email);


}
