package com.egg.cinefilos;

import com.egg.cinefilos.entidades.Roles;
import com.egg.cinefilos.entidades.Usuario;
import com.egg.cinefilos.entidades.Valoracion;
import com.egg.cinefilos.repositorios.RepUsuario;
import com.egg.cinefilos.repositorios.RepoValoracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CinefilosApplication {
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RepoValoracion repoValoracion;

	public static void main(String[] args) {
		SpringApplication.run(CinefilosApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(RepUsuario repUsuario) {
		return (args) -> {
			Usuario usuario = new Usuario();
			usuario.setContrasenia(passwordEncoder.encode("admin"));
			usuario.setUsername("admin2");
			usuario.setRol(Roles.ADMIN);

			//repUsuario.save(usuario);
		};
	}
}
