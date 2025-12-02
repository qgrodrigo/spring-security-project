package com.autonoma.model.entity;


import com.autonoma.model.enums.Estado;
import com.autonoma.model.enums.EstadoProducto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    private BigDecimal precio;

    @Column(unique = true)
    private String urlImg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoProducto estado;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoProducto.DISPONIBLE;
        }
        if (this.stock != null && this.stock <= 0) {
            this.estado =  EstadoProducto.AGOTADO;
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (this.stock != null && this.stock <= 0) {
            this.estado = EstadoProducto.AGOTADO;
        } else {
            this.estado = EstadoProducto.DISPONIBLE;
        }
    }

}
