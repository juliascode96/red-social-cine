package com.egg.cinefilos.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainControl {
    @GetMapping("/")
    public String inicio() {
        return "index.html";
    }
}
