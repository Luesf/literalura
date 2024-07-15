package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositorioAutor extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);
    List<Autor> findByFechaNacimientoLessThanEqualAndFechaMuerteGreaterThanEqual(Integer fechaNacimiento, Integer fechaMuerte);
}
