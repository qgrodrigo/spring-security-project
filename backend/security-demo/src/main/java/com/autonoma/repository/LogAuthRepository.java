package com.autonoma.repository;

import com.autonoma.model.entity.LogAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAuthRepository extends JpaRepository<LogAuth, Long> {
}
