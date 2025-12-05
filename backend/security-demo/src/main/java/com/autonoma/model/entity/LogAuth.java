package com.autonoma.model.entity;

import com.autonoma.model.enums.TipoEventoLogin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log_auth")
public class LogAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = true)
    private Usuario usuario;

    @Column(name = "usuario_ingresado")
    private String usuarioIngresado;

    private LocalDate fecha;

    private LocalTime hora;

    private String ipAddress;

    @Enumerated(EnumType.STRING)
    private TipoEventoLogin tipoEvento;

}
