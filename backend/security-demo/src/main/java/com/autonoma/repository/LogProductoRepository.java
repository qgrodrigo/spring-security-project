package com.autonoma.repository;

import com.autonoma.model.entity.LogProducto;
import com.autonoma.model.enums.TipoEventoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LogProductoRepository extends JpaRepository<LogProducto, Long> {

    List<LogProducto> findByUsuarioId(Long idUsuario);

    List<LogProducto> findByProductoId(Long idProducto);

    List<LogProducto> findByTipoEvento(TipoEventoProducto tipoEvento);

    List<LogProducto> findByFecha(LocalDate fecha);
}
