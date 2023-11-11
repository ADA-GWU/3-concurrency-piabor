package runnable;

import util.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//Custom runnable class that contains the instructions each thread should follow
public class ImageProcessorRunnable implements Runnable {

    public ImageProcessorRunnable(int id,
                                  int startingIndex,
                                  int squareSize,
                                  int verticalSqCount,
                                  int horizontalSqCount,
                                  BufferedImage image,
                                  JPanel imagePanel) {
        this.id = id;
        this.startingIndex = startingIndex;
        this.squareSize = squareSize;
        this.verticalSqCount = verticalSqCount;
        this.horizontalSqCount = horizontalSqCount;
        this.image = image;
        this.imagePanel = imagePanel;
    }

    private final int id;
    private final int startingIndex;
    private final int squareSize;
    private final int verticalSqCount;
    private final int horizontalSqCount;
    private final BufferedImage image;
    private final JPanel imagePanel;

    @Override
    public void run() {
        long start = System.nanoTime();
        //Starting coordinates are different for each thread since they work on the different parts of the image
        for (int i = startingIndex; i < startingIndex + verticalSqCount; i++) {
            for (int j = 0; j < horizontalSqCount; j++) {
                int x = j * squareSize;
                int y = i * squareSize;
                int squareWidth = Math.min(squareSize, (image.getWidth() - x));
                int squareHeight = Math.min(squareSize, (image.getHeight() - y));
                Color averageColor = ImageUtils.getAverageColor(image, x, y, squareWidth, squareHeight);

                for (int k = x; k < x + squareWidth; k++) {
                    for (int l = y; l < y + squareHeight; l++) {
                        //Change the color of all pixels inside the square to the average color
                        image.setRGB(k, l, averageColor.getRGB());
                    }
                }

                //Repainting the JLabel each time a square is ready to be shown
                imagePanel.repaint();
            }
        }

        //Calculating the elapsed time for each thread.
        long end = System.nanoTime();
        System.out.println("Elapsed time for thread[" + id + "] in nano seconds: " + (end - start));
    }

}
