package com.autonoma.service.impl;

import com.autonoma.dto.request.PersonalRequest;
import com.autonoma.dto.response.MessageResponse;
import com.autonoma.dto.response.PersonalResponse;
import com.autonoma.exception.ResourceNotFoundException;
import com.autonoma.model.entity.Personal;
import com.autonoma.model.entity.Usuario;
import com.autonoma.model.enums.Estado;
import com.autonoma.repository.PersonalRepository;
import com.autonoma.repository.RolRepository;
import com.autonoma.repository.UsuarioRepository;
import com.autonoma.service.PersonalService;
import com.autonoma.service.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalServiceImpl implements PersonalService {

    private final PersonalRepository personalRepository;
    private final RolRepository rolRepository;
    private final UsuarioHelperService usuarioHelperService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    @Override
    public MessageResponse save(PersonalRequest request) {
        Personal personal = mapToEntity(request);
        Personal personalSaved = personalRepository.save(personal);

        Usuario usuario = new Usuario();
        usuario.setPersonal(personal);
        usuario.setRol(rolRepository.findByNombre("BASIC"));
        usuario.setUsuario(usuarioHelperService.generarUsuario(personal));
        usuario.setContraseña(passwordEncoder.encode( usuarioHelperService.generarContraseña(personal)));
        usuario.setEstado(Estado.INACTIVO);
        usuario.setIntentosFallidos(0);

        usuarioRepository.save(usuario);

        MessageResponse messageResponse = new MessageResponse("EXITO: Personal registrado correctamente.");
        return messageResponse;
    }

    @Override
    public MessageResponse update(Integer id, PersonalRequest request) {

        Personal personal = personalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personal no existe."));

        personal.setNombre(request.nombre());
        personal.setApellidoPaterno(request.apellidoPaterno());
        personal.setApellidoMaterno(request.apellidoMaterno());
        personal.setDni(request.dni());
        personal.setCelular(request.celular());
        personal.setCorreo(request.correo());
        personal.setUrlimg(request.urlimg());

        Personal updated = personalRepository.save(personal);

        String mensaje;
        if (updated != null){
            mensaje = "EXITO: Personal actualizado correctamente.";
        }else{
            mensaje = "ERROR: ocurrió un error al actualizar datos del personal.";
        }

        MessageResponse messageResponse = new MessageResponse(mensaje);

        return messageResponse;
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
                .orElseThrow(() -> new ResourceNotFoundException("Personal no existe."));
        return mapToResponse(personal);
    }


    @Transactional
    public void delete(Integer id) {
        Personal personal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));

        personalRepository.save(personal);
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
                //personal.getEstado().name(),
                personal.getFechacreacion()
        );
    }
}
