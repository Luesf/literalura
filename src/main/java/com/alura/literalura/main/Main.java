package com.alura.literalura.main;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Libro;
import com.alura.literalura.model.Resultados;
import com.alura.literalura.repository.RepositorioAutor;
import com.alura.literalura.repository.RepositorioLibro;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvertirData;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private Scanner teclado = new Scanner(System.in);
    private ConvertirData convertirData = new ConvertirData();
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private RepositorioLibro repositorioLibro;
    private RepositorioAutor repositorioAutor;

    List<Libro> libros;
    List<Autor> autores;

    public Main(RepositorioLibro repositorioLibro, RepositorioAutor repositorioAutor){
        this.repositorioLibro = repositorioLibro;
        this.repositorioAutor = repositorioAutor;
    }

    public void Menu(){
        var menu = """
                Elija una accion que desea efectuar:
                1 - Buscar libro por titulo.
                2 - Mostrar lista de libros registrados.
                3 - Mostrar lista de autores registrados.
                4 - Mostrar lista de autores vivos en un determinado anio.
                5 - Mostrar lista de libros por lenguaje.
                0 - Salir del programa.
                """;

        var seleccion = -1;
        while (seleccion != 0){
            System.out.println(menu);
            seleccion = teclado.nextInt();
            teclado.nextLine();
            switch (seleccion){
                case 1:
                    libroPorTitulo();
                    break;
                case 2:
                    librosRegistrados();
                    break;
                case 3:
                    autoresRegistrados();
                    break;
                case 4:
                    autoresVivosPorAnio();
                    break;
                case 5:
                    libroPorLenguaje();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opcion invalida, intente de nuevo...");
                    break;
            }
        }
    }

    private void libroPorTitulo(){
        System.out.println("Por favor ingrese el titulo del libro: ");
        String busqueda = teclado.nextLine();
        var json = consumoAPI.getData(busqueda.replace(" ", "%20"));
        var data = convertirData.getData(json, Resultados.class);
        if (data.resultados().isEmpty()){
            System.out.println("Libro no encontrado...");
        }else {
            DatosLibro datosLibro = data.resultados().get(0);
            Libro libro = new Libro(datosLibro);
            Autor autor = new Autor().obtenerPrimerAutor(datosLibro);
            guardarDatos(libro, autor);
        }
    }

    private void guardarDatos(Libro libro, Autor autor){
        Optional<Libro> libroBusqueda = repositorioLibro.findByTituloContains(libro.getTitulo());
        if (libroBusqueda.isPresent()){
            System.out.println("Este libro ya fue buscado y guardado...");
        }else {
            try {
                repositorioLibro.save(libro);
                System.out.println(libro.getTitulo());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }

        Optional<Autor> autorBusqueda = repositorioAutor.findByNombre(autor.getNombre());
        if (autorBusqueda.isPresent()){
            System.out.println("Este autor ya fue buscado y guardado...");
        }else {
            try {
                repositorioAutor.save(autor);
                System.out.println(autor.getNombre());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
    }

    private void librosRegistrados(){
        System.out.println("Libros Registrados:\n");
        libros = repositorioLibro.findAll();
        libros.stream().sorted(Comparator.comparing(Libro::getTitulo)).forEach(System.out::println);
    }

    private void autoresRegistrados(){
        System.out.println("Autores Registrados:\n");
        autores = repositorioAutor.findAll();
        autores.stream().sorted(Comparator.comparing(Autor::getNombre)).forEach(System.out::println);
    }

    private void autoresVivosPorAnio(){
        System.out.println("Autores Vivos por Anio, ingrese el anio que desea buscar: ");
        Integer anio = Integer.valueOf(teclado.nextLine());
        autores = repositorioAutor.findByFechaNacimientoLessThanEqualAndFechaMuerteGreaterThanEqual(anio, anio);
        if (autores.isEmpty()){
            System.out.println("Autores no encontrados...");
        }else {
            autores.stream().sorted(Comparator.comparing(Autor::getNombre)).forEach(System.out::println);
        }
    }

    private void libroPorLenguaje(){
        System.out.println("Libros por Lenguaje, ingrese el lenguaje que desea buscar: \nOpciones: es, en, fr, pt.");
        String lenguaje = teclado.nextLine();
        libros = repositorioLibro.findByLenguajeContains(lenguaje);
        if (libros.isEmpty()){
            System.out.println("Lenguaje no encontrado...");
        }else {
            libros.stream().sorted(Comparator.comparing(Libro::getTitulo)).forEach(System.out::println);
        }
    }
}
