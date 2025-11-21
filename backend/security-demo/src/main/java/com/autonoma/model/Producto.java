package com.autonoma.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Producto {

    private Integer id;

    private String nombre;

    private Integer stock;

    private String color;

    private String talla;

    private LocalDateTime fechaCreacion;

    private String urlImg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEnum estado;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

}
