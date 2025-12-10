package com.autonoma;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class SecurityDemoApplication implements CommandLineRunner {

    private final JavaMailSender mailSender;

	public static void main(String[] args) {
		SpringApplication.run(SecurityDemoApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("rodrigo.quispe.gil@gmail.com");
        message.setSubject("Hola Mundo");
        message.setText("Hola Mundo desde Spring Boot 🚀");

        mailSender.send(message);

        System.out.println("Correo enviado con éxito!");
    }
}
