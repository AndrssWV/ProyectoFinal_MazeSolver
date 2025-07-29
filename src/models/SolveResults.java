package models;

import java.util.List;
import java.util.Set;

/**
 * Clase que encapsula los resultados de la resolución de un laberinto.
 * Esta clase almacena la información esencial obtenida tras ejecutar un algoritmo
 * de resolución de laberintos, incluyendo el camino encontrado y las celdas visitadas.
 * 
 * Es similar a AlgorithmResult pero puede usarse como una alternativa más simple
 * o en contextos donde se requiera una estructura de datos específica para
 * almacenar únicamente los resultados básicos de la resolución.
 * 
 * La clase proporciona acceso de solo lectura a los resultados, manteniendo
 * la inmutabilidad de los datos una vez creados.
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta]
 * @version 1.0
 */
public class SolveResults {
    /**
     * Lista ordenada de celdas que representa el camino encontrado desde el inicio hasta el destino.
     * Si no se encontró un camino válido, esta lista estará vacía.
     * El primer elemento corresponde al punto de inicio y el último al punto de destino.
     */
    private List<Cell> path;
    
    /**
     * Conjunto de todas las celdas que fueron visitadas durante el proceso de resolución.
     * Incluye tanto las celdas que forman parte del camino final como aquellas
     * que fueron exploradas pero no condujeron a la solución.
     */
    private Set<Cell> visited;

    /**
     * Constructor que inicializa los resultados de resolución con el camino encontrado
     * y el conjunto de celdas visitadas.
     * 
     * @param path Lista ordenada de celdas que representa el camino desde el inicio
     *             hasta el destino. Puede estar vacía si no se encontró solución.
     * @param visited Conjunto de todas las celdas que fueron visitadas durante
     *                el proceso de resolución, incluyendo las del camino final
     *                y las exploradas sin éxito.
     */
    public SolveResults(List<Cell> path, Set<Cell> visited) {
        this.path = path;
        this.visited = visited;
    }

    /**
     * Obtiene la lista ordenada de celdas que representa el camino encontrado.
     * 
     * @return Lista de celdas desde el punto de inicio hasta el destino.
     *         Si no se encontró un camino válido, retorna una lista vacía.
     *         El orden de las celdas corresponde al orden de navegación
     *         desde el inicio hasta el objetivo.
     */
    public List<Cell> getPath() {
        return path;
    }

    /**
     * Obtiene el conjunto de todas las celdas visitadas durante el proceso de resolución.
     * 
     * @return Set de celdas que fueron exploradas durante la búsqueda.
     *         Incluye tanto las celdas del camino final como aquellas que fueron
     *         visitadas pero no formaron parte de la solución. Es útil para
     *         análisis de rendimiento y visualización del proceso de búsqueda.
     */
    public Set<Cell> getVisited() {
        return visited;
    }
}
