package com.egg.cinefilos.repositorios;

import com.egg.cinefilos.entidades.Pelicula;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoPelicula extends CrudRepository<Pelicula, Long> {
    Pelicula findByTitulo(String titulo);
    Pelicula findByTituloContaining(String titulo);
    List<Pelicula> findByDirector(String director);
    List<Pelicula> findByDirectorContaining(String director);
    List<Pelicula> findByGenero(String genero);
}
