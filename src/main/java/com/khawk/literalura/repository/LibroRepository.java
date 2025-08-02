package com.khawk.literalura.repository;

import com.khawk.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    Optional<Libro> findByTitulo(String titulo);
    
    @Query("SELECT l FROM Libro l WHERE :idioma MEMBER OF l.idiomas")
    List<Libro> findByIdioma(@Param("idioma") String idioma);
    
    @Query("SELECT COUNT(l) FROM Libro l WHERE :idioma MEMBER OF l.idiomas")
    Long countByIdioma(@Param("idioma") String idioma);
    
    @Query("SELECT DISTINCT i FROM Libro l JOIN l.idiomas i")
    List<String> findAllIdiomas();
} 