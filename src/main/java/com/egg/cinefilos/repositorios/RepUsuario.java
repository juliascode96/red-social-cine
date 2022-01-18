package com.egg.cinefilos.repositorios;

import com.egg.cinefilos.entidades.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepUsuario extends CrudRepository<Usuario, Long> {

}
