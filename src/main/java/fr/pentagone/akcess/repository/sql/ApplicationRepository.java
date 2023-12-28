package fr.pentagone.akcess.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Optional<Application> findByLabel(String label);
}
