package views;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Diálogo modal que muestra los resultados de rendimiento de los diferentes algoritmos de resolución.
 * Esta clase extiende JDialog y proporciona una interfaz para visualizar, comparar y analizar
 * los resultados de ejecución de los algoritmos de resolución de laberintos.
 * 
 * Funcionalidades principales:
 * - Visualización tabular de resultados con algoritmo, celdas del camino y tiempo de ejecución
 * - Limpieza de resultados almacenados
 * - Generación de gráficas de rendimiento utilizando JFreeChart
 * - Comparación visual entre diferentes algoritmos
 * 
 * La clase utiliza datos de ejemplo que muestran métricas típicas de rendimiento:
 * - Algoritmo utilizado
 * - Número de celdas en el camino encontrado
 * - Tiempo de ejecución en nanosegundos
 * 
 * Componentes de la interfaz:
 * - Tabla con scroll para mostrar los resultados
 * - Botones para limpiar resultados y generar gráficas
 * - Diálogo secundario para mostrar gráficas de líneas
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta ]
 * @version 1.0
 */
public class ResultadosDialog extends JDialog {

    /**
     * Constructor que inicializa el diálogo de resultados con todos sus componentes.
     * Configura la tabla de resultados, los botones de acción y establece el layout
     * de la interfaz de usuario.
     * 
     * @param parent Frame padre al cual este diálogo está asociado, utilizado para
     *               centrar el diálogo y establecer la modalidad
     */
    public ResultadosDialog(JFrame parent) {
        super(parent, "Resultados Guardados", true);
        setLayout(new BorderLayout());

        // Definir columnas de la tabla de resultados
        String[] columnas = {"Algoritmo", "Celdas Camino", "Tiempo (ns)"};
        
        // Datos de ejemplo con métricas de rendimiento de diferentes algoritmos
        Object[][] datos = {
            {"Recursivo", 8, 146599},
            {"Backtracking", 8, 51400},
            {"BFS", 8, 89000},
            {"Recursivo Completo", 23, 72200},
            {"Recursivo Completo BT", 22, 49901}
        };

        // Crear modelo de tabla y componente JTable
        DefaultTableModel tableModel = new DefaultTableModel(datos, columnas);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Agregar tabla al centro del diálogo
        add(scrollPane, BorderLayout.CENTER);

        // Crear panel de botones con funcionalidades de control
        JPanel buttonPanel = new JPanel();
        JButton limpiarBtn = new JButton("Limpiar Resultados");
        JButton graficarBtn = new JButton("Graficar Resultados");

        // Configurar acción para limpiar todos los resultados de la tabla
        limpiarBtn.addActionListener(e -> tableModel.setRowCount(0));
        
        // Configurar acción para generar y mostrar gráfica de resultados
        graficarBtn.addActionListener(e -> {
            // Crear dataset para la gráfica con los datos de la tabla
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String algoritmo = tableModel.getValueAt(i, 0).toString();
                Number tiempo = (Number) tableModel.getValueAt(i, 2);
                dataset.addValue(tiempo.doubleValue(), "Tiempo(ns)", algoritmo);
            }
            
            // Crear gráfica de líneas con los tiempos de ejecución
            JFreeChart chart = ChartFactory.createLineChart(
                "Tiempos de Ejecución por Algoritmo",
                "Algoritmo",
                "Tiempo (ns)",
                dataset
            );
            
            // Crear y configurar diálogo para mostrar la gráfica
            ChartPanel chartPanel = new ChartPanel(chart);
            JDialog chartDialog = new JDialog(this, "Gráfica", true);
            chartDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            chartDialog.setContentPane(chartPanel);
            chartDialog.setSize(600, 400);
            chartDialog.setLocationRelativeTo(this);
            chartDialog.setVisible(true);
        });

        // Agregar botones al panel
        buttonPanel.add(limpiarBtn);
        buttonPanel.add(graficarBtn);

        // Agregar panel de botones al sur del diálogo
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(500, 300);
        setLocationRelativeTo(parent);
    }
}