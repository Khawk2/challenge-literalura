package com.khawk.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LibroDTO {

    // Getters y Setters
    private Long id;
    private String title;
    private List<AutorDTO> authors;
    private List<String> languages;
    
    @JsonAlias("download_count")
    private Integer downloadCount;

    public LibroDTO() {}
    
    public LibroDTO(Long id, String title, List<AutorDTO> authors, List<String> languages, Integer downloadCount) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.languages = languages;
        this.downloadCount = downloadCount;
    }

    public String getIdiomaPrincipal() {
        return languages != null && !languages.isEmpty() ? languages.get(0) : "Desconocido";
    }
    
    public AutorDTO getAutorPrincipal() {
        return authors != null && !authors.isEmpty() ? authors.get(0) : null;
    }
    
    @Override
    public String toString() {
        return "LibroDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", languages=" + languages +
                ", downloadCount=" + downloadCount +
                '}';
    }
} 