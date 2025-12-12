package com.autonoma.service.impl;

import com.autonoma.config.IpUtils;
import com.autonoma.dto.request.LoginRequest;
import com.autonoma.dto.request.OtpVerifyRequest;
import com.autonoma.dto.response.LoginResponse;
import com.autonoma.exception.*;
import com.autonoma.model.entity.OtpCode;
import com.autonoma.model.entity.Personal;
import com.autonoma.model.entity.Usuario;
import com.autonoma.model.enums.Estado;
import com.autonoma.model.enums.TipoEventoLogin;
import com.autonoma.repository.UsuarioRepository;
import com.autonoma.security.JwtService;
import com.autonoma.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final LogAuthService logAuthService;
    private final OtpCodeService otpCodeService;
    private final EmailService emailService;
    private final PersonalService personalService;


    /*
    @Override
    public LoginResponse login(LoginRequest request, HttpServletRequest httpRequest) {

        String ip = IpUtils.getClientIp(httpRequest);

        Usuario usuario = usuarioRepository.findByUsuario(request.usuario()).orElse(null);
        if (usuario == null) {
            logAuthService.logAuth(null, request.usuario(), ip, TipoEventoLogin.LOGIN_FAILED);
            throw new UsuarioNoExisteException("Usuario no existe.");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.usuario(), request.contraseña())
            );

            if (usuario.getEstado() == Estado.BLOQUEADO) {
                throw new UsuarioBloqueadoException("Usuario bloqueado por múltiples intentos fallidos.");
            }

            if (usuario.getEstado() == Estado.INACTIVO) {
                throw new UsuarioInactivoException("Usuario inactivo.");
            }

            usuario.setIntentosFallidos(0);
            usuarioRepository.save(usuario);

            String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
            long expiresIn = jwtService.getExpirationMinutes();

            logAuthService.logAuth(usuario.getId(), request.usuario(), ip, TipoEventoLogin.LOGIN_SUCCESS);

            return new LoginResponse(
                    token,
                    expiresIn,
                    usuario.getPersonal().getId(),
                    usuario.getPersonal().getNombre(),
                    usuario.getPersonal().getApellidoPaterno(),
                    usuario.getRol().getNombre()
            );

        } catch (BadCredentialsException ex) {

            int intentos = usuario.getIntentosFallidos() + 1;
            usuario.setIntentosFallidos(intentos);

            if (intentos >= 3) {
                usuario.setEstado(Estado.BLOQUEADO);
                usuarioRepository.save(usuario);

                logAuthService.logAuth(usuario.getId(), request.usuario(), ip, TipoEventoLogin.LOGIN_FAILED);
                throw new UsuarioBloqueadoException("Usuario bloqueado por múltiples intentos fallidos.");
            }

            usuarioRepository.save(usuario);

            logAuthService.logAuth(usuario.getId(), request.usuario(), ip, TipoEventoLogin.LOGIN_FAILED);
            throw new CredencialesInvalidasException("Usuario o contraseña incorrectos.");
        }
    }
    */

    @Override
    public LoginResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        String ip = IpUtils.getClientIp(httpRequest);

        Usuario usuario = usuarioRepository.findByUsuario(request.usuario())
                .orElseThrow(() -> new UsuarioNoExisteException("Usuario no existe."));

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.usuario(), request.contraseña())
            );

            if (usuario.getEstado() == Estado.BLOQUEADO) {
                throw new UsuarioBloqueadoException("Usuario bloqueado.");
            }
            if (usuario.getEstado() == Estado.INACTIVO) {
                throw new UsuarioInactivoException("Usuario inactivo.");
            }

            usuario.setIntentosFallidos(0);
            usuarioRepository.save(usuario);


            // Generar OTP y enviar al correo
            //if ()

            OtpCode otpCode = otpCodeService.generateOtp(usuario.getPersonal().getId());

            emailService.sendOtpEmail(usuario.getPersonal().getCorreo(), otpCode.getOtp());

            logAuthService.logAuth(usuario.getId(), request.usuario(), ip, TipoEventoLogin.LOGIN_SUCCESS);

            // Respuesta: login pendiente de verificación OTP
            return new LoginResponse(
                    null, 0,
                    usuario.getPersonal().getId(),
                    usuario.getPersonal().getNombre(),
                    usuario.getPersonal().getApellidoPaterno(),
                    usuario.getRol().getNombre(),
                    "OTP enviado, pendiente de verificación"
            );

        } catch (BadCredentialsException ex) {
            manejarIntentosFallidos(usuario, request.usuario(), ip);
            throw new CredencialesInvalidasException("Usuario o contraseña incorrectos.");
        }
    }

    @Override
    public LoginResponse verifyOtp(OtpVerifyRequest request, HttpServletRequest httpRequest) {

        String ip = IpUtils.getClientIp(httpRequest);

        boolean valid = otpCodeService.validateOtp(request.idUsuario(), request.otp());
        if (!valid) {
            logAuthService.logAuth(request.idUsuario(), "user", ip, TipoEventoLogin.VERIFY_OTP_FAILED);
            throw new CredencialesInvalidasException("OTP inválido o expirado.");
            //logAuthService.logAuth(request.idUsuario(), , ip, TipoEventoLogin.VERIFY_OTP_SUCCES);
        }

        Usuario usuario = usuarioRepository.findById(request.idUsuario())
                .orElseThrow(() -> new UsuarioNoExisteException("Usuario no existe."));

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getUsuario())
                .password(usuario.getContraseña())
                .roles(usuario.getRol().getNombre())
                .build();

        String token = jwtService.generateToken(userDetails);
        long expiresIn = jwtService.getExpirationMinutes();

        logAuthService.logAuth(usuario.getId(), usuario.getUsuario(), ip, TipoEventoLogin.VERIFY_OTP_SUCCES);


        return new LoginResponse(
                token, expiresIn,
                usuario.getPersonal().getId(),
                usuario.getPersonal().getNombre(),
                usuario.getPersonal().getApellidoPaterno(),
                usuario.getRol().getNombre(),
                "Login exitoso..."
        );
    }


    private void manejarIntentosFallidos(Usuario usuario, String username, String ip) {
        int intentos = usuario.getIntentosFallidos() + 1;
        usuario.setIntentosFallidos(intentos);

        if (intentos >= 3) {
            usuario.setEstado(Estado.BLOQUEADO);
        }
        usuarioRepository.save(usuario);
        logAuthService.logAuth(usuario.getId(), username, ip, TipoEventoLogin.LOGIN_FAILED);
    }

}
