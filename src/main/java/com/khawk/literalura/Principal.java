package com.khawk.literalura;

import com.khawk.literalura.dto.LibroDTO;
import com.khawk.literalura.model.Autor;
import com.khawk.literalura.model.Libro;
import com.khawk.literalura.service.LiteraluraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {
    
    @Autowired
    private LiteraluraService literaluraService;
    
    private Scanner scanner = new Scanner(System.in);
    
    public void ejecutar() {
        mostrarBienvenida();
        
        while (true) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();
            
            switch (opcion) {
                case "1":
                    buscarLibroPorTitulo();
                    break;
                case "2":
                    listarLibrosRegistrados();
                    break;
                case "3":
                    listarAutoresRegistrados();
                    break;
                case "4":
                    listarAutoresVivosEnAnio();
                    break;
                case "5":
                    listarLibrosPorIdiomas();
                    break;
                case "0":
                    System.out.println("¡Gracias por usar LITERALURA! ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción inválida. Por favor, selecciona una opción válida.");
            }
            
            System.out.println();
        }
    }
    
    private void mostrarBienvenida() {
        System.out.println("=== LITERALURA - CATÁLOGO DE LIBROS ===");
    }
    
    private void mostrarMenu() {
        System.out.println("Elija la opción a través del número:");
        System.out.println("1 - Buscar libro por título");
        System.out.println("2 - Listar libros registrados");
        System.out.println("3 - Listar autores registrados");
        System.out.println("4 - Listar autores vivos en un determinado año");
        System.out.println("5 - Listar libros por idiomas");
        System.out.println("0 - Salir");
        System.out.print("Opción: ");
    }
    
    private void buscarLibroPorTitulo() {
        System.out.println("\n=== BÚSQUEDA DE LIBROS ===");
        System.out.println("Puedes buscar por título completo o parcial.");
        System.out.println("Ejemplos: 'Moby Dick', 'Frankenstein', 'Emma', 'Pride'");
        System.out.print("Ingrese el título o parte del título del libro: ");
        
        String titulo = scanner.nextLine().trim();
        
        if (titulo.isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return;
        }
        
        try {
            List<LibroDTO> resultados = literaluraService.buscarLibrosPorTitulo(titulo);
            
            if (resultados.isEmpty()) {
                System.out.println("No se encontraron libros que coincidan con tu búsqueda.");
                System.out.println("Sugerencia: Intenta con un término de búsqueda diferente.");
                return;
            }
            
            System.out.println("\n=== RESULTADOS ENCONTRADOS ===");
            System.out.println("Se encontraron " + resultados.size() + " libro(s) que coinciden con tu búsqueda:");
            
            int maxResultados = Math.min(resultados.size(), 5);
            for (int i = 0; i < maxResultados; i++) {
                LibroDTO libro = resultados.get(i);
                System.out.println((i + 1) + ". " + libro.getTitle());
                System.out.println("   Autor: " + libro.getAutorPrincipal().getName());
                System.out.println("   Idioma: " + libro.getIdiomaPrincipal());
                System.out.println("   Descargas: " + libro.getDownloadCount());
                System.out.println();
            }
            
            if (resultados.size() > 5) {
                System.out.println("... y " + (resultados.size() - 5) + " resultado(s) más.");
            }
            
            System.out.println("Guardando automáticamente el primer resultado...");
            
            Libro libroGuardado = literaluraService.convertirLibroDTOALibro(resultados.get(0));
            System.out.println("Libro guardado exitosamente: " + libroGuardado.getTitulo());
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Sugerencia: Intenta con un término de búsqueda diferente.");
        }
    }
    
    private void listarLibrosRegistrados() {
        System.out.println("\n=== LIBROS REGISTRADOS ===");
        
        try {
            List<Libro> libros = literaluraService.listarLibrosRegistrados();
            
            if (libros.isEmpty()) {
                System.out.println("No hay libros registrados en la base de datos.");
                System.out.println("Sugerencia: Usa la opción 1 para buscar y agregar libros.");
                return;
            }
            
            System.out.println("Total de libros registrados: " + libros.size());
            System.out.println();
            
            for (int i = 0; i < libros.size(); i++) {
                Libro libro = libros.get(i);
                System.out.println((i + 1) + ". " + libro.getTitulo());
                System.out.println("   Autor: " + libro.getAutor().getNombre());
                System.out.println("   Idioma: " + libro.getIdiomaPrincipal());
                System.out.println("   Descargas: " + libro.getNumeroDescargas());
                System.out.println();
            }
            
        } catch (Exception e) {
            System.out.println("Error al listar libros: " + e.getMessage());
        }
    }
    
    private void listarAutoresRegistrados() {
        System.out.println("\n=== AUTORES REGISTRADOS ===");
        
        try {
            List<Autor> autores = literaluraService.listarAutoresRegistrados();
            
            if (autores.isEmpty()) {
                System.out.println("No hay autores registrados en la base de datos.");
                System.out.println("Sugerencia: Usa la opción 1 para buscar y agregar libros.");
                return;
            }
            
            System.out.println("Total de autores registrados: " + autores.size());
            System.out.println();
            
            for (int i = 0; i < autores.size(); i++) {
                Autor autor = autores.get(i);
                System.out.println((i + 1) + ". " + autor.getNombre());
                System.out.println("   Nacimiento: " + autor.getAnioNacimiento());
                System.out.println("   Fallecimiento: " + (autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento() : "Desconocido"));
                System.out.println("   Libros: " + autor.getLibros().size() + " libro(s)");
                System.out.println();
            }
            
        } catch (Exception e) {
            System.out.println("Error al listar autores: " + e.getMessage());
        }
    }
    
    private void listarAutoresVivosEnAnio() {
        System.out.println("\n=== AUTORES VIVOS EN UN AÑO ESPECÍFICO ===");
        System.out.print("Ingrese el año para buscar autores vivos: ");
        
        String input = scanner.nextLine().trim();
        
        try {
            Integer anio = Integer.parseInt(input);
            
            if (anio < 1000 || anio > 2100) {
                System.out.println("Por favor, ingrese un año válido entre 1000 y 2100.");
                return;
            }
            
            List<Autor> autores = literaluraService.listarAutoresVivosEnAnio(anio);
            
            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + anio);
                return;
            }
            
            System.out.println("Autores vivos en el año " + anio + ":");
            System.out.println();
            
            for (int i = 0; i < autores.size(); i++) {
                Autor autor = autores.get(i);
                System.out.println((i + 1) + ". " + autor.getNombre());
                System.out.println("   Nacimiento: " + autor.getAnioNacimiento());
                System.out.println("   Fallecimiento: " + (autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento() : "Desconocido"));
                System.out.println("   Libros: " + autor.getLibros().size() + " libro(s)");
                System.out.println();
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un año válido.");
        } catch (Exception e) {
            System.out.println("Error al buscar autores: " + e.getMessage());
        }
    }
    
    private void listarLibrosPorIdiomas() {
        System.out.println("\n=== LIBROS POR IDIOMAS ===");
        
        try {
            List<String> idiomas = literaluraService.listarIdiomasDisponibles();
            
            if (idiomas.isEmpty()) {
                System.out.println("No hay idiomas registrados en la base de datos.");
                System.out.println("Sugerencia: Usa la opción 1 para buscar y agregar libros.");
                return;
            }
            
            System.out.println("Idiomas disponibles:");
            for (int i = 0; i < idiomas.size(); i++) {
                String idioma = idiomas.get(i);
                Long cantidad = literaluraService.contarLibrosPorIdioma(idioma);
                System.out.println((i + 1) + ". " + idioma + " (" + cantidad + " libros)");
            }
            
            System.out.println();
            System.out.print("Ingrese el código del idioma para ver los libros (ejemplo: \n" +
                    "'en': inglés \n" +
                    "'es': español \n" +
                    "'fr': frances): \n");
            String idiomaSeleccionado = scanner.nextLine().trim();
            
            if (idiomaSeleccionado.isEmpty()) {
                System.out.println("El idioma no puede estar vacío.");
                return;
            }
            
            List<Libro> libros = literaluraService.listarLibrosPorIdioma(idiomaSeleccionado.toLowerCase());
            
            if (libros.isEmpty()) {
                System.out.println("No se encontraron libros en el idioma '" + idiomaSeleccionado + "'.");
                return;
            }
            
            System.out.println("Libros encontrados en el idioma: " + idiomaSeleccionado + " (" + libros.size() + " libros):");
            System.out.println();
            
            for (int i = 0; i < libros.size(); i++) {
                Libro libro = libros.get(i);
                System.out.println((i + 1) + ". " + libro.getTitulo());
                System.out.println("   Autor: " + libro.getAutor().getNombre());
                System.out.println("   Descargas: " + libro.getNumeroDescargas());
                System.out.println();
            }
            
        } catch (Exception e) {
            System.out.println("Error al listar libros por idioma: " + e.getMessage());
        }
    }
} 