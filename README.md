# Literalura - Catálogo de Libros

Literalura es una aplicación Java Spring Boot que consume la API de Gutenberg (https://gutendex.com/books) para crear un catálogo de libros y autores. La aplicación permite buscar libros por título, listar libros y autores registrados, y realizar consultas específicas sobre autores vivos en determinados años y libros por idiomas.

## Características

- **Búsqueda de libros por título**: Consume la API de Gutenberg para buscar y registrar libros
- **Gestión de autores**: Almacena información de autores con años de nacimiento y fallecimiento
- **Consultas por idioma**: Permite listar libros por idioma y mostrar estadísticas
- **Autores vivos por año**: Busca autores que estaban vivos en un año específico
- **Persistencia de datos**: Utiliza PostgreSQL para almacenar la información
- **Interfaz de consola**: Menú interactivo para todas las funcionalidades

## Demo Animada

![Uso completo en consola](images/demo-literalura.gif)

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **PostgreSQL**
- **Jackson** (para mapeo JSON)
- **Lombok** (para reducir código boilerplate)
- **HttpClient** (para consumo de APIs)

## Estructura del Proyecto

```
src/main/java/com/khawk/literalura/
├── LiteraluraApplication.java          # Clase main
├── Principal.java                      # Clase principal con el menú
├── model/
│   ├── Autor.java                      # Entidad Autor
│   └── Libro.java                      # Entidad Libro
├── dto/
│   ├── AutorDTO.java                   # DTO para mapeo JSON de Autor
│   ├── LibroDTO.java                   # DTO para mapeo JSON de Libro
│   └── RespuestaAPI.java               # DTO para respuesta completa de la API
├── repository/
│   ├── AutorRepository.java            # Repositorio para Autor
│   └── LibroRepository.java            # Repositorio para Libro
└── service/
    ├── ConsumoAPI.java                 # Servicio para consumir la API
    └── LiteraluraService.java          # Servicio principal de negocio
```

## Configuración de la Base de Datos

### Variables de Entorno

El proyecto utiliza las siguientes variables de entorno:

```properties
DB_HOST
DB_NAME=khawk_literalura
DB_USER
DB_PASSWORD
```

### Configuración de PostgreSQL

1. Instala PostgreSQL en tu sistema
2. Crea una base de datos llamada `khawk_literalura`
3. Configura las variables de entorno o modifica `application.properties`

## Instalación y Ejecución

### Prerrequisitos

- Java 21 o superior
- Maven 3.6 o superior
- PostgreSQL 12 o superior

### Pasos de Instalación

1. **Clona el repositorio:**
   ```bash
   git clone <https://github.com/Khawk2/challenge-literalura.git>
   cd literalura
   ```

2. **Configura la base de datos:**
   - Crea una base de datos PostgreSQL llamada `khawk_literalura`
   - Configura las variables de entorno o modifica `application.properties`

3. **Compila el proyecto:**
   ```bash
   mvn clean compile
   ```

4. **Ejecuta la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

## Funcionalidades

### Menú Principal

La aplicación presenta un menú interactivo con las siguientes opciones:

1. **Buscar libro por título**: Busca un libro en la API de Gutenberg y lo registra en la base de datos
2. **Listar libros registrados**: Muestra todos los libros almacenados en la base de datos
3. **Listar autores registrados**: Muestra todos los autores almacenados en la base de datos
4. **Listar autores vivos en un determinado año**: Busca autores que estaban vivos en un año específico
5. **Listar libros por idiomas**: Muestra estadísticas y libros por idioma
0. **Salir**: Termina la aplicación

### Detalles de las Funcionalidades

#### 1. Búsqueda de Libros
- Consume la API de Gutenberg usando HttpClient
- Permite búsquedas por título completo o parcial
- Muestra múltiples resultados de búsqueda
- Mapea la respuesta JSON a objetos Java usando Jackson
- Almacena el libro y su autor en la base de datos
- Evita duplicados verificando si el libro ya existe
- Guarda automáticamente el primer resultado más relevante

#### 2. Gestión de Autores
- Cada libro tiene un autor principal (primer autor de la lista)
- Almacena nombre, año de nacimiento y año de fallecimiento
- Permite consultas por autores vivos en años específicos

#### 3. Consultas por Idioma
- Cada libro tiene un idioma principal (primer idioma de la lista)
- Permite contar libros por idioma
- Muestra estadísticas y listado de libros por idioma

## API Consumida

La aplicación consume la API de Gutenberg a través de:
- **URL Base**: https://gutendex.com/books/
- **Endpoint de búsqueda**: `?search=<título>`
- **Formato de respuesta**: JSON

### Estructura de la Respuesta

```json
{
  "count": 76428,
  "next": "https://gutendex.com/books/?page=2",
  "previous": null,
  "results": [
    {
      "id": 2701,
      "title": "Moby Dick; Or, The Whale",
      "authors": [
        {
          "name": "Melville, Herman",
          "birth_year": 1819,
          "death_year": 1891
        }
      ],
      "languages": ["en"],
      "download_count": 104393
    }
  ]
}
```

## Anotaciones JPA Utilizadas

- `@Entity`: Marca la clase como entidad JPA
- `@Table`: Especifica el nombre de la tabla
- `@Id`: Marca el campo como clave primaria
- `@GeneratedValue`: Configura la generación automática de IDs
- `@Column`: Configura propiedades de la columna
- `@ManyToOne`: Define relación muchos a uno
- `@OneToMany`: Define relación uno a muchos
- `@JoinColumn`: Especifica la columna de unión
- `@ElementCollection`: Para colecciones de elementos simples


## Anotaciones de Spring Utilizadas
- `@Component`: Para que Spring la gestione


## Anotaciones Lombok Utilizadas
- `@Getter`: Crea los Getters de la clase
- `@Setetr`: Crea los Setters de la clase


## Anotaciones Jackson Utilizadas

- `@JsonIgnoreProperties(ignoreUnknown = true)`: Ignora propiedades desconocidas
- `@JsonAlias`: Mapea nombres de propiedades JSON a campos Java

## Consultas Personalizadas

### AutorRepository
- `findByNombre(String nombre)`: Busca autor por nombre
- `findAutoresVivosEnAnio(Integer anio)`: Busca autores vivos en un año específico
- `findAutoresConAnioNacimiento()`: Busca autores con año de nacimiento

### LibroRepository
- `findByTitulo(String titulo)`: Busca libro por título
- `findByIdioma(String idioma)`: Busca libros por idioma
- `countByIdioma(String idioma)`: Cuenta libros por idioma
- `findAllIdiomas()`: Obtiene todos los idiomas disponibles

## Manejo de Errores

La aplicación incluye manejo de errores para:
- Errores de conexión a la API
- Errores de mapeo JSON
- Errores de base de datos
- Entrada de datos inválidos del usuario
- Libros no encontrados

## Pruebas

Para probar la aplicación:

1. Ejecuta la aplicación
2. Selecciona la opción 1 para buscar un libro
3. Ingresa un título como "Moby Dick" o "Frankenstein"
4. Explora las otras opciones del menú

## Contribución

¡Gracias por tu interés en contribuir a este proyecto!
Sigue estos pasos para proponer una mejora o nueva funcionalidad:

1. Fork el proyecto
2. Clona tu fork en tu máquina local (git clone https://github.com/Khawk2/challenge-literalura.git
   cd NOMBRE_DEL_REPO)
3. Crea una rama para tu feature (`git checkout -b feature/nombreDeTuFeature`)
4. Commit tus cambios (`git commit -m 'Add feature: NombreDeTuFeature'`)
5. Push a la rama (`git push origin feature/nombreDeTuFeature`)
6. Abre un Pull Request

## Licencia

Este proyecto es de uso libre con fines educativos y personales. Para uso comercial, por favor revisa las condiciones de la API utilizada.

## Autor

Desarrollado por [KEVIN ALAPE] - [Ksalapeg.270700@gmail.com]
