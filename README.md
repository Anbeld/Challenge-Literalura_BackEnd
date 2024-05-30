# Literalura
Biblioteca potenciada por la API: **[Gutendex](https://gutendex.com/ "Gutendex")**

Permite realizar consultas especializadas en libros y autores. Los libros y autores consultados son almacenados en la base de datos.

## Índice
- [Modo de uso](#modo-de-uso)
  - [Menú inicial](#menú-inicial)
  - [Buscar libros por nombre](#buscar-libros-por-nombre)
  - [Buscar libros por autor](#buscar-libros-por-autor)
  - [Mostrar libros registrados](#mostrar-libros-registrados)
  - [Mostrar autores registrados](#mostrar-autores-registrados)
  - [Mostrar autores vivos en determinado año](#mostrar-autores-vivos-en-determinado-año)
  - [Mostrar libros por idioma](#mostrar-libros-por-idioma)
  - [Top 10 libros más descargados](#top-10-libros-más-descargados)

## Modo de uso
### Menú inicial
Funcionalidades:
- Selección opciones del menú:
- Cerrar programa

![Menu inicial](https://github.com/Anbeld/Challenge-Literalura_BackEnd/assets/147835151/a543e0db-c6a2-4709-98e6-30e625fac5f4)

### Buscar libros por nombre
Proceso:
- Usuario:
  - Ingresar nombre del libro a buscar o un fragmento de este.
- Programa:
  - Consulta si el nombre del libro coincide con alguno de los libros registrados en la base de datos.
    - Si existe:
      - Muestra el libro en la terminal.
    - Si no existe:
      - Realiza una consulta a la API Gutendex, en la cual consulta si existe un libro que contenga en su título el fragmento de nombre suministrado por el usuario.
        - Si existe:
          - Lo muestra en la terminal y lo guarda en la base de datos.
        - Si no existe:
          - Informa que no se ha encontrado ningún libro con el nombre suministrado.
  
![Buscar libros por autor](https://github.com/Anbeld/Challenge-Literalura_BackEnd/assets/147835151/b876fb64-09f1-435d-a7e8-571b73f490be)

### Buscar libros por autor
Proceso:
- Usuario:
  - Ingresar nombre del autor a buscar o un fragmento de este.
  - Elegir si se desea realizar la consulta en la base de datos o en la API Gutendex.
- Programa:
  - Consulta en base a la elección del usuario: 
    - Base de datos.
      - Revisa si existe el autor.
        - Si existe:
          - Lo muestra en la terminal.
        - Si no existe:
          - Informa al usuario que no se ha encontrado ningún autor con ese nombre.
![Autor no encontrado - Base de datos](https://github.com/Anbeld/Challenge-Literalura_BackEnd/assets/147835151/d2fe4f8e-0c78-4af1-aba3-f64cda23fd66)
    - Gutendex:
      - Revisa si existe algún autor que en su nombre contenga el fragmento de nombre ingresado por el usuario.
       - Si existe:
         - Lo muestra en la terminal y lo guarda en la base de datos.
       - Si no existe:
         - Informa al usuario que no se ha encontrado ningún autor con ese nombre.
![Autor encontrado - Gutendex](https://github.com/Anbeld/Challenge-Literalura_BackEnd/assets/147835151/a0216a41-3b31-4296-92c8-40aeb0a18f2f)

### Mostrar libros registrados
Proceso:
- Programa:
  - Muestra en la terminal el listado de todos los libros consultados previamente.
![Libros registrados](https://github.com/Anbeld/Challenge-Literalura_BackEnd/assets/147835151/2f982233-9fb6-4faf-b702-d42ee91584a5)

### Mostrar autores registrados:
Proceso:
- Programa:
  - Muestra en la terminal el listado de todos los autores consultados previamente.
![Autores registrados](https://github.com/Anbeld/Challenge-Literalura_BackEnd/assets/147835151/c75519ff-0e50-42d5-a6ca-c946828ed690)

### Mostrar autores vivos en determinado año
Proceso:
- Usuario:
  - Ingresa año en el que el autor a consultar estaba vivo.
- Programa:
  - Consulta en la base de datos todos los autores vivos en el año ingresado por el usuario.
    - Si existe al menos uno lo muestra en la terminal.
    - Si no existe ningún autor que cumpla la condición muestra un mensaje informando que no se encontró ningún autor. 
![Autores vivos en determinado año](https://github.com/Anbeld/Challenge-Literalura_BackEnd/assets/147835151/50d2b2e5-a5b2-401b-8d74-22daea73f42a)

### Mostrar libros por idioma
Proceso:
- Usuario:
  - Seleccionar el idioma por el que desea realizar la consulta.
- Programa:
  - Consulta en la base de datos todos los libros esten en el idioma seleccionado por el usuario.
    - Si existe al menos uno lo muestra en terminal.
    - Si no existe ningún libro que cumpla la condición muestra un mensaje informando que no se encontró ningún libro. 
![Libros por idioma](https://github.com/Anbeld/Challenge-Literalura_BackEnd/assets/147835151/47207f95-7c69-4ac8-88f2-6739faef6072)

### Top 10 libros más descargados
Proceso:
- Programa:
  - Consulta en Gutendex los 10 libros más descargados.
    - Si el libro no existe lo guarda en la base de datos.
    - Muestra el resultado obtenido de la consulta.
![image](https://github.com/Anbeld/Challenge-Literalura_BackEnd/assets/147835151/cdfb7a1d-4d8c-4864-a9d5-9b7db3d1d208)
   

