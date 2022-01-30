package com.egg.cinefilos.controladores;

import com.egg.cinefilos.repositorios.RepUsuario;
import com.egg.cinefilos.repositorios.RepoPelicula;
import com.egg.cinefilos.repositorios.RepoValoracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainControl {
    @Autowired
    RepoPelicula repoPelicula;

    @Autowired
    RepoValoracion repoValoracion;

    @Autowired
    RepUsuario repUsuario;

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("peliculasNuevas", repoPelicula.findTop4ByOrderByIdDesc());
        model.addAttribute("mejorValoradas", repoValoracion.findTop4ByOrderByPromedioDesc());
        return "index";
    }

    /*
    @GetMapping("/error")
    public String error() {
        return "error";
    }

     */
}
