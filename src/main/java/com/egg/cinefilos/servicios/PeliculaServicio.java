package com.egg.cinefilos.servicios;

import com.egg.cinefilos.entidades.Pelicula;
import com.egg.cinefilos.excepciones.ErrorServicio;
import com.egg.cinefilos.repositorios.RepoPelicula;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeliculaServicio {

    @Autowired
    private RepoPelicula repopeli;

    public void validar(String titulo, String director,
            Set<String> actores, Integer duracion, String genero,
            Integer anio, Integer valoracion, Integer cantValoracion) throws ErrorServicio {

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El título de la película no puede ser nulo");
        }

        if (director == null || director.isEmpty()) {
            throw new ErrorServicio("El director de la película no puede ser nulo");
        }

        if (actores == null || actores.isEmpty()) {
            throw new ErrorServicio("Complete con los actores de la película");
        }

        if (duracion == null || duracion<0 ) {
            throw new ErrorServicio("La duración de la película no puede ser nula o ser menor a 0");
        }

        if (genero == null || genero.isEmpty()) {
            throw new ErrorServicio("El género de la película no puede ser nulo");
        }

        if (anio == null || anio<0) {
            throw new ErrorServicio("El año de la película no puede ser nulo");
        }

        if (valoracion == null || valoracion<0) {
            throw new ErrorServicio("La valoración de la película no puede ser nula");
        }

        if (cantValoracion == null || valoracion <0) {
            throw new ErrorServicio("La cantidad de valoraciones no puede ser nula");
        }
    }

    @Transactional
    public void CreacionPelicula(String titulo, String director,
            Set<String> actores, Integer duracion, String genero, Integer anio, Integer valoracion, Integer cantValoracion) throws ErrorServicio {

        validar(titulo, director, actores, duracion, genero, anio, valoracion, cantValoracion);
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(titulo);
        pelicula.setDirector(director);
        pelicula.setActores(actores);
        pelicula.setDuracion(duracion);
        pelicula.setGenero(genero);
        pelicula.setAnio(anio);
        pelicula.setValoracion(valoracion);
        pelicula.setCantValoracion(cantValoracion);
        

        repopeli.save(pelicula);
    }

    @Transactional
    public void modificarPelicula(Long id, String titulo, String director,
            Set<String> actores, Integer duracion, String genero, Integer anio, Integer valoracion, Integer cantValoracion) throws ErrorServicio {

        validar(titulo, director, actores, duracion, genero, anio, valoracion, cantValoracion);

        Optional<Pelicula> respuesta = repopeli.findById(id);
        if (respuesta.isPresent()) {

            Pelicula pelicula = respuesta.get();
             pelicula.setTitulo(titulo);
        pelicula.setDirector(director);
        pelicula.setActores(actores);
        pelicula.setDuracion(duracion);
        pelicula.setGenero(genero);
        pelicula.setAnio(anio);
        pelicula.setValoracion(valoracion);
        pelicula.setCantValoracion(cantValoracion);

            repopeli.save(pelicula);
        } else {
            throw new ErrorServicio("No se pudo encontrar el id");
        }
    }

     private Iterable<Pelicula> listarLibros() {
        return repopeli.findAll();
    }

    @Transactional
    public void eliminarLibro(Long id) throws ErrorServicio {
        Optional<Pelicula> respuesta = repopeli.findById(id);
        if (respuesta.isPresent()) {
            Pelicula pelicula = respuesta.get();
            repopeli.deleteById(id);
        } else {
        }
        throw new ErrorServicio("No se pudo encontrar una pelicula con ese id");
    }

}

