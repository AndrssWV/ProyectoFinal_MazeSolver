package solver;

import models.Cell;
import models.AlgorithmResult;

/**
 * Interfaz que define el contrato para todos los algoritmos de resolución de laberintos.
 * Esta interfaz establece un estándar común que debe ser implementado por todos los
 * algoritmos de resolución, garantizando consistencia en la API y facilitando
 * el intercambio entre diferentes estrategias de resolución.
 * 
 * La interfaz sigue el patrón Strategy, permitiendo que el cliente use diferentes
 * algoritmos de resolución (BFS, DFS, recursivos) de manera intercambiable sin
 * cambiar el código cliente.
 * 
 * Implementaciones disponibles:
 * - MazeSolverBFS: Búsqueda en anchura (garantiza camino más corto)
 * - MazeSolverDFS: Búsqueda en profundidad (eficiente en memoria)
 * - MazeSolverRecursivo: Algoritmo recursivo básico (simple y rápido)
 * - MazeSolverRecursivoCompleto: Algoritmo recursivo exhaustivo
 * - MazeSolverRecursivoCompletoBT: Algoritmo recursivo con backtracking completo
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta]
 * @version 1.0
 */
public interface MazeSolver {
    
    /**
     * Método principal que debe ser implementado por todos los algoritmos de resolución.
     * Este método encuentra un camino desde la celda de inicio hasta la celda de destino
     * en el laberinto especificado, utilizando la estrategia específica del algoritmo implementador.
     * 
     * El método debe:
     * - Validar los parámetros de entrada
     * - Implementar su algoritmo específico de búsqueda
     * - Mantener un registro de las celdas visitadas durante la búsqueda
     * - Construir el camino desde el inicio hasta el destino (si existe)
     * - Retornar un resultado que incluya tanto el camino como las celdas visitadas
     * 
     * Comportamiento esperado:
     * - Si existe un camino válido, retorna el camino encontrado y todas las celdas visitadas
     * - Si no existe camino, retorna un camino vacío pero mantiene las celdas visitadas
     * - Si los parámetros son inválidos, retorna estructuras vacías
     * 
     * @param grid Matriz booleana que representa el laberinto donde:
     *             - true indica una celda transitable (camino libre)
     *             - false indica una pared o obstáculo (no transitable)
     *             La matriz debe ser rectangular y no nula
     * @param start Celda de inicio de la búsqueda. Debe estar dentro de los límites
     *              del laberinto y ser una celda transitable
     * @param end Celda de destino de la búsqueda. Debe estar dentro de los límites
     *            del laberinto y ser una celda transitable
     * @return AlgorithmResult que contiene:
     *         - path: Lista ordenada de celdas que forman el camino desde start hasta end
     *                 (vacía si no existe camino)
     *         - visited: Set de todas las celdas exploradas durante la búsqueda
     *                   (útil para análisis de rendimiento y visualización)
     */
    AlgorithmResult getPath(boolean[][] grid, Cell start, Cell end);

    /**
     * Constante que define las direcciones de movimiento estándar en un laberinto.
     * Representa los cuatro movimientos cardinales posibles desde cualquier celda:
     * - {1, 0}: Mover hacia abajo (incrementar fila)
     * - {0, 1}: Mover hacia la derecha (incrementar columna)
     * - {-1, 0}: Mover hacia arriba (decrementar fila)
     * - {0, -1}: Mover hacia la izquierda (decrementar columna)
     * 
     * Esta constante puede ser utilizada por las implementaciones que necesiten
     * explorar en las cuatro direcciones cardinales. Sin embargo, las implementaciones
     * son libres de definir sus propias direcciones si requieren un comportamiento específico
     * (como el MazeSolverRecursivo que solo usa 2 direcciones).
     * 
     * Uso típico:
     * for (int[] dir : directions) {
     *     int newRow = currentRow + dir[0];
     *     int newCol = currentCol + dir[1];
     *     // Procesar nueva posición
     * }
     */
    int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    
}
