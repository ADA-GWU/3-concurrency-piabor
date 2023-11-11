package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageUtils {

    //Summing reds, greens, and blues of all pixels inside a square then dividing each of them by the number of pixels
    //inside the square to get the average color for that square
    public static Color getAverageColor(BufferedImage image, int startX, int startY, int width, int height) {
        int x = startX + width;
        int y = startY + height;
        long redSum = 0, greenSum = 0, blueSum = 0;

        for (int i = startX; i < x; i++) {
            for (int j = startY; j < y; j++) {
                Color pixel = new Color(image.getRGB(i, j));
                redSum += pixel.getRed();
                greenSum += pixel.getGreen();
                blueSum += pixel.getBlue();
            }
        }
        int totalPixels = width * height;

        int averageR = (int) (redSum / totalPixels);
        int averageG = (int) (greenSum / totalPixels);
        int averageB = (int) (blueSum / totalPixels);

        return new Color(averageR, averageG, averageB);
    }

    public static BufferedImage loadImageFromFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return ImageIO.read(file);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveImageToFile(BufferedImage image) {
        File outputfile = new File("result.jpg");
        try {
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
