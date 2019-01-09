import java.awt.*;
import javax.swing.*;

public class GUISquare extends JPanel {
    private static final long serialVersionUID = 4729078763643541598L;

    private JLabel label; // Label in panel

    // ****************************************************
    // Method: GUISquare
    //
    // Purpose: Constructor. Initializes square.
    // ****************************************************
    public GUISquare() {
        // Create JLabel object
        label = new JLabel();

        // Set foreground color to white (text color will be white)
        label.setForeground(Color.white);

        // Add label to square panel
        add(label);
    }

    // ****************************************************
    // Method: setText
    //
    // Purpose: Sets the square's label text with the
    // String parameter.
    // ****************************************************
    public void setText(String text) {
        label.setText(text);
    }

    // ****************************************************
    // Method: resetText
    //
    // Purpose: Sets the square's label text to null, which
    // clears the label.
    // ****************************************************
    public void resetText() {
        label.setText(null);
    }
}