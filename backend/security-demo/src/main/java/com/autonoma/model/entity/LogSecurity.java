package com.autonoma.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LOG_SECURITY")
public class LogSecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accion;       // LOGIN_SUCCESS, LOGIN_FAILURE, ACCESS_DENIED
    private String usuario;
    private String ip;
    private LocalDateTime fechaHora;
}
