package com.autonoma.repository;

import com.autonoma.model.entity.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {

    // Buscar OTP válido por usuario y código
    Optional<OtpCode> findByUserIdAndOtpAndUsedFalse(Integer userId, String otp);

    // Opcional: limpiar OTPs expirados
    void deleteByExpiresAtBefore(LocalDateTime now);

}
