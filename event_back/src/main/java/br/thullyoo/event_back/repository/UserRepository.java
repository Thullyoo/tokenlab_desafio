package br.thullyoo.event_back.repository;

import br.thullyoo.event_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByDocument(String document);

    @Query("SELECT CASE WHEN COUNT(e1) > 0 " +
            "THEN true ELSE false " +
            "END FROM User u " +
            "LEFT JOIN u.events e1 WHERE u.id = :userId " +
            "AND ((e1.startTime < :endTime " +
            "AND e1.endTime > :startTime))")
    boolean hasOverlappingEvents(@Param("userId") UUID userId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT CASE WHEN COUNT(e1) > 0 " +
            "THEN true ELSE false " +
            "END FROM User u " +
            "LEFT JOIN u.events e1 WHERE u.id = :userId " +
            "AND e1.id <> :eventId " +
            "AND ((e1.startTime < :endTime " +
            "AND e1.endTime > :startTime))")
    boolean hasOverlappingEventsByEvent(
            @Param("userId") UUID userId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("eventId") Long eventId
    );
}
