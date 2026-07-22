package com.polysolve;
import com.polysolve.view.MainFrame;
import javax.swing.*;

/*

 * Main
 * opening the POLYSOLVE desktop application.
 * Starts the application's graphical interface (GUI).
 
*/
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Uses the default GUI style when the system style isn't available.
            }
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
