package com.egg.cinefilos.controladores;

import com.egg.cinefilos.entidades.Usuario;
import com.egg.cinefilos.excepciones.ErrorServicio;
import com.egg.cinefilos.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioControl {
    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/registrar")
    public String nuevoUsuario(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);

        return "nuevo_usuario";
    }

    @PostMapping("/registrar/nuevo")
    public String registroDeUsuario(@ModelAttribute("usuario") Usuario usuario) {
        try {
            usuarioServicio.guardarUsuario(usuario);
            return "redirect:/";
        } catch (ErrorServicio e) {
            System.out.println(e);
            return "redirect:/error";
        }
    }

    @GetMapping("/iniciar")
    public String iniciarSesion(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);

        return "iniciar_sesion";
    }
}
