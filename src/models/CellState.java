package models;

import java.awt.Color;

/**
 * Enumeración que representa los diferentes estados que puede tener una celda en el laberinto.
 * Esta clase implementa el patrón Enum Type-Safe, proporcionando instancias constantes
 * predefinidas que representan todos los posibles estados de una celda durante
 * la visualización y resolución del laberinto.
 * 
 * Cada estado tiene asociado:
 * - Un nombre descriptivo para identificación
 * - Un color específico para la representación visual en la interfaz gráfica
 * 
 * Los estados disponibles son:
 * - EMPTY: Celda vacía (transitable)
 * - WALL: Pared o obstáculo (no transitable)
 * - START: Punto de inicio del laberinto
 * - END: Punto de destino del laberinto
 * - PATH: Celda que forma parte del camino solución
 * - VISITED: Celda que fue explorada durante la búsqueda
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta]
 * @version 1.0
 */
public class CellState {
    /**
     * Estado que representa una celda vacía y transitable en el laberinto.
     * Se visualiza en color blanco en la interfaz gráfica.
     */
    public static final CellState EMPTY = new CellState("Empty", Color.WHITE);
    
    /**
     * Estado que representa una pared o obstáculo en el laberinto.
     * Las celdas con este estado no son transitables por los algoritmos de búsqueda.
     * Se visualiza en color negro en la interfaz gráfica.
     */
    public static final CellState WALL = new CellState("Wall", Color.BLACK);
    
    /**
     * Estado que representa el punto de inicio del laberinto.
     * Es la posición desde donde comienzan todos los algoritmos de búsqueda.
     * Se visualiza en color verde en la interfaz gráfica.
     */
    public static final CellState START = new CellState("Start", Color.GREEN);
    
    /**
     * Estado que representa el punto de destino del laberinto.
     * Es la posición objetivo que deben alcanzar los algoritmos de búsqueda.
     * Se visualiza en color rojo en la interfaz gráfica.
     */
    public static final CellState END = new CellState("End", Color.RED);
    
    /**
     * Estado que representa una celda que forma parte del camino solución.
     * Estas celdas constituyen la ruta encontrada desde el inicio hasta el destino.
     * Se visualiza en color azul en la interfaz gráfica.
     */
    public static final CellState PATH = new CellState("Path", Color.BLUE);
    
    /**
     * Estado que representa una celda que fue visitada durante la búsqueda.
     * Incluye celdas exploradas que no necesariamente forman parte del camino final.
     * Se visualiza en color gris claro en la interfaz gráfica.
     */
    public static final CellState VISITED = new CellState("Visited", Color.LIGHT_GRAY);
    
    /**
     * Nombre descriptivo del estado de la celda.
     * Se utiliza para identificación y propósitos de depuración.
     */
    private final String name;
    
    /**
     * Color asociado al estado para la representación visual.
     * Se utiliza en la interfaz gráfica para diferenciar visualmente
     * los diferentes tipos de celdas en el laberinto.
     */
    private final Color color;
    
    /**
     * Constructor privado para crear instancias de estados de celda.
     * Este constructor es privado para implementar el patrón Enum Type-Safe,
     * garantizando que solo se puedan usar las instancias predefinidas.
     * 
     * @param name Nombre descriptivo del estado
     * @param color Color asociado al estado para visualización
     */
    private CellState(String name, Color color) {
        this.name = name;
        this.color = color;
    }
    
    /**
     * Obtiene el color asociado al estado de la celda.
     * Este color se utiliza en la interfaz gráfica para la representación
     * visual del laberinto y sus diferentes elementos.
     * 
     * @return Color asociado al estado actual
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Proporciona una representación textual del estado de la celda.
     * Retorna el nombre descriptivo del estado, útil para depuración,
     * logging y visualización en modo texto.
     * 
     * @return Nombre descriptivo del estado (ej: "Empty", "Wall", "Start")
     */
    @Override
    public String toString() {
        return name;
    }
}