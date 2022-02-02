package com.egg.cinefilos.servicios;

import com.egg.cinefilos.entidades.Comentario;
import com.egg.cinefilos.entidades.Pelicula;
import com.egg.cinefilos.entidades.Respuesta;
import com.egg.cinefilos.entidades.Foto;
import com.egg.cinefilos.entidades.Valoracion;
import com.egg.cinefilos.excepciones.ErrorServicio;
import com.egg.cinefilos.repositorios.RepoPelicula;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;

import com.egg.cinefilos.repositorios.RepoValoracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PeliculaServicio {

    @Autowired
    private RepoPelicula repopeli;

    @Autowired
    private RepoValoracion repoValoracion;

    @Autowired
    ComentarioServicio comenSV;

    @Autowired
    RespuestaServicio respuestaServicio;

    @Autowired
    FotoServicio fotoServicio;

    public void validar(String titulo, String director, Integer duracion, String genero,
                        Integer anio) throws ErrorServicio {

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El título de la película no puede ser nulo");
        }

        if (director == null || director.isEmpty()) {
            throw new ErrorServicio("El director de la película no puede ser nulo");
        }

       /*
        if (actores == null || actores.isEmpty()) {
            throw new ErrorServicio("Complete con los actores de la película");
        }

        */

        if (duracion == null || duracion<0 ) {
            throw new ErrorServicio("La duración de la película no puede ser nula o ser menor a 0");
        }

        if (genero == null || genero.isEmpty()) {
            throw new ErrorServicio("El género de la película no puede ser nulo");
        }

        if (anio == null || anio<0) {
            throw new ErrorServicio("El año de la película no puede ser nulo");
        }
    }

    @Transactional
    public void CreacionPelicula (String titulo, String director, String sinopsis, Integer duracion, String genero, Integer anio, MultipartFile archivo) throws ErrorServicio {

        validar(titulo, director, duracion, genero, anio);
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(titulo);
        pelicula.setDirector(director);
        pelicula.setDuracion(duracion);
        pelicula.setSinopsis(sinopsis);
        pelicula.setGenero(genero);
        pelicula.setAnio(anio);

        Foto foto = fotoServicio.guardar(archivo);
        pelicula.setFoto(foto);
        if(pelicula.getSinopsis().length()>299) {
            pelicula.setExtracto(pelicula.getSinopsis().substring(0, 300).concat("..."));
        } else {
            pelicula.setExtracto(pelicula.getSinopsis());
        }

        repopeli.save(pelicula);

        Valoracion v = new Valoracion(0d,0d,0d,0d, pelicula);
        repoValoracion.save(v);
    }

    @Transactional
    public Pelicula modificarPelicula(Long id, String titulo, String director, String sinopsis,
                                      Integer duracion, String genero, Integer anio, MultipartFile archivo) throws ErrorServicio {

        validar(titulo, director, duracion, genero, anio);

        Optional<Pelicula> respuesta = repopeli.findById(id);
        if (respuesta.isPresent()) {
            Pelicula pelicula = respuesta.get();
            pelicula.setTitulo(titulo);
            pelicula.setDirector(director);
            pelicula.setSinopsis(sinopsis);

            pelicula.setDuracion(duracion);
            pelicula.setGenero(genero);
            pelicula.setAnio(anio);

            String idFoto = null;
            if (pelicula.getFoto()==null) {
                Foto foto = fotoServicio.guardar(archivo);
                pelicula.setFoto(foto);
            }

        if(pelicula.getSinopsis().length()>299) {
            pelicula.setExtracto(pelicula.getSinopsis().substring(0, 300).concat("..."));
        } else {
            pelicula.setExtracto(pelicula.getSinopsis());
        }
            return repopeli.save(pelicula);
        } else {
            throw new ErrorServicio("No se pudo encontrar el id");
        }
    }

    public Optional<Pelicula> buscarPorId(Long id) {
        Optional<Pelicula> respuesta = repopeli.findById(id);
        return respuesta;
    }

    @Transactional
    public void eliminarPelicula(Long id) throws ErrorServicio {
        Optional<Pelicula> respuesta = repopeli.findById(id);
        if (respuesta.isPresent()) {
            Pelicula pelicula = respuesta.get();

            List<Comentario> comentarios = comenSV.buscarPorPelicula(id);
            for(Comentario c : comentarios) {
                comenSV.borrarComentario(c.getId());
            }
            repoValoracion.deleteById(repoValoracion.findByPeliculaId(id).getId());
            repopeli.deleteById(pelicula.getId());
        } else {
        }
        throw new ErrorServicio("No se pudo encontrar una pelicula con ese id");
    }

    public Iterable<Pelicula> mostrarTodas() {
        return repopeli.findAll();
    }

    public Pelicula buscarPorTitulo(String titulo) {
        return repopeli.findByTitulo(titulo);
    }

    public List<Pelicula> buscarPorPalabraClave(String palabra) {
        return repopeli.findByTituloContaining(palabra);
    }

    public List<Pelicula> buscarPorDirector(String director) {
        return repopeli.findByDirectorContaining(director);
    }

    public List<Pelicula> buscarPorGenero(String genero) {
        return repopeli.findByGeneroContaining(genero);
    }


}



