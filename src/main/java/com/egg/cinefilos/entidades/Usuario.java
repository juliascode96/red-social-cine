package com.egg.cinefilos.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usarname;
    private String contrasenia;

    @OneToMany
    private Set<Pelicula> peliculasFavoritas;

    @OneToMany
    private Set<Pelicula> peliculasPorVer;

    public Usuario(String usarname, String contrasenia) {
        this.usarname = usarname;
        this.contrasenia = contrasenia;
    }
}
