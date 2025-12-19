package com.autonoma.repository;

import com.autonoma.model.entity.LogAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogAuthRepository extends JpaRepository<LogAuth, Long> {
    Optional<LogAuth> findByTokenId(String tokenId);
    List<LogAuth> findByActiveTrue();
    Optional<LogAuth> findTopByUsuarioIdOrderByFechaDescHoraDesc(Integer idUsuario);
}
