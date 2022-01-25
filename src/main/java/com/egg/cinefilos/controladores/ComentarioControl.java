package com.egg.cinefilos.controladores;

import com.egg.cinefilos.entidades.Comentario;
import com.egg.cinefilos.excepciones.ErrorServicio;
import com.egg.cinefilos.servicios.ComentarioServicio;
import com.egg.cinefilos.servicios.PeliculaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ComentarioControl {
    @Autowired
    ComentarioServicio comenSV;

    @Autowired
    PeliculaServicio peliculaSV;

    @PostMapping("pelicula/detalles/{idP}/nuevo")
    public String nuevoComentario(@PathVariable Long idP, @ModelAttribute("comentario") Comentario comentario) {
        comentario.setPelicula(peliculaSV.buscarPorId(idP).get());
        try {
            comenSV.crearComentario(comentario);
            return "redirect:/pelicula/detalles/{idP}";
        } catch (ErrorServicio e) {
            return "error";
        }
    }
}
