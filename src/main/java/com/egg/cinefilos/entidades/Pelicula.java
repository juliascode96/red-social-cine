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
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String director;

    @ElementCollection
    private Set<String> actores;

    private Integer duracion;
    private String genero;
    private Integer anio;
    private Integer valoracion;
    private Integer cantValoracion;

    public Pelicula(String titulo, String director, Set<String> actores, Integer duracion, String genero, Integer anio) {
        this.titulo = titulo;
        this.director = director;
        this.actores = actores;
        this.duracion = duracion;
        this.genero = genero;
        this.anio = anio;
    }
}
