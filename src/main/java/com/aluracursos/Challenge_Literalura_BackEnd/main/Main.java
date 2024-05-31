package com.aluracursos.Challenge_Literalura_BackEnd.main;

import com.aluracursos.Challenge_Literalura_BackEnd.repository.AutorRepository;
import com.aluracursos.Challenge_Literalura_BackEnd.service.AutorService;

import java.util.*;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private AutorService service;
    private AutorRepository repository;

    public Main(AutorRepository repository) {
        this.repository = repository;
    }

    public void mostrarMenu() {
        service = new AutorService(repository);
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros por nombre
                    2 - Buscar libros por autor
                    3 - Mostrar libros registrados
                    4 - Mostrar autores registrados
                    5 - Mostrar autores vivos en determinado año
                    6 - Mostrar libros por idioma
                    7 - Top 10 libros más descargados

                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                    buscarLibrosPorTitulo();
                    break;
                case 2:
                    buscarAutorPorNombre();
                    break;
                case 3:
                    mostrarLibrosRegistrados();
                    break;
                case 4:
                    mostrarAutoresRegistrados();
                    break;
                case 5:
                    mostrarAutoresVivosPorFecha();
                    break;
                case 6:
                    mostrarLibrosPorIdioma();
                    break;
                case 7:
                    mostrarTop10LibrosDescargados();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void mostrarTop10LibrosDescargados() {
        service.mostrarTop10LibrosDescargados();
    }


    private void buscarLibrosPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        String titulo = scanner.nextLine();
        service.buscarLibrosPorTitulo(titulo);
    }

    private void buscarAutorPorNombre() {
        System.out.println("Ingrese el nombre del autor que desea buscar");
        String nombreAutor = scanner.nextLine();

        int opcion = 0;
        while (opcion == 0) {
            try {
                System.out.println("""
                        Desea realizar la consulta en:
                        1 - Base de datos
                        2 - Gutendex
                        """);
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NoSuchElementException | NumberFormatException e) {
                System.out.println("Ingrese una opcion válida");
                opcion = 0;
            }
        }

        if (opcion == 1){
            service.buscarAutorPorNombreBaseDatos(nombreAutor);
        } else {
            service.buscarAutorPorNombreGutendex(nombreAutor);
        }
    }

    private void mostrarLibrosRegistrados() {
        service.mostrarLibrosRegistrados();
    }

    private void mostrarAutoresRegistrados() {
        service.mostrarAutoresRegistrados();
    }

    private void mostrarAutoresVivosPorFecha() {
        Integer anio = null;
        while (anio == null) {
            try {
                System.out.println("Ingrese el año en el que estaba vivo el autor(es)");
                anio = Integer.parseInt(scanner.nextLine());
            } catch (NoSuchElementException | NumberFormatException e) {
                System.out.println("Ingrese una opción válida");
                anio = null;
            } catch (IllegalStateException e) {
                System.out.println("Scanner cerrado inesperadamente. Saliendo...");
                break;
            }
        }
        service.mostrarAutoresVivosPorFecha(anio);
    }

    private void mostrarLibrosPorIdioma() {
        int opcion = 0;
        while (opcion == 0) {
            try {
                System.out.println("""
                        Ingrese el idioma del libro que desa buscar
                        1 - Español (es)
                        2 - Inglés (en)
                        3 - Portugués (pt)
                        4 - Francés (fr)
                        5 - Otro
                        """);
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NoSuchElementException | NumberFormatException e) {
                System.out.println("Ingrese una opcion válida");
                opcion = 0;
            }
        }

        List<String> diminutivoIdioma = new ArrayList<>();
        switch (opcion) {
            case 1:
                diminutivoIdioma.add("es");
                break;
            case 2:
                diminutivoIdioma.add("en");
                break;
            case 3:
                diminutivoIdioma.add("pt");
                break;
            case 4:
                diminutivoIdioma.add("fr");
                break;
            case 5:
                System.out.println("Ingrese el diminitivo del idioma deseado. Ejemplo: español = es");
                String idioma = scanner.nextLine();
                diminutivoIdioma.add(idioma);
            default:
                System.out.println("Opción inválida");
        }
        service.mostrarLibrosPorIdioma(diminutivoIdioma);
    }
}
