package com.autonoma.service;

import com.autonoma.dto.request.MovimientoRequest;
import com.autonoma.dto.response.MovimientoResponse;

import java.util.List;

public interface MovimientoService {

    MovimientoResponse save(MovimientoRequest request);
    MovimientoResponse update(Integer id, MovimientoRequest request);
    List<MovimientoResponse> findAll();
    MovimientoResponse findById(Integer id);
    void delete(Integer id);
}
