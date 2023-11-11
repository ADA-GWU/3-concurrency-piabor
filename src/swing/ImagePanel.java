package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//Custom JPanel to display image in the frame
public class ImagePanel extends JPanel {

    private final int width;
    private final int height;
    private final BufferedImage image;

    public ImagePanel(BufferedImage image) {
        double aspectRatio = (double) image.getWidth() / image.getHeight();
        int scaledWidth, scaledHeight;

        GraphicsDevice defaultGraphicsDevice =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        //Adjusting image size based on the display size
        if (image.getWidth() > image.getHeight()) {
            scaledWidth = defaultGraphicsDevice.getDisplayMode().getWidth();
            scaledHeight = (int) (scaledWidth / aspectRatio);
        } else {
            scaledHeight = defaultGraphicsDevice.getDisplayMode().getHeight();
            scaledWidth = (int) (scaledHeight * aspectRatio);
        }

        this.width = scaledWidth;
        this.height = scaledHeight;
        this.image = image;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

}
