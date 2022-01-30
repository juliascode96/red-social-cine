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

    private String username;
    private String contrasenia;
    private Double puntaje;

    @Enumerated(EnumType.STRING)
    private Role rol;

    @OneToMany
    private Set<Pelicula> peliculasFavoritas;

    @OneToMany
    private Set<Pelicula> peliculasPorVer;

    @OneToMany
    private Set<Usuario> seguidos;

    public Usuario(String usarname, String contrasenia) {
        this.username = usarname;
        this.contrasenia = contrasenia;
    }
}
