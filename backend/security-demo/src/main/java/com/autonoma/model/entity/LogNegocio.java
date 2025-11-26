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
@Table(name = "LOG_NEGOCIO")
public class LogNegocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accion;       // CREATE_INSPECTION, UPDATE_ORDER
    private String tabla;        // INSPECTION, ORDER
    private Long idUsuario;
    private LocalDateTime fechaHora;
    private String detalles;

}
