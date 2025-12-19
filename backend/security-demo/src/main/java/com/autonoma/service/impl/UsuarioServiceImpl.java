package com.autonoma.service.impl;

import com.autonoma.dto.request.UpdateRolRequest;
import com.autonoma.dto.request.UpdateUsuarioRequest;
import com.autonoma.dto.request.UsuarioRequest;
import com.autonoma.dto.response.MessageResponse;
import com.autonoma.dto.response.UserResponse;
import com.autonoma.dto.response.UsuarioResponse;
import com.autonoma.exception.ResourceNotFoundException;
import com.autonoma.model.entity.LogAuth;
import com.autonoma.model.entity.Personal;
import com.autonoma.model.entity.Rol;
import com.autonoma.model.entity.Usuario;
import com.autonoma.model.enums.Estado;
import com.autonoma.model.enums.TipoEventoLogin;
import com.autonoma.repository.LogAuthRepository;
import com.autonoma.repository.PersonalRepository;
import com.autonoma.repository.RolRepository;
import com.autonoma.repository.UsuarioRepository;
import com.autonoma.security.IpRateLimiter;
import com.autonoma.service.LogAuthService;
import com.autonoma.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonalRepository personalRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final IpRateLimiter ipRateLimiter;
    private final LogAuthRepository logAuthRepository;
    private final LogAuthService logAuthService;


    @Override
    public UsuarioResponse save(UsuarioRequest request) {
        Usuario usuario = mapToEntity(request);

        Usuario saved = usuarioRepository.save(usuario);

        return mapToResponse(saved);
    }

    @Override
    public UsuarioResponse update(Integer id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no existe."));


        Personal personal = personalRepository.findById(request.idPersonal())
                .orElseThrow(() -> new ResourceNotFoundException("Personal no existe."));

        Rol rol = rolRepository.findById(request.idRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no existe."));

        usuario.setPersonal(personal);
        usuario.setRol(rol);
        usuario.setUsuario(generarUsuario(personal));

        Usuario updated = usuarioRepository.save(usuario);
        return mapToResponse(updated);
    }

    @Override
    public List<UsuarioResponse> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UsuarioResponse findById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no existe."));
        return mapToResponse(usuario);
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioResponse restablecerContraseña (Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no existe."));

        Personal personal = usuario.getPersonal();
        String nuevaClave = generarContraseña(personal);

        usuario.setContraseña(passwordEncoder.encode(nuevaClave));
        usuarioRepository.save(usuario);

        return mapToResponse(usuario);
    }

    @Override
    public MessageResponse activarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no existe."));

        if(usuario.getEstado() == Estado.INACTIVO || usuario.getEstado() == Estado.BLOQUEADO)
        {
            usuario.setEstado(Estado.ACTIVO);
        }


        usuarioRepository.save(usuario);

        MessageResponse messageResponse = new MessageResponse("EXITO: Usuario activado.");

        return messageResponse;
    }


    @Override
    public MessageResponse desactivarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no existe."));

        if (usuario.getEstado() != Estado.INACTIVO){
            usuario.setEstado(Estado.INACTIVO);
        }

        usuarioRepository.save(usuario);

        MessageResponse messageResponse = new MessageResponse("EXITO: Usuario desactivado.");

        return messageResponse;
    }

    @Override
    public UserResponse showUsuario(Integer id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no existe."));

        Personal personal = new Personal();
        personalRepository.findById(usuario.getId());

        UserResponse response = new UserResponse(usuario.getUsuario(), generarContraseña(personal));

        return response;
    }

    @Override
    public MessageResponse cambiarRol(Integer id, UpdateRolRequest request) {

        //Personal personal = personalRepository.findById(id)
        //        .orElseThrow(() -> new ResourceNotFoundException("Personal no existe."));

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no existe."));

        Rol rol = rolRepository.findById(request.idRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no existe."));



        usuario.setRol(rol);
        Usuario updated = usuarioRepository.save(usuario);

        MessageResponse message = new MessageResponse("EXITO: Rol actualizado.");
        return message;
    }

    private Usuario mapToEntity(UsuarioRequest request) {
        Personal personal = personalRepository.findById(request.idPersonal())
                .orElseThrow(() -> new ResourceNotFoundException("Personal no existe."));

        Rol rol = rolRepository.findById(request.idRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no existe."));


        Usuario usuario = new Usuario();
        usuario.setPersonal(personal);
        usuario.setRol(rol);
        usuario.setUsuario(generarUsuario(personal));
        usuario.setContraseña(passwordEncoder.encode(generarContraseña(personal)));
        usuario.setEstado(Estado.ACTIVO);
        //usuario.setIntentosFallidos(0);

        return usuario;
    }

    private UsuarioResponse mapToResponse(Usuario usuario) {
        Personal p = usuario.getPersonal();
        String nombreCompleto = p.getNombre() + " " + p.getApellidoPaterno() + " " + p.getApellidoMaterno();

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getUsuario(),
                p.getCorreo(),
                usuario.getRol().getNombre(),
                usuario.getEstado().name()
                //nombreCompleto
        );
    }

    // generate automatic user
        public String generarUsuario(Personal p) {
            String inicialNombre = p.getNombre().substring(0, 1).toUpperCase();
            String apellido = p.getApellidoPaterno().toLowerCase();
            return inicialNombre + apellido;
        }

        // generate automatic password
        public String generarContraseña(Personal p) {
            String inicialApellido = p.getApellidoPaterno().substring(0, 1).toUpperCase();
            String dni = p.getDni();
            String celular = p.getCelular();
            String inicialNombre = p.getNombre().substring(0, 1).toUpperCase();

            return inicialApellido +
                    dni.substring(0, 3) +
                    celular.substring(celular.length() - 3) +
                    inicialNombre;
    }

}
