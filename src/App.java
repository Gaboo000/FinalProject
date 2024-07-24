// Main.java

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Laberinto laberinto = new Laberinto(10, 10); // Default size
            LaberintoView view = new LaberintoView();
            new LaberintoController(laberinto, view);
            view.setVisible(true);
        });
    }
}
