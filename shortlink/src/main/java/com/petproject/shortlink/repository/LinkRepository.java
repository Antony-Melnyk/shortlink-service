package com.petproject.shortlink.repository;

import com.petproject.shortlink.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {
}