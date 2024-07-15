package com.alura.literalura.model;

import jakarta.persistence.*;


@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private String autor;
    private String lenguaje;
    private Double descargas;

    public Libro(){
    }

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.autor = obtenerPrimerAutor(datosLibro).getNombre();
        this.lenguaje = obtenerPrimerLenguaje(datosLibro);
        this.descargas = datosLibro.descargas();
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    private String obtenerPrimerLenguaje(DatosLibro datosLibro) {
        return datosLibro.lenguaje().get(0);
    }

    private Autor obtenerPrimerAutor(DatosLibro datosLibro) {
        DatosAutor datosAutor = datosLibro.autor().get(0);
        return new Autor(datosAutor);
    }

    @Override
    public String toString() {
        return "\n------- Libro -------" +
                "\nTitle: " + titulo +
                "\nAuthor: " + autor +
                "\nLanguage: " + lenguaje +
                "\nDownloads: " + descargas +
                "\n---------------------\n";
    }

}
