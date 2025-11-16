package com.autonoma.service.impl;

import com.autonoma.dto.PersonalRequest;
import com.autonoma.dto.PersonalResponse;
import com.autonoma.model.Personal;
import com.autonoma.repository.PersonalRepository;
import com.autonoma.service.PersonalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalServiceImpl implements PersonalService {

    private final PersonalRepository personalRepository;


    @Override
    public PersonalResponse save(PersonalRequest request) {
        Personal personal = mapToEntity(request);
        Personal personalSaved = personalRepository.save(personal);

        return mapToResponse(personalSaved);
    }

    @Override
    public PersonalResponse update(Integer id, PersonalRequest request) {
        Personal personal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));

        personal.setNombre(request.nombre());
        personal.setApellidoPaterno(request.apellidoPaterno());
        personal.setApellidoMaterno(request.apellidoMaterno());
        personal.setDni(request.dni());
        personal.setCelular(request.celular());
        personal.setCorreo(request.correo());
        personal.setUrlimg(request.urlimg());
        Personal updated = personalRepository.save(personal);
        return mapToResponse(updated);
    }

    @Override
    public List<PersonalResponse> findAll() {
        return personalRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PersonalResponse findById(Integer id) {
        Personal personal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));
        return mapToResponse(personal);
    }

    @Override
    public void delete(Integer id) {
        personalRepository.deleteById(id);
    }

    private Personal mapToEntity(PersonalRequest request) {
        Personal personal = new Personal();
        personal.setNombre(request.nombre());
        personal.setApellidoPaterno(request.apellidoPaterno());
        personal.setApellidoMaterno(request.apellidoMaterno());
        personal.setDni(request.dni());
        personal.setCelular(request.celular());
        personal.setCorreo(request.correo());
        personal.setUrlimg(request.urlimg());
        return personal;
    }

    private PersonalResponse mapToResponse(Personal personal) {
        return new PersonalResponse(
                personal.getId(),
                personal.getNombre(),
                personal.getApellidoPaterno(),
                personal.getApellidoMaterno(),
                personal.getDni(),
                personal.getCelular(),
                personal.getCorreo(),
                personal.getUrlimg(),
                personal.getFechacreacion()
        );
    }
}
