package com.khawk.literalura.service;

import com.khawk.literalura.dto.AutorDTO;
import com.khawk.literalura.dto.LibroDTO;
import com.khawk.literalura.dto.RespuestaAPI;
import com.khawk.literalura.model.Autor;
import com.khawk.literalura.model.Libro;
import com.khawk.literalura.repository.AutorRepository;
import com.khawk.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LiteraluraService {
    
    @Autowired
    private ConsumoAPI consumoAPI;
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private AutorRepository autorRepository;
    
    	public Libro buscarLibroPorTitulo(String titulo) {
		try {
			RespuestaAPI respuesta = consumoAPI.buscarLibroPorTitulo(titulo);
			
			if (respuesta.getResults() != null && !respuesta.getResults().isEmpty()) {
				// Mostrar información sobre los resultados encontrados
				System.out.println("Búsqueda realizada para: '" + titulo + "'");
				System.out.println("Total de resultados encontrados: " + respuesta.getResults().size());
				
				// Tomar el primer resultado (el más relevante)
				LibroDTO libroDTO = respuesta.getResults().get(0);
				System.out.println("Seleccionando el primer resultado: '" + libroDTO.getTitle() + "'");
				
				return convertirLibroDTOALibro(libroDTO);
			} else {
				throw new RuntimeException("No se encontró ningún libro que contenga: '" + titulo + "'");
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Error al buscar el libro: " + e.getMessage(), e);
		}
	}
    
    	@Transactional
	public Libro convertirLibroDTOALibro(LibroDTO libroDTO) {
        // Verificar si el libro ya existe
        Optional<Libro> libroExistente = libroRepository.findByTitulo(libroDTO.getTitle());
        if (libroExistente.isPresent()) {
            System.out.println("El libro ya existe en la base de datos.");
            return libroExistente.get();
        }
        
        // Obtener o crear el autor
        Autor autor = obtenerOCrearAutor(libroDTO.getAutorPrincipal());
        
        // Crear el libro
        Libro libro = new Libro();
        libro.setTitulo(libroDTO.getTitle());
        libro.setAutor(autor);
        libro.setIdiomas(libroDTO.getLanguages());
        libro.setNumeroDescargas(libroDTO.getDownloadCount());
        
        return libroRepository.save(libro);
    }
    
    @Transactional
    protected Autor obtenerOCrearAutor(AutorDTO autorDTO) {
        if (autorDTO == null) {
            throw new RuntimeException("No se encontró información del autor");
        }
        
        // Verificar si el autor ya existe
        Optional<Autor> autorExistente = autorRepository.findByNombre(autorDTO.getName());
        if (autorExistente.isPresent()) {
            System.out.println("El autor ya existe en la base de datos.");
            return autorExistente.get();
        }
        
        // Crear nuevo autor
        Autor autor = new Autor();
        autor.setNombre(autorDTO.getName());
        autor.setAnioNacimiento(autorDTO.getBirthYear());
        autor.setAnioFallecimiento(autorDTO.getDeathYear());
        
        System.out.println("Creando nuevo autor: " + autorDTO.getName());
        return autorRepository.save(autor);
    }
    
    public List<Libro> listarLibrosRegistrados() {
        return libroRepository.findAll();
    }
    
    public List<Autor> listarAutoresRegistrados() {
        return autorRepository.findAll();
    }
    
    public List<Autor> listarAutoresVivosEnAnio(Integer anio) {
        return autorRepository.findAutoresVivosEnAnio(anio);
    }
    
    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }
    
    public Long contarLibrosPorIdioma(String idioma) {
        return libroRepository.countByIdioma(idioma);
    }
    
    public List<String> listarIdiomasDisponibles() {
		return libroRepository.findAllIdiomas();
	}
	
	// Método adicional para obtener múltiples resultados de búsqueda
	public List<LibroDTO> buscarLibrosPorTitulo(String titulo) {
		try {
			RespuestaAPI respuesta = consumoAPI.buscarLibroPorTitulo(titulo);
			
			if (respuesta.getResults() != null && !respuesta.getResults().isEmpty()) {
				return respuesta.getResults();
			} else {
				return new java.util.ArrayList<>();
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Error al buscar libros: " + e.getMessage(), e);
		}
	}
} 