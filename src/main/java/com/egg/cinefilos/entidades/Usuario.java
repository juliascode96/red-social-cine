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

    @Enumerated(EnumType.STRING)
    private Roles rol;

    @OneToMany
    private Set<Pelicula> peliculasFavoritas;

    @OneToMany
    private Set<Pelicula> peliculasPorVer;

    public Usuario(String usarname, String contrasenia) {
        this.username = usarname;
        this.contrasenia = contrasenia;
    }
}
