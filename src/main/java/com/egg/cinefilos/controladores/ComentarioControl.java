package com.egg.cinefilos.controladores;

import com.egg.cinefilos.entidades.Comentario;
import com.egg.cinefilos.entidades.Usuario;
import com.egg.cinefilos.excepciones.ErrorServicio;
import com.egg.cinefilos.repositorios.RepUsuario;
import com.egg.cinefilos.servicios.ComentarioServicio;
import com.egg.cinefilos.servicios.PeliculaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @Autowired
    RepUsuario repUsuario;

    @PostMapping("pelicula/detalles/{idP}/nuevo")
    public String nuevoComentario(@PathVariable Long idP, @ModelAttribute("comentario") Comentario comentario, Authentication auth) {
        Usuario usuario = repUsuario.findByUsername(auth.getName()).orElse(null);
        comentario.setPelicula(peliculaSV.buscarPorId(idP).get());
        comentario.setUsuario(usuario);
        try {
            comenSV.crearComentario(comentario);
            return "redirect:/pelicula/detalles/{idP}";
        } catch (ErrorServicio e) {
            return "redirect:/error";
        }
    }
}
