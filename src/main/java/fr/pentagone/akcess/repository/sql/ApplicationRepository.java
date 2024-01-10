package fr.pentagone.akcess.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Optional<Application> findByLabel(String label);

    @Query("SELECT a FROM Application a LEFT JOIN FETCH a.users WHERE a.id = :appId")
    Optional<Application> findByIdWithUsers(@Param("appId") int appId);
}
