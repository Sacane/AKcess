package fr.pentagone.akcess.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsById(Integer integer);

    void deleteById(Integer integer);

    Optional<User> findById(Integer userId);
}
