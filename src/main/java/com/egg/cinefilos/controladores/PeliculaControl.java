package com.egg.cinefilos.controladores;

import com.egg.cinefilos.entidades.Pelicula;
import com.egg.cinefilos.excepciones.ErrorServicio;
import com.egg.cinefilos.servicios.PeliculaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/pelicula")
public class PeliculaControl {

    @Autowired
    PeliculaServicio peliculaServicio;

    @GetMapping("/nueva")
    public String nuevaPeliculaForm(Model model) {
        Pelicula pelicula = new Pelicula();
        model.addAttribute("pelicula", pelicula);
        return "nueva_pelicula";
    }

    @PostMapping("/creada")
    public String crearPelicula(@ModelAttribute("pelicula") Pelicula pelicula) {
        try {
            peliculaServicio.CreacionPelicula(pelicula);
            return "redirect:/pelicula/todas";
        }catch (ErrorServicio e) {
            return "error.html";
        }
    }

    @GetMapping("/todas")
    public String mostrarTodas(Model model) {
        model.addAttribute("peliculas", peliculaServicio.mostrarTodas());
        return "peliculas";
    }

    @GetMapping("/editar/{id}")
    public String editarPeliculaForm(@PathVariable Long id, Model model) {
        Pelicula pelicula = peliculaServicio.buscarPorId(id).get();
        model.addAttribute("pelicula", pelicula);
        return "editar_pelicula";
    }

    @PostMapping("/editar")
    public String editarPelicula(@PathVariable Long id, Model model, @ModelAttribute("pelicula") Pelicula pelicula) {
        Optional<Pelicula> respuesta = peliculaServicio.buscarPorId(id);

        if(respuesta.isPresent()) {
            Pelicula p1 = respuesta.get();
            try {
                peliculaServicio.modificarPelicula(p1.getId(), p1.getTitulo(), p1.getDirector(), p1.getActores(), p1.getDuracion(), p1.getGenero(), p1.getAnio(), p1.getValoracion(), p1.getCantValoracion());
                return "redirect:/pelicula/todas";
            } catch (ErrorServicio e) {
                return "error";
            }

        }
        return "redirect:/pelicula/todas";
    }


}
