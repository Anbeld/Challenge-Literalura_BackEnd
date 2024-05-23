package com.aluracursos.Challenge_Literalura_BackEnd.service;

import com.aluracursos.Challenge_Literalura_BackEnd.model.*;
import com.aluracursos.Challenge_Literalura_BackEnd.repository.AutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AutorService {
    private final String URL_BASE = "https://gutendex.com/books/?";
    private final String URL_SEARCH = "search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private AutorRepository repository;

    public AutorService(AutorRepository autorRepository) {
        this.repository = autorRepository;
    }

    public AutorService() {
    }

    @Transactional
    public void buscarLibrosPorTitulo(String titulo) {
        Optional<Libro> libroBaseDatos = repository.obtenerLibroPorNombreAutor(titulo);

        // Informa los resultados obtenidos en la consulta a la base de datos
        if (libroBaseDatos.isPresent()) {
            System.out.println("Libro encontrado en base de datos");
            System.out.println(libroBaseDatos.get());
        } else {
            System.out.println("""
                    ----------------------------------------
                    Libro no registrado en la base de datos
                    ----------------------------------------
                            Consultando en GUTENDEX
                    ----------------------------------------
                    """);
            String url = URL_BASE + URL_SEARCH + titulo.replaceAll(" ", "+");

            Datos datosBusqueda = realizarConsultaAPI(url);

            Optional<DatosLibro> libroAPI = datosBusqueda.libros().stream()
                    .filter(e -> e.titulo().toUpperCase().contains(titulo.toUpperCase()))
                    .findFirst();

            // Informa los resultados obtenidos en la consulta a la API
            if (libroAPI.isPresent()) {
                crearLibroUsuario(libroAPI);
            } else {
                System.out.println("""
                        ----------------------------------------
                            Libro no encontrado en Gutendex
                        ----------------------------------------
                        """);
            }

            // Verifica la existencia del libro y lo guarda en la base de datos
        }
    }


    public void buscarAutorPorNombreBaseDatos(String nombreAutor) {
        Optional<Autor> autor = repository.obtenerAutorPorNombre(nombreAutor);
        if (autor.isPresent()){
            System.out.println(autor);
        } else {
            System.out.println("""
                    ----------------------------------------
                    Autor no encontrado en la base de datos
                    ----------------------------------------
                    """);
        }
    }

    public void buscarAutorPorNombreGutendex(String nombreAutor) {
        System.out.println("""
                ----------------------------------------
                       Consultando en GUTENDEX
                ----------------------------------------
                """);

        String url = URL_BASE + URL_SEARCH + nombreAutor.replaceAll(" ", "+");
        Datos datosBusqueda = realizarConsultaAPI(url);

        Optional<DatosAutor> datosAutor = datosBusqueda.libros().stream()
                .flatMap(l -> l.autor().stream().filter(a -> a.nombre().toUpperCase().contains(nombreAutor.toUpperCase())))
                .findFirst();

        if (datosAutor.isPresent()) {
            Optional<Autor> autorExistente = repository.obtenerAutor(datosAutor.get().nombre(), datosAutor.get().fechaNacimiento(), datosAutor.get().fechaFallecimiento());
            if (autorExistente.isPresent()){
                System.out.println(autorExistente);
            } else {
                Autor autor = new Autor(datosAutor.get());
                System.out.println(autor);
                repository.save(autor);
            }
        } else {
            System.out.println("""
                    ----------------------------------------
                        Autor no encontrado en Gutendex
                    ----------------------------------------
                    """);
        }
    }

    public void mostrarLibrosRegistrados() {
        List<Libro> libros = new ArrayList<>();
        List<Autor> autores = repository.findAll();
        for (Autor autor : autores) {
            libros.addAll(repository.obtenerLibrosPorAutorID(autor.getId()));
        }
        if (!libros.isEmpty()) {
            libros.forEach(System.out::println);
        } else {
            System.out.println("""
                    ----------------------------------------
                         No ha registrado ningún libro
                    ----------------------------------------
                    """);
        }
    }

    public void mostrarAutoresRegistrados() {
        List<Autor> autores = repository.findAll();
        if (!autores.isEmpty()) {
            autores.forEach(System.out::println);
        } else {
            System.out.println("""
                    ----------------------------------------
                         No ha registrado ningún autor
                    ----------------------------------------
                    """);
        }
    }

    public void mostrarAutoresVivosPorFecha(Integer anio) {
        List<Autor> autores = repository.obtenerAutoresVivosPorFecha(anio);

        if (!autores.isEmpty()) {
            autores.forEach(System.out::println);
        } else {
            System.out.println("""
                    ----------------------------------------
                    No ha encontrado ningún autor vivo en %d
                    ----------------------------------------
                    """.formatted(anio));
        }
    }

    public void mostrarLibrosPorIdioma(List<String> diminutivoIdioma) {
        List<Libro> libros = repository.obtenerLibroPorIdioma(diminutivoIdioma);

        if (!libros.isEmpty()) {
            libros.forEach(System.out::println);
        } else {
            System.out.println("""
                    ----------------------------------------
                    No ha encontrado ningún libro en "%s"
                    ----------------------------------------
                    """.formatted(diminutivoIdioma.getFirst()));
        }
    }

    public void mostrarTop10LibrosDescargados() {
        Datos datos = realizarConsultaAPI(URL_BASE);

        List<DatosLibro> top10LibrosConsulta = datos.libros().stream()
                .sorted(Comparator.comparingInt(DatosLibro::numeroDescargas).reversed())
                .limit(10)
                .collect(Collectors.toList());

        buscarTop10Libros(top10LibrosConsulta);
    }

    private void crearLibroUsuario(Optional<DatosLibro> datosLibro) {
            // Revisa si alguno de los autores del libro buscado ya existe
            List<Autor> autorExistente = datosLibro.stream()
                    .flatMap(libro -> libro.autor().stream()
                            .map(a -> repository.obtenerAutor(a.nombre(), a.fechaNacimiento(), a.fechaFallecimiento())
                                    .orElse(null))
                    )
                    .filter(a -> a != null)
                    .collect(Collectors.toList());

            List<Autor> autorAPI = datosLibro.stream()
                    .flatMap(e -> e.autor().stream().map(a -> new Autor(a)))
                    .collect(Collectors.toList());

            List<Autor> autoresComunes = new ArrayList<>();
            for (Autor autor : autorExistente) {
                List<Autor> coincidencias = autorAPI.stream()
                        .filter(a -> a.getNombre().equals(autor.getNombre()))
                        .filter(a -> a.getFechaNacimiento().equals(autor.getFechaNacimiento()))
                        .filter(a -> a.getFechaFallecimiento().equals(autor.getFechaFallecimiento()))
                        .collect(Collectors.toList());

                autoresComunes.addAll(coincidencias);
            }

            // Remueve todos los autores existentes
            autorAPI.removeAll(autoresComunes);

            Libro libro = new Libro(datosLibro.get());

            autorAPI.forEach(e -> e.addLibro(libro));
            autorExistente.forEach(e -> e.addLibro(libro));

            // Se guarda y actualiza la información de los autores y libros en la base de datos
            repository.saveAll(autorExistente);
            repository.saveAll(autorAPI);

            // Se presenta la información del libro buscado
            System.out.println(libro);


    }

    private void buscarTop10Libros(List<DatosLibro> top10LibrosConsulta){
        List<Libro> top10LibrosGeneral = new ArrayList<>();

        // Revisa si alguno de los autores del libro buscado ya existe y que este no se repita en la lista
        Set<String> nombresAutorExistente = new HashSet<>();
        List<Autor> autorExistente = top10LibrosConsulta.stream()
                .flatMap(libro -> libro.autor().stream()
                        .map(a -> repository.obtenerAutor(a.nombre(), a.fechaNacimiento(), a.fechaFallecimiento())
                                .orElse(null))
                )
                .filter(a -> a != null)
                .filter(a -> nombresAutorExistente.add(a.getNombre()))
                .collect(Collectors.toList());

        // Revisa que el autor no se repita en la lista
        Set<String> nombresAutorAPI = new HashSet<>();
        List<Autor> autorAPI = top10LibrosConsulta.stream()
                .flatMap(l -> l.autor().stream())
                .filter(a -> nombresAutorAPI.add(a.nombre()))
                .map(a -> new Autor(a))
                .collect(Collectors.toList());

        List<Autor> autoresComunes = new ArrayList<>();
        for (Autor autor : autorExistente) {
            List<Autor> coincidencias = autorAPI.stream()
                    .filter(a -> a.getNombre().equals(autor.getNombre()))
                    .filter(a -> a.getFechaNacimiento().equals(autor.getFechaNacimiento()))
                    .filter(a -> a.getFechaFallecimiento().equals(autor.getFechaFallecimiento()))
                    .collect(Collectors.toList());

            autoresComunes.addAll(coincidencias);
        }

        // Remueve todos los autores existentes
        autorAPI.removeAll(autoresComunes);

        // Agrega los libros a los autores existentes y muestra el libro en pantalla.
        for (Autor autor : autorExistente) {
            List<Libro> librosAutorExistente = top10LibrosConsulta.stream()
                    .filter(l -> l.autor().stream()
                            .anyMatch(a -> a.nombre().equals(autor.getNombre())
                                    && Objects.equals(a.fechaNacimiento(), autor.getFechaNacimiento())
                                    && Objects.equals(a.fechaFallecimiento(), autor.getFechaFallecimiento())))
                    .map(Libro::new)
                    .collect(Collectors.toList());

            List<Libro> librosExistentes = new ArrayList<>();
            for (Libro libro : librosAutorExistente) {
                Optional<Libro> libroExistente = repository.buscarLibroPorTituloNumeroDescargasIdioma(libro.getTitulo(), libro.getNumeroDeDescargas(), libro.getIdioma());
                if (libroExistente.isPresent()){
                    librosExistentes.add(libro);
                    top10LibrosGeneral.add(libroExistente.get());
                }
            }

            librosAutorExistente.removeAll(librosExistentes);

            librosAutorExistente.forEach(l -> autor.addLibro(l));
            top10LibrosGeneral.addAll(librosAutorExistente);
        }

        // Agrega los libros a los nuevos autores
        for (Autor autor : autorAPI) {
            List<Libro> librosAutorAPI = top10LibrosConsulta.stream()
                    .filter(l -> l.autor().stream()
                            .anyMatch(a -> a.nombre().equals(autor.getNombre())
                                    && Objects.equals(a.fechaNacimiento(), autor.getFechaNacimiento())
                                    && Objects.equals(a.fechaFallecimiento(), autor.getFechaFallecimiento())))
                    .map(Libro::new)
                    .collect(Collectors.toList());

            librosAutorAPI.forEach(l -> autor.addLibro(l));
            top10LibrosGeneral.addAll(librosAutorAPI);
        }

        top10LibrosGeneral.sort(Comparator.comparing(Libro::getNumeroDeDescargas).reversed());
        top10LibrosGeneral.forEach(System.out::println);
        repository.saveAll(autorExistente);
        repository.saveAll(autorAPI);
    }

    // Crea la url, obtiene la información de la API Gutendex y la convierte en un objeto de la clase Datos
    private Datos realizarConsultaAPI(String url) {
        String json = ConsumoAPI.obtenerDatos(url);
        return conversor.obtenerDatos(json, Datos.class);
    }
}
