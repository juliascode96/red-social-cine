package com.egg.cinefilos.controladores;

import com.egg.cinefilos.entidades.Comentario;
import com.egg.cinefilos.entidades.Foto;
import com.egg.cinefilos.entidades.Pelicula;
import com.egg.cinefilos.excepciones.ErrorServicio;
import com.egg.cinefilos.servicios.ComentarioServicio;
import com.egg.cinefilos.servicios.FotoServicio;
import com.egg.cinefilos.servicios.PeliculaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
@RequestMapping("/pelicula")
public class PeliculaControl {

    @Autowired
    PeliculaServicio peliculaServicio;

    @Autowired
    ComentarioServicio comenSV;

    @Autowired
    FotoServicio fotosv;

    @GetMapping("/nueva")
    public String nuevaPeliculaForm(Model model) {
        Pelicula pelicula = new Pelicula();
        model.addAttribute("pelicula", pelicula);
        return "nueva_pelicula";
    }

    @PostMapping("/creada")
    public String crearPelicula(@ModelAttribute("pelicula") Pelicula pelicula, MultipartFile archivo) {
        try {
            Foto foto = fotosv.guardar(archivo);
            pelicula.setFoto(foto);
            peliculaServicio.CreacionPelicula(pelicula);
            return "redirect:/pelicula/todas";
        }catch (ErrorServicio e) {
            return "redirect:/error";
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

    @PostMapping("/{id}")
    public String editarPelicula(@PathVariable Long id, Model model, @ModelAttribute("pelicula") Pelicula p1) {
            try {
                peliculaServicio.modificarPelicula(p1.getId(), p1.getTitulo(), p1.getDirector(), p1.getActores(), p1.getDuracion(), p1.getGenero(), p1.getAnio(), p1.getValoracion(), p1.getCantValoracion(), (MultipartFile) p1.getFoto());
                return "redirect:/pelicula/todas";
            } catch (ErrorServicio e) {
                return "redirect:/error";
            }
    }

    @GetMapping("/borrar/{id}")
    public String borrarPelicula(@PathVariable Long id) {
        try {
            peliculaServicio.eliminarPelicula(id);
            return "redirect:/pelicula/todas";
        } catch (ErrorServicio e) {
            return "redirect:/error";
        } finally {
            return "redirect:/pelicula/todas";
        }
    }

    @GetMapping("/detalles/{id}")
    public String detallesPelicula(@PathVariable Long id, Model model) {
        Pelicula p = peliculaServicio.buscarPorId(id).get();
        model.addAttribute("pelicula", p);
        ArrayList<Comentario> comentarios = (ArrayList<Comentario>) comenSV.buscarPorPelicula(id);
        model.addAttribute("comentarios", comentarios);
        Comentario c = new Comentario();
        model.addAttribute("comentario", c);
        return "pelicula";
    }


}
