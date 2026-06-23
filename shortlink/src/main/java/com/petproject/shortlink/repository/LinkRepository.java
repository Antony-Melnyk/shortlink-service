package com.petproject.shortlink.repository;

import com.petproject.shortlink.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {

    Optional<LinkEntity> findByShortCode(String shortCode);

    @Modifying // JPQL --> Entity
    @Query("""
            UPDATE LinkEntity l
            SET l.clickCount = l.clickCount + 1
            WHERE l.shortCode = :shortCode
            """)
    int incrementClickCountByShortCode(String shortCode);

}