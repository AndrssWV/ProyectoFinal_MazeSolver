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
 * Implementación del algoritmo de resolución recursiva con backtracking completo para laberintos.
 * Esta clase utiliza un enfoque recursivo con backtracking exhaustivo, que explora todas
 * las posibles rutas y retrocede cuando encuentra un callejón sin salida.
 * 
 * Características del algoritmo recursivo con backtracking completo:
 * - Utiliza recursión para explorar el laberinto en profundidad
 * - Implementa backtracking verdadero: deshace cambios cuando no encuentra solución
 * - Mantiene un seguimiento completo de celdas visitadas durante toda la búsqueda
 * - Construye el camino dinámicamente agregando y removiendo celdas según sea necesario
 * - Explora sistemáticamente todas las direcciones posibles desde cada celda
 * - Garantiza encontrar una solución si existe, aunque no necesariamente la más corta
 * 
 * La diferencia principal con otros algoritmos recursivos es que este implementa
 * backtracking completo, removiendo celdas del camino cuando no conducen a la solución.
 * 
 * Complejidad temporal: O(4^(N*M)) en el peor caso, donde N y M son las dimensiones del laberinto
 * Complejidad espacial: O(N*M) para la pila de recursión y estructuras de datos
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta]
 * @version 1.0
 */
public class MazeSolverRecursivoCompletoBT implements MazeSolver {
    /**
     * Matriz booleana que representa el laberinto.
     * true indica una celda transitable, false indica una pared u obstáculo.
     */
    private boolean[][] grid;
    
    /**
     * Lista que almacena el camino siendo construido dinámicamente durante la búsqueda.
     * Las celdas se agregan cuando forman parte de una ruta prometedora y se remueven
     * durante el backtracking cuando no conducen a la solución.
     */
    private List<Cell> path;
    
    /**
     * Conjunto ordenado de todas las celdas visitadas durante toda la búsqueda.
     * Se utiliza LinkedHashSet para mantener el orden de visita y evitar revisitar celdas.
     * A diferencia del path, este conjunto no se modifica durante el backtracking.
     */
    private Set<Cell> visited;
    
    /**
     * Celda de destino del laberinto. Se utiliza para determinar cuándo
     * se ha alcanzado el objetivo durante la búsqueda recursiva.
     */
    private Cell end;
    
    /**
     * Matriz que define las direcciones de movimiento posibles en el laberinto.
     * Representa los movimientos: arriba, derecha, abajo, izquierda.
     */
    private static final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    /**
     * Constructor que inicializa todas las estructuras de datos necesarias.
     * Prepara las colecciones vacías que serán utilizadas durante la búsqueda recursiva.
     */
    public MazeSolverRecursivoCompletoBT() {
        grid = new boolean[][] {};
        path = new ArrayList<>();
        visited = new LinkedHashSet<>();
    }

    /**
     * Método principal que inicia la búsqueda recursiva con backtracking completo.
     * Configura las estructuras de datos y llama al método recursivo para encontrar
     * un camino desde el punto de inicio hasta el destino.
     * 
     * Este método implementa el patrón de inicialización y delegación, preparando
     * el entorno para la búsqueda recursiva y manejando casos especiales.
     * 
     * @param grid Matriz booleana que representa el laberinto donde true indica
     *             celda transitable y false indica pared
     * @param start Celda de inicio de la búsqueda
     * @param end Celda de destino a alcanzar
     * @return AlgorithmResult que contiene el camino encontrado (si existe) y
     *         todas las celdas visitadas durante la búsqueda exhaustiva
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
        
        // Iniciar búsqueda recursiva con backtracking
        if (findPath(start)) {
            return new AlgorithmResult(path, visited);
        }
        
        // No se encontró camino
        return new AlgorithmResult(new ArrayList<>(), visited);
    }

    /**
     * Método recursivo que implementa el algoritmo de backtracking completo.
     * Este método es el corazón del algoritmo, explorando recursivamente todas las
     * posibles rutas desde la celda actual hasta el destino.
     * 
     * Proceso del algoritmo recursivo:
     * 1. Verifica si la celda actual es válida (dentro del laberinto y transitable)
     * 2. Marca la celda como visitada para evitar ciclos
     * 3. Si la celda actual es el destino, retorna true (éxito)
     * 4. Explora recursivamente todas las direcciones posibles
     * 5. Si alguna dirección lleva al éxito, agrega la celda al camino y retorna true
     * 6. Si ninguna dirección funciona, implementa backtracking removiendo la celda del camino
     * 
     * El backtracking se implementa removiendo la celda del camino cuando no conduce
     * a una solución, permitiendo explorar otras rutas alternativas.
     * 
     * @param current Celda actual siendo explorada en la recursión
     * @return true si desde esta celda se puede alcanzar el destino, false en caso contrario
     */
    private boolean findPath(Cell current) {
        // Verificar validez de la celda actual
        if (!isInMaze(current) || !isValid(current)) return false;
        
        // Marcar celda como visitada
        visited.add(current);
        
        // Caso base: se alcanzó el destino
        if (current.equals(end)) {
            return true;
        }
        
        // Explorar recursivamente todas las direcciones
        for (int[] dir : directions) {
            Cell next = new Cell(current.row + dir[0], current.col + dir[1]);
            if (findPath(next)) {
                // Si se encontró un camino válido, agregar celda actual al path
                path.add(current);
                return true;
            }
        }
        
        // Backtracking: remover celda del camino si no conduce a solución
        if (!path.isEmpty() && path.get(path.size()-1).equals(current)) {
            path.remove(path.size()-1);
        }
        
        return false;
    }

    /**
     * Verifica si una celda está dentro de los límites del laberinto.
     * Comprueba que las coordenadas de fila y columna estén dentro del rango
     * válido de la matriz del laberinto.
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
    
    /**
     * Verifica si una celda es válida para ser visitada durante la búsqueda.
     * Una celda es válida si:
     * - Es transitable (grid[row][col] == true)
     * - No ha sido visitada previamente en la búsqueda actual
     * 
     * Esta verificación es crucial para evitar ciclos infinitos en la recursión
     * y asegurar que solo se exploren celdas que pueden formar parte de un camino válido.
     * 
     * @param current Celda a verificar
     * @return true si la celda es transitable y no ha sido visitada,
     *         false en caso contrario
     */
    private boolean isValid(Cell current) {
        return grid[current.row][current.col] && !visited.contains(current);
    }

}
