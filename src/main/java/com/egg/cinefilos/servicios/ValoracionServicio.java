package com.egg.cinefilos.servicios;

import com.egg.cinefilos.entidades.Pelicula;
import com.egg.cinefilos.entidades.Valoracion;
import com.egg.cinefilos.excepciones.ErrorServicio;
import com.egg.cinefilos.repositorios.RepoPelicula;
import com.egg.cinefilos.repositorios.RepoValoracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ValoracionServicio {
    @Autowired
    RepoValoracion repoValoracion;

    @Autowired
    RepoPelicula repopeli;

    public Valoracion guardarValoracion(Valoracion valoracion, Long idPelicula) {
        valoracion.setPelicula(repopeli.findById(idPelicula).get());
        return repoValoracion.save(valoracion);
    }

    public void borrarValoracion(Valoracion valoracion){
        repoValoracion.delete(valoracion);
    }

       @Transactional
    public Valoracion valorar(Valoracion valoracion, Long idPelicula) throws ErrorServicio {
        if(valoracion.getUltima() > 5 || valoracion.getUltima() < 1) {
            throw new ErrorServicio("Valoración no válida");
        }

        Optional<Pelicula> respuesta = repopeli.findById(idPelicula);

        if (respuesta.isPresent()) {
            Pelicula p1 = respuesta.get();
            Valoracion v1 = repoValoracion.findByPeliculaId(idPelicula);

            v1.setCantidad(v1.getCantidad()+1);
            v1.setTotal(v1.getTotal()+valoracion.getUltima());
            v1.setPromedio(v1.getTotal()/v1.getCantidad());

            return repoValoracion.save(v1);
        } else {
            throw new ErrorServicio("Pelicula no encontrada");
        }
    }
}
