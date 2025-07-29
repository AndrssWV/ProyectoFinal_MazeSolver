package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana principal de la aplicación de resolución de laberintos.
 * Esta clase extiende JFrame y proporciona la interfaz gráfica principal para interactuar
 * con el sistema de resolución de laberintos, incluyendo controles para crear, editar
 * y resolver laberintos utilizando diferentes algoritmos.
 * 
 * La ventana incluye:
 * - Barra de menú con opciones de archivo y ayuda
 * - Barra de herramientas superior para modos de edición (inicio, fin, paredes)
 * - Panel central con la visualización del laberinto
 * - Barra de herramientas inferior para selección de algoritmos y controles de resolución
 * 
 * Funcionalidades principales:
 * - Creación de nuevos laberintos con dimensiones personalizables
 * - Visualización de resultados de diferentes algoritmos
 * - Interfaz para establecer puntos de inicio y fin
 * - Edición de paredes del laberinto
 * - Resolución paso a paso o completa
 * - Limpieza y reinicio del laberinto
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta]
 * @version 1.0
 */
public class MazeFrame extends JFrame {
    /**
     * Panel principal que contiene la visualización y edición del laberinto.
     * Este componente maneja la representación gráfica del laberinto, los eventos
     * de mouse para edición, y la visualización de los resultados de los algoritmos.
     */
    private MazePanel mazePanel;
    
    /**
     * ComboBox que permite seleccionar el algoritmo de resolución a utilizar.
     * Contiene las opciones: Recursivo, Completo, Completo BT, BFS, DFS.
     */
    JComboBox<String> methods;
    
    /**
     * Número de filas del laberinto actual.
     * Se utiliza para inicializar el MazePanel y para crear nuevos laberintos.
     */
    private int rows;
    
    /**
     * Número de columnas del laberinto actual.
     * Se utiliza para inicializar el MazePanel y para crear nuevos laberintos.
     */
    private int cols;

    /**
     * Constructor que inicializa la ventana principal del laberinto.
     * Configura las dimensiones, título, comportamiento de cierre y inicializa
     * todos los componentes de la interfaz gráfica.
     * 
     * @param rows Número de filas para el laberinto (debe ser mayor que 0)
     * @param cols Número de columnas para el laberinto (debe ser mayor que 0)
     */
    public MazeFrame(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        setTitle("MathWorks Maze");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(0, 0);
        setJMenuBar(createMenuBar());
        initUI();
        pack();
        setVisible(true);
    }

    /**
     * Crea y configura la barra de menú principal de la aplicación.
     * Incluye el menú "Archivo" con opciones para crear nuevos laberintos y ver resultados,
     * y el menú "Ayuda" con información sobre los desarrolladores.
     * 
     * @return JMenuBar configurada con todos los menús y elementos de menú
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem nuevoLaberinto = new JMenuItem("Nuevo Laberinto");
        JMenuItem verResultados = new JMenuItem("Ver resultados");

        // Acción para crear un nuevo laberinto con dimensiones personalizadas
        nuevoLaberinto.addActionListener(e -> {
            String stringRows = JOptionPane.showInputDialog(this, "Ingrese número de filas:", "Dimensión Maze", JOptionPane.QUESTION_MESSAGE);
            if (stringRows == null) return;
            String stringCols = JOptionPane.showInputDialog(this, "Ingrese número de columnas:", "Dimensión Maze", JOptionPane.QUESTION_MESSAGE);
            if (stringCols == null) return;
            try {
                int newRows = Integer.parseInt(stringRows);
                int newCols = Integer.parseInt(stringCols);
                if (newRows <= 0 || newCols <= 0) {
                    JOptionPane.showMessageDialog(this, "Las dimensiones deben ser valores positivos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Crear nueva ventana con las dimensiones especificadas
                SwingUtilities.invokeLater(() -> {
                    dispose();
                    new MazeFrame(newRows, newCols);
                });
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(this, "Dimensiones no válidas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Acción para mostrar el diálogo de resultados
        verResultados.addActionListener(e -> {
            ResultadosDialog dialog = new ResultadosDialog(this);
            dialog.setVisible(true);
        });

        menuArchivo.add(nuevoLaberinto);
        menuArchivo.add(verResultados);

        // Menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem acercaDe = new JMenuItem("Acerca de");
        
        // Acción para mostrar información sobre los desarrolladores
        acercaDe.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                this,
                "Desarrollado por:\n - Sebastian Lopez\n - Andres Villata\n - Jamileth Kumpanam\n - Valeria Guaman",
                "Acerca de",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        menuAyuda.add(acercaDe);

        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);

        return menuBar;
    }

    /**
     * Inicializa y configura todos los componentes de la interfaz de usuario.
     * Crea el panel principal con layout BorderLayout, incluyendo:
     * - Barra de herramientas superior con botones de modo de edición
     * - Panel central con la visualización del laberinto
     * - Barra de herramientas inferior con controles de algoritmos y resolución
     */
    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Barra de herramientas superior para modos de edición
        JToolBar topToolBar = new JToolBar();
        topToolBar.setFloatable(false);

