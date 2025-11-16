package com.autonoma.service.impl;

import com.autonoma.dto.UsuarioRequest;
import com.autonoma.dto.UsuarioResponse;
import com.autonoma.model.Personal;
import com.autonoma.model.Rol;
import com.autonoma.model.Usuario;
import com.autonoma.repository.PersonalRepository;
import com.autonoma.repository.RolRepository;
import com.autonoma.repository.UsuarioRepository;
import com.autonoma.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonalRepository personalRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UsuarioResponse save(UsuarioRequest request) {
        Usuario usuario = mapToEntity(request);
        Usuario saved = usuarioRepository.save(usuario);
        return mapToResponse(saved);
    }

    @Override
    public UsuarioResponse update(Integer id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Personal personal = personalRepository.findById(request.idPersonal())
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));

        Rol rol = rolRepository.findById(request.idRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

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
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapToResponse(usuario);
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioResponse restablecerContraseña (Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Personal personal = usuario.getPersonal();
        String nuevaClave = generarContraseña(personal);

        usuario.setContraseña(passwordEncoder.encode(nuevaClave));
        usuarioRepository.save(usuario);

        return mapToResponse(usuario);
    }

    @Override
    public UsuarioResponse desactivarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEstado(Usuario.Estado.Desactivo);
        usuarioRepository.save(usuario);

        return mapToResponse(usuario);
    }

    private Usuario mapToEntity(UsuarioRequest request) {
        Personal personal = personalRepository.findById(request.idPersonal())
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));

        Rol rol = rolRepository.findById(request.idRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setPersonal(personal);
        usuario.setRol(rol);
        usuario.setUsuario(generarUsuario(personal));
        usuario.setContraseña(passwordEncoder.encode(generarContraseña(personal)));
        usuario.setEstado(Usuario.Estado.Activo);

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
                usuario.getEstado().name(),
                usuario.getContraseña(),
                nombreCompleto
        );
    }

    // generate automatic user
    private String generarUsuario(Personal p) {
        String inicialNombre = p.getNombre().substring(0, 1).toUpperCase();
        String apellido = p.getApellidoPaterno().toLowerCase();
        return inicialNombre + apellido;
    }

    // generate automatic password
    private String generarContraseña(Personal p) {
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
