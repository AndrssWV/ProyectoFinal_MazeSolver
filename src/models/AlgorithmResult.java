package models;

import java.util.List;
import java.util.Set;

/**
 * Clase que encapsula el resultado de la ejecución de un algoritmo de resolución de laberintos.
 * Esta clase contiene toda la información relevante sobre la ejecución del algoritmo,
 * incluyendo el camino encontrado desde el punto de inicio hasta el destino,
 * y el conjunto de celdas que fueron visitadas durante el proceso de búsqueda.
 * 
 * Es utilizada por todos los algoritmos de resolución (BFS, DFS, recursivos)
 * para proporcionar un formato uniforme de resultados.
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta]
 * @version 1.0
 */
public class AlgorithmResult {
    /**
     * Lista ordenada de celdas que representa el camino encontrado desde el inicio hasta el destino.
     * Si no se encontró un camino válido, esta lista estará vacía.
     * El primer elemento de la lista es la celda de inicio y el último es la celda de destino.
     */
    private List<Cell> path;
    
    /**
     * Conjunto de todas las celdas que fueron visitadas durante la ejecución del algoritmo.
     * Incluye tanto las celdas que forman parte del camino final como las que fueron
     * exploradas pero no condujeron a la solución. Es útil para análisis de rendimiento
     * y visualización del proceso de búsqueda.
     */
    private Set<Cell> visited;

    /**
     * Constructor que inicializa un resultado de algoritmo con el camino encontrado
     * y las celdas visitadas durante la búsqueda.
     * 
     * @param path Lista ordenada de celdas que representa el camino desde el inicio
     *             hasta el destino. Puede estar vacía si no se encontró solución.
     * @param visited Conjunto de todas las celdas que fueron visitadas durante
     *                la ejecución del algoritmo, incluyendo tanto las del camino
     *                final como las exploradas sin éxito.
     */
    public AlgorithmResult(List<Cell> path, Set<Cell> visited) {
        this.path = path;
        this.visited = visited;
    }

    /**
     * Obtiene la lista ordenada de celdas que representa el camino encontrado.
     * 
     * @return Lista de celdas desde el punto de inicio hasta el destino.
     *         Si no se encontró un camino válido, retorna una lista vacía.
     *         El primer elemento es la celda de inicio y el último es el destino.
     */
    public List<Cell> getPath() {
        return path;
    }

    /**
     * Obtiene el conjunto de todas las celdas visitadas durante la ejecución del algoritmo.
     * 
     * @return Set de celdas que fueron exploradas durante la búsqueda.
     *         Incluye tanto las celdas del camino final como las que fueron
     *         visitadas pero no formaron parte de la solución.
     */
    public Set<Cell> getVisited() {
        return visited;
    }

    /**
     * Establece la lista de celdas que representa el camino encontrado.
     * 
     * @param path Nueva lista ordenada de celdas desde el inicio hasta el destino.
     *             Puede ser una lista vacía si no existe un camino válido.
     */
    public void setPath(List<Cell> path) {
        this.path = path;
    }

    /**
     * Establece el conjunto de celdas visitadas durante la ejecución del algoritmo.
     * 
     * @param visited Nuevo conjunto de celdas que fueron exploradas durante
     *                la búsqueda. Debe incluir todas las celdas procesadas
     *                por el algoritmo, no solo las del camino final.
     */
    public void setVisited(Set<Cell> visited) {
        this.visited = visited;
    }

    
}
