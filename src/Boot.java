import javax.swing.*;

/**
 * Created by Dominik Mandinec on 23/10/2019
 */

public class Boot {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CanvasMouse(800, 600).start());
    }
}
