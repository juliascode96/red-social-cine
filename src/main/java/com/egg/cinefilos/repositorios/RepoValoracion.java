package com.egg.cinefilos.repositorios;

import com.egg.cinefilos.entidades.Valoracion;
import org.springframework.data.repository.CrudRepository;

public interface RepoValoracion extends CrudRepository<Valoracion, Long> {
    Valoracion findByPeliculaId(Long id);
}
