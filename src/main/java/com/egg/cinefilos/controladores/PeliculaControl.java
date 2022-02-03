package com.egg.cinefilos.controladores;

import com.egg.cinefilos.entidades.*;
import com.egg.cinefilos.excepciones.ErrorServicio;
import com.egg.cinefilos.repositorios.RepUsuario;
import com.egg.cinefilos.repositorios.RepoPelicula;
import com.egg.cinefilos.repositorios.RepoValoracion;
import com.egg.cinefilos.servicios.ComentarioServicio;
import com.egg.cinefilos.servicios.FotoServicio;
import com.egg.cinefilos.servicios.PeliculaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pelicula")
public class PeliculaControl {

    @Autowired
    PeliculaServicio peliculaServicio;

    @Autowired
    RepoPelicula repoPelicula;

    @Autowired
    ComentarioServicio comenSV;

    @Autowired
    FotoServicio fotosv;

    @Autowired
    RepoValoracion repoValoracion;

    @Autowired
    RepUsuario repUsuario;


    @GetMapping("/nueva")
    public String nuevaPeliculaForm(Model model) {
        Pelicula pelicula = new Pelicula();
        model.addAttribute("pelicula", pelicula);
        return "nueva_pelicula";
    }

    @PostMapping("/creada")
    public String crearPelicula(@ModelAttribute("pelicula") @RequestParam String titulo, @RequestParam String director,
                                @RequestParam Integer duracion, @RequestParam String sinopsis, @RequestParam String genero, @RequestParam Integer anio, @RequestParam MultipartFile archivo) {
        Pelicula pelicula = new Pelicula();
        try {
            Foto foto = fotosv.guardar(archivo);
            pelicula.setFoto(foto);
            peliculaServicio.CreacionPelicula(titulo, director, sinopsis, duracion, genero, anio, archivo);
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
    public String editarPelicula(@PathVariable Long id, @ModelAttribute("pelicula") Pelicula p1, MultipartFile archivo) {
        try {
            peliculaServicio.modificarPelicula(p1.getId(), p1.getTitulo(), p1.getDirector(), p1.getSinopsis() ,p1.getDuracion(), p1.getGenero(), p1.getAnio(),archivo);
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
    public String detallesPelicula(@PathVariable Long id, Model model, Authentication auth) {
        if(auth==null) {
            List<Pelicula> favoritas = new ArrayList<>();
            List<Pelicula> porVer = new ArrayList<>();
            model.addAttribute("favoritas", favoritas);
            model.addAttribute("porVer", porVer);
        } else {
            Usuario usuario = repUsuario.findByUsername(auth.getName()).orElse(null);
            model.addAttribute("favoritas", usuario.getPeliculasFavoritas());
            model.addAttribute("porVer", usuario.getPeliculasPorVer());
        }

        Pelicula p = peliculaServicio.buscarPorId(id).get();
        model.addAttribute("pelicula", p);
        ArrayList<Comentario> comentarios = (ArrayList<Comentario>) comenSV.buscarPorPelicula(id);
        model.addAttribute("comentarios", comentarios);
        Comentario c = new Comentario();
        model.addAttribute("comentario", c);
        Valoracion v = repoValoracion.findByPeliculaId(id);
        model.addAttribute("valoracion", v);
        ValoracionComentario vc = new ValoracionComentario();
        model.addAttribute("valoracionC", vc);
        return "pelicula";
    }

    @GetMapping("/{genero}")
    public String mostrarPorGenero(@PathVariable String genero, Model model) {
        model.addAttribute("peliculas", peliculaServicio.buscarPorGenero(genero));
        return "peliculas_por_genero";
    }

    @GetMapping("/ver-todas/{ref}")
    public String listaDePeliculas(@PathVariable Integer ref, Model model) {
        if(ref == 0) {
            model.addAttribute("peliculas", peliculaServicio.mostrarTodas());
        } else if(ref == 1) {
            model.addAttribute("peliculas", repoPelicula.findByOrderByTituloAsc());
        } else if(ref == 2){
            model.addAttribute("peliculas", repoPelicula.findByOrderByDirectorAsc());
        }
        return "lista_peliculas";
    }

}
