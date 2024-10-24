import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar la imagen redimensionada para que ocupe todo el tamaño del panel
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}

