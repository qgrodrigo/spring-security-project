package com.autonoma.model.entity;


import com.autonoma.model.enums.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private Integer stock;

    private String color;

    private String talla;

    private LocalDateTime fechaCreacion;

    private String urlImg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = Estado.ACTIVO;
        }
        if (this.stock != null && this.stock <= 0) {
            this.estado = Estado.INACTIVO;
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (this.stock != null && this.stock <= 0) {
            this.estado = Estado.INACTIVO;
        } else {
            this.estado = Estado.ACTIVO;
        }
    }

}
