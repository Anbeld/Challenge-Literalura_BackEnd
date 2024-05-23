package com.aluracursos.Challenge_Literalura_BackEnd.model;

import com.aluracursos.Challenge_Literalura_BackEnd.service.ConvierteDatos;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_autor",
            joinColumns = @JoinColumn(name = "libros_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autor = new ArrayList<>();

    @Column(unique = true)
    private String titulo;

    private List<String> idioma;
    private Integer numeroDeDescargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idiomas();
        this.numeroDeDescargas = datosLibro.numeroDescargas();
    }

    @Override
    public String toString() {
        List<String> nombreAutor = new ArrayList<>();
        if (!autor.isEmpty()) {
            for (Autor autor : autor) {
                nombreAutor.add(autor.getNombre());
            }
        } else {
            nombreAutor.add("N/A");
        }

        return """
                ------------LIBRO-----------
                 Título: %s
                 Autor: %s
                 Idioma: %s
                 Número de descargas: %d
                ----------------------------
                """.formatted(titulo, nombreAutor, idioma, numeroDeDescargas);
    }

    public Long getId() {
        return id;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<String> getIdioma() {
        return idioma;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }
}
