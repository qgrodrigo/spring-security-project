package com.autonoma.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "personal")
public class Personal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    private String dni;

    private String celular;

    private String correo;

    private String urlimg;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechacreacion;

    @PrePersist
    public void prePersist() {
        this.fechacreacion = LocalDateTime.now();
    }
}