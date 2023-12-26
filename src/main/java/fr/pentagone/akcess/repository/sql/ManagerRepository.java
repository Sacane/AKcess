package fr.pentagone.akcess.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Optional<Manager> findByName(String name);
    Optional<Manager> findByLogin(String login);
}