        topToolBar.add(createModeButton("Set Start"));
        topToolBar.add(createModeButton("Set End"));
        topToolBar.add(createModeButton("Toggle Wall"));

        mainPanel.add(topToolBar, BorderLayout.NORTH);

        // Panel central con el laberinto
        mazePanel = new MazePanel(rows, cols);
        mainPanel.add(mazePanel, BorderLayout.CENTER);

        // Barra de herramientas inferior para algoritmos y controles
        JToolBar bottomToolBar = new JToolBar();
        bottomToolBar.setFloatable(false);

        // Selector de algoritmo
        methods = new JComboBox<>(new String[]{"Recursivo", "Completo", "Completo BT", "BFS", "DFS"});
        bottomToolBar.add(new JLabel("Algoritmo:"));
        bottomToolBar.add(methods);

        // Botones de control
        bottomToolBar.addSeparator();
        bottomToolBar.add(createMazeButton("Resolver"));
        bottomToolBar.add(createMazeButton("Paso a paso"));
        bottomToolBar.add(createMazeButton("Limpiar"));

        mainPanel.add(bottomToolBar, BorderLayout.SOUTH);
        add(mainPanel);
    }

    /**
     * Crea botones para cambiar el modo de edición del laberinto.
     * Estos botones permiten al usuario cambiar entre diferentes modos:
     * establecer punto de inicio, punto final, o alternar paredes.
     * 
     * @param text Texto del botón que determina su funcionalidad
     * @return JButton configurado con el ActionListener apropiado
     */
    private JButton createModeButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = ((JButton)e.getSource()).getText();
                switch (command) {
                    case "Set Start":
                        mazePanel.setMode(MazePanel.Mode.SET_START);
                        break;
                    case "Set End":
                        mazePanel.setMode(MazePanel.Mode.SET_END);
                        break;
                    case "Toggle Wall":
                        mazePanel.setMode(MazePanel.Mode.TOGGLE_WALL);
                        break;
                }
            }
        });
        return button;
    }

    /**
     * Crea botones para las operaciones principales del laberinto.
     * Estos botones manejan tanto los modos de edición como las operaciones
     * de resolución del laberinto.
     * 
     * Funcionalidades disponibles:
     * - Set Start/End/Toggle Wall: Cambian el modo de edición
     * - Resolver: Ejecuta el algoritmo seleccionado de forma completa
     * - Paso a paso: Ejecuta el algoritmo con visualización gradual
     * - Limpiar: Reinicia el laberinto a su estado inicial
     * 
     * @param text Texto del botón que determina su funcionalidad
     * @return JButton configurado con el ActionListener apropiado
     */
    private JButton createMazeButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = ((JButton)e.getSource()).getText();
                switch (command) {
                    case "Set Start":
                        mazePanel.setMode(MazePanel.Mode.SET_START);
                        break;
                    case "Set End":
                        mazePanel.setMode(MazePanel.Mode.SET_END);
                        break;
                    case "Toggle Wall":
                        mazePanel.setMode(MazePanel.Mode.TOGGLE_WALL);
                        break;
                    case "Resolver":
                        mazePanel.solveMaze(methods.getSelectedItem().toString());
                        break;
                    case "Paso a paso":
                        mazePanel.solveMazeBySteps(methods.getSelectedItem().toString());
                        break;
                    case "Limpiar":
                        mazePanel.clearMaze();
                        break;
                }
            }
        });
        return button;
    }


}