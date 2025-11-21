package com.autonoma.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "idpersonal", nullable = false)
    private Personal personal;

    @ManyToOne
    @JoinColumn(name = "idrol", nullable = false)
    private Rol rol;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(nullable = false)
    private String contraseña;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;


    public enum Estado {
        Activo, Desactivo
    }

    /**
    @PrePersist
    public void generarUsuario() {
        if (this.personal != null && this.usuario == null) {
            String inicialNombre = personal.getNombre().substring(0, 1).toUpperCase();
            String apellido = personal.getApellidoPaterno().toLowerCase();
            this.usuario = inicialNombre + apellido;
        }
    }

    @PrePersist
    public void generarCredenciales() {
        if (personal != null) {
            String inicialNombre = personal.getNombre().substring(0, 1).toUpperCase();
            String primerApellido = personal.getApellidoPaterno().toLowerCase();
            this.usuario = inicialNombre + primerApellido;

            String inicialApellido = personal.getApellidoPaterno().substring(0, 1).toUpperCase();
            String dni = personal.getDni();
            String celular = personal.getCelular();
            String clave = inicialApellido +
                    dni.substring(0, 3) +
                    celular.substring(celular.length() - 3) +
                    inicialNombre;
            this.contraseña = clave;
        }

        if (this.estado == null) {
            this.estado = Estado.Activo;
        }
    }
    **/

}
