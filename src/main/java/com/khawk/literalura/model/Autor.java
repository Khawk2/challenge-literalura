package com.khawk.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "autores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Autor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String nombre;
    
    @JsonAlias("birth_year")
    private Integer anioNacimiento;
    
    @JsonAlias("death_year")
    private Integer anioFallecimiento;
    
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private java.util.List<Libro> libros;
    
    public Autor(String nombre, Integer anioNacimiento, Integer anioFallecimiento) {
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
        this.anioFallecimiento = anioFallecimiento;
    }
    
    public boolean estaVivoEn(Integer anio) {
        if (anioNacimiento == null) return false;
        if (anioFallecimiento == null) return anio >= anioNacimiento;
        return anio >= anioNacimiento && anio <= anioFallecimiento;
    }
} 