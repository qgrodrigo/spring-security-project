package com.autonoma.service;

import com.autonoma.dto.RolRequest;
import com.autonoma.dto.RolResponse;

import java.util.List;

public interface RolService {

    RolResponse save(RolRequest request);
    RolResponse update(Integer id, RolRequest request);
    List<RolResponse> findAll();
    RolResponse findById(Integer id);
    void delete(Integer id);
}
