// LaberintoController.java (Controlador)
import java.awt.Color;
import javax.swing.JButton;

public class LaberintoController {
    private Laberinto laberinto;
    private LaberintoView view;

    public LaberintoController(Laberinto laberinto, LaberintoView view) {
        this.laberinto = laberinto;
        this.view = view;

        view.getCreateButton().addActionListener(e -> createGrid());
        view.getBfsButton().addActionListener(e -> bfs());
        view.getDfsButton().addActionListener(e -> dfs());
        view.getRecursivoButton().addActionListener(e -> recursivo());
        view.getConCacheButton().addActionListener(e -> conCache());
        view.getClearButton().addActionListener(e -> clear());
    }

    private void createGrid() {
        int rows = view.getRows();
        int cols = view.getCols();
        view.createGrid(rows, cols);
        laberinto = new Laberinto(rows, cols);
    }

    public void bfs() {
        resolverLaberinto("BFS");
    }

    public void dfs() {
        resolverLaberinto("DFS");
    }

    public void recursivo() {
        resolverLaberinto("Recursivo");
    }

    public void conCache() {
        resolverLaberinto("Dinámico");
    }

    public void clear() {
        limpiarLaberinto();
    }

    private void resolverLaberinto(String algoritmo) {
        JButton[][] gridButtons = view.getGridButtons();
        laberinto = new Laberinto(view.getRows(), view.getCols()); // Reinitialize laberinto with new grid size
        for (int i = 0; i < view.getRows(); i++) {
            for (int j = 0; j < view.getCols(); j++) {
                if (gridButtons[i][j].getBackground() == Color.BLACK) {
                    laberinto.setCell(i, j, 1);
                }
            }
        }

        int[][] path = null;

        int startX = view.getStartX();
        int startY = view.getStartY();
        int endX = view.getEndX();
        int endY = view.getEndY();

        long startTime = System.nanoTime();
        switch (algoritmo) {
            case "BFS":
                path = Algoritmos.bfs(laberinto, startX, startY, endX, endY);
                break;
            case "DFS":
                path = Algoritmos.dfsIterativo(laberinto, startX, startY, endX, endY);
                break;
            case "Recursivo":
                path = Algoritmos.encontrarRutaRecursiva(laberinto, startX, startY, endX, endY);
                break;
            case "Dinámico":
                path = Algoritmos.encontrarRutaDinamica(laberinto, startX, startY, endX, endY);
                break;
        }
        long endTime = System.nanoTime();

        if (path != null) {
            for (int[] step : path) {
                gridButtons[step[0]][step[1]].setBackground(Color.GREEN);
            }
            int pasos = path.length;
            view.updateInfo("<html>Algoritmo: " + algoritmo + "<br>Ruta encontrada en: " + pasos + " pasos<br>Tiempo: " + (endTime - startTime) / 1000000 + " ms</html>");
        } else {
            view.updateInfo("Ruta no encontrada");
        }
    }

    private void limpiarLaberinto() {
        JButton[][] gridButtons = view.getGridButtons();
        for (int i = 0; i < view.getRows(); i++) {
            for (int j = 0; j < view.getCols(); j++) {
                if (gridButtons[i][j].getBackground() == Color.YELLOW || gridButtons[i][j].getBackground() == Color.GREEN) {
                    gridButtons[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }
}
