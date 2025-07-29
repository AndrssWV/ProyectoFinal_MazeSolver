package solver.solverImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import models.Cell;
import models.AlgorithmResult;
import solver.MazeSolver;

/**
 * Implementación del algoritmo de resolución recursiva básica para laberintos.
 * Esta clase utiliza un enfoque recursivo simple para encontrar un camino desde
 * el punto de inicio hasta el destino en el laberinto.
 * 
 * Características del algoritmo recursivo básico:
 * - Utiliza recursión para explorar el laberinto en profundidad
 * - Implementación más simple y directa que otros algoritmos recursivos
 * - Explora solo direcciones limitadas (arriba y derecha) para simplicidad
 * - Marca las celdas como visitadas para evitar ciclos infinitos
 * - Construye el camino agregando celdas cuando encuentra una ruta exitosa
 * - No implementa backtracking: las celdas visitadas permanecen marcadas
 * 
 * Este algoritmo es ideal para casos donde se requiere una solución rápida
 * y simple, aunque puede no encontrar el camino óptimo debido a su exploración limitada.
 * 
 * Complejidad temporal: O(N*M) en el mejor caso, O(4^(N*M)) en el peor caso
 * Complejidad espacial: O(N*M) para la pila de recursión y estructuras de datos
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta]
 * @version 1.0
 */
public class MazeSolverRecursivo implements MazeSolver {
    /**
     * Matriz booleana que representa el laberinto.
     * true indica una celda transitable, false indica una pared u obstáculo.
     */
    private boolean[][] grid;
    
    /**
     * Lista que almacena el camino encontrado desde el inicio hasta el destino.
     * Se construye dinámicamente durante la búsqueda, agregando celdas cuando
     * se encuentra una ruta exitosa hacia el destino.
     */
    private List<Cell> path;
    
    /**
     * Conjunto ordenado de todas las celdas visitadas durante la búsqueda.
     * Se utiliza LinkedHashSet para mantener el orden de visita y evitar
     * revisitar celdas, previniendo ciclos infinitos en la recursión.
     */
    private Set<Cell> visited;
    
    /**
     * Celda de destino del laberinto. Se utiliza para determinar cuándo
     * se ha alcanzado el objetivo durante la búsqueda recursiva.
     */
    private Cell end;
    
    /**
     * Matriz que define las direcciones de movimiento limitadas para este algoritmo básico.
     * Solo incluye dos direcciones (arriba y derecha) para mantener la simplicidad del algoritmo.
     * Esto hace que el algoritmo sea más rápido pero puede no encontrar todos los caminos posibles.
     */
    private static final int[][] directions = {{-1, 0}, {0, 1}}; // arriba, derecha

    /**
     * Constructor que inicializa todas las estructuras de datos necesarias.
     * Prepara las colecciones vacías que serán utilizadas durante la búsqueda recursiva básica.
     */
    public MazeSolverRecursivo() {
        grid = new boolean[][] {};
        path = new ArrayList<>();
        visited = new LinkedHashSet<>();
    }

    /**
     * Método principal que inicia la búsqueda recursiva básica en el laberinto.
     * Configura las estructuras de datos y delega la búsqueda al método recursivo,
     * utilizando un enfoque simple y directo para encontrar un camino.
     * 
     * Este método implementa el patrón de inicialización y delegación, preparando
     * el entorno para la búsqueda recursiva básica con exploración limitada.
     * 
     * @param grid Matriz booleana que representa el laberinto donde true indica
     *             celda transitable y false indica pared
     * @param start Celda de inicio de la búsqueda
     * @param end Celda de destino a alcanzar
     * @return AlgorithmResult que contiene el camino encontrado (si existe) y
     *         las celdas visitadas durante la búsqueda con exploración limitada
     */
    @Override
    public AlgorithmResult getPath(boolean[][] grid, Cell start, Cell end) {
        // Reinicializar estructuras de datos para nueva búsqueda
        path = new ArrayList<>();
        visited = new LinkedHashSet<>();
        this.grid = grid;
        this.end = end;
        
        // Validación de entrada
        if (grid == null || grid.length == 0) return new AlgorithmResult(path, visited);
        
        // Iniciar búsqueda recursiva básica
        if (findPath(start)) {
            return new AlgorithmResult(path, visited);
        }
        
        // No se encontró camino
        return new AlgorithmResult(new ArrayList<>(), visited);
    }

    /**
     * Método recursivo que implementa la búsqueda básica en el laberinto.
     * Este método utiliza un enfoque simple de recursión con exploración limitada,
     * siendo más eficiente pero potencialmente menos exhaustivo que otros algoritmos recursivos.
     * 
     * Características del algoritmo recursivo básico:
     * - Verifica directamente la transitabilidad sin método auxiliar separado
     * - Marca cada celda como visitada antes de explorarla
     * - Cuando alcanza el destino, agrega la celda al camino y retorna éxito
     * - Explora solo direcciones limitadas (arriba y derecha) para simplicidad
     * - Construye el camino agregando celdas cuando encuentra una ruta exitosa
     * - No implementa backtracking: las celdas visitadas permanecen marcadas
     * 
     * Proceso del algoritmo:
     * 1. Verifica si la celda está dentro del laberinto y es transitable
     * 2. Marca la celda como visitada para evitar ciclos
     * 3. Si la celda actual es el destino, la agrega al camino y retorna éxito
     * 4. Explora recursivamente solo las direcciones limitadas (arriba y derecha)
     * 5. Si alguna dirección lleva al éxito, agrega la celda actual al camino
     * 6. Si ninguna dirección funciona, retorna falso sin modificar el camino
     * 
     * @param current Celda actual siendo explorada en la recursión
     * @return true si desde esta celda se puede alcanzar el destino, false en caso contrario
     */
    private boolean findPath(Cell current) {
        // Verificar validez de la celda actual (límites y transitabilidad)
        if (!isInMaze(current) || !grid[current.row][current.col]) return false;
        
        // Marcar celda como visitada
        visited.add(current);
        
        // Caso base: se alcanzó el destino
        if (current.equals(end)) {
            path.add(current);  // Agregar destino al camino
            return true;
        }
        
        // Explorar recursivamente solo direcciones limitadas (arriba y derecha)
        for (int i = 0; i < 2; i++) {
            int[] dir = directions[i];
            if (findPath(new Cell(current.row + dir[0], current.col + dir[1]))) {
                // Si se encontró un camino válido, agregar celda actual al path
                path.add(current);
                return true;
            }
        }
        
        // No se encontró camino desde esta celda
        return false;
    }

    /**
     * Verifica si una celda está dentro de los límites del laberinto.
     * Este método auxiliar comprueba que las coordenadas de fila y columna
     * estén dentro del rango válido de la matriz del laberinto.
     * 
     * @param current Celda a verificar
     * @return true si la celda está dentro de los límites del laberinto,
     *         false si está fuera de los límites o si current es null
     */
    private boolean isInMaze(Cell current) {
        return current != null && 
               current.row >= 0 && 
               current.col >= 0 && 
               current.row < grid.length && 
               current.col < grid[0].length;
    }
    
}