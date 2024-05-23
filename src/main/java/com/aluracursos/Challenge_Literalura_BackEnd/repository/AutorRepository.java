package com.aluracursos.Challenge_Literalura_BackEnd.repository;

import com.aluracursos.Challenge_Literalura_BackEnd.model.Autor;
import com.aluracursos.Challenge_Literalura_BackEnd.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query(value = "SELECT l FROM Autor a JOIN a.libros l WHERE l.titulo ILIKE %:nombreLibro%")
    Optional<Libro> obtenerLibroPorNombreAutor(String nombreLibro);

    @Query(value = "SELECT a FROM Autor a WHERE a.nombre ILIKE %:nombreAutor% " +
            "AND a.fechaNacimiento = %:fechaNacimientoAutor% " +
            "AND a.fechaFallecimiento = %:fechaFallecimientoAutor%")
    Optional<Autor> obtenerAutor(String nombreAutor, Integer fechaNacimientoAutor, Integer fechaFallecimientoAutor);

    @Query(value = "SELECT a.libros FROM Autor a WHERe a.id = :autorId")
    List<Libro> obtenerLibrosPorAutorID(Long autorId);

    @Query(value = "SELECT a FROM Autor a WHERE :anio BETWEEN a.fechaNacimiento AND a.fechaFallecimiento")
    List<Autor> obtenerAutoresVivosPorFecha(Integer anio);

    @Query(value = "SELECT l FROM Autor a JOIN a.libros l WHERE l.idioma IN %:diminutivoIdioma%")
    List<Libro> obtenerLibroPorIdioma(List<String> diminutivoIdioma);

    @Query(value = "SELECT a FROM Autor a WHERE a.nombre ILIKE %:nombreAutor%")
    Optional<Autor> obtenerAutorPorNombre(String nombreAutor);

    @Query(value = "SELECT l FROM Autor a JOIN a.libros l WHERE l.titulo ILIKE %:nombreLibro% AND l.numeroDeDescargas = :numeroDeDescargas AND l.idioma IN %:diminutivoIdioma%")
    Optional<Libro> buscarLibroPorTituloNumeroDescargasIdioma(String nombreLibro, Integer numeroDeDescargas, List<String> diminutivoIdioma);
}
