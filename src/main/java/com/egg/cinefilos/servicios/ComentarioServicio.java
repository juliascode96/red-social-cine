package com.egg.cinefilos.servicios;

import com.egg.cinefilos.entidades.Comentario;
import com.egg.cinefilos.entidades.Respuesta;
import com.egg.cinefilos.excepciones.ErrorServicio;
import com.egg.cinefilos.repositorios.RepoComentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioServicio {
    @Autowired
    RepoComentario comenRepo;

    @Autowired
    RespuestaServicio respuestaServicio;

    @Transactional
    public void crearComentario(Comentario comentario) throws ErrorServicio {
        if(comentario.getTexto().isEmpty()) {
            throw new ErrorServicio("El comentario no puede estar vacío");
        } else {
            comenRepo.save(comentario);
        }
    }

    public Optional<Comentario> buscarPorId(Long id) {
        return comenRepo.findById(id);
    }

    public List<Comentario> buscarPorPelicula(Long pId) {
        return comenRepo.findByPeliculaId(pId);
    }

    @Transactional
    public boolean borrarComentario(Long id) {
       try {
           Optional<Comentario> respuesta = comenRepo.findById(id);
           if(respuesta.isPresent()) {
               Comentario c = respuesta.get();
               List<Respuesta> respuestas = respuestaServicio.buscarPorComentario(c.getId());
               for(Respuesta r : respuestas) {
                   respuestaServicio.borrarRespuesta(r.getId());
               }
           }
           comenRepo.deleteById(id);
           return true;
       } catch (Exception e) {
           return false;
       }
    }
}
