package com.autonoma.service.impl;

import com.autonoma.dto.request.RolRequest;
import com.autonoma.dto.response.RolResponse;
import com.autonoma.model.Rol;
import com.autonoma.repository.RolRepository;
import com.autonoma.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    public RolResponse save(RolRequest request) {
        Rol rol = mapToEntity(request);
        Rol saved = rolRepository.save(rol);
        return mapToResponse(saved);
    }

    @Override
    public RolResponse update(Integer id, RolRequest request) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rol.setNombre(request.nombre());
        Rol updated = rolRepository.save(rol);
        return mapToResponse(updated);
    }

    @Override
    public List<RolResponse> findAll() {
        return rolRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public RolResponse findById(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return mapToResponse(rol);
    }

    @Override
    public void delete(Integer id) {
        rolRepository.deleteById(id);
    }

    private Rol mapToEntity(RolRequest request) {
        Rol rol = new Rol();
        rol.setNombre(request.nombre());
        return rol;
    }

    private RolResponse mapToResponse(Rol rol) {
        return new RolResponse(rol.getId(), rol.getNombre());
    }
}
