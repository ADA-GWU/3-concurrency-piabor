import runnable.ImageProcessorRunnable;
import swing.ImagePanel;
import util.ImageUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ImageProcessingApplication {

    public static void main(String[] args) {
        //Checking if the number of arguments is valid
        if (args.length != 3) {
            throw new IllegalArgumentException("Invalid program arguments!");
        }

        String fileName = args[0];
        int squareSize = Integer.parseInt(args[1]);
        int threadCount;

        //Checking if the processing mode is valid
        if ("M".equals(args[2])) {
            threadCount = Runtime.getRuntime().availableProcessors();
        } else if ("S".equals(args[2])) {
            threadCount = 1;
        } else {
            throw new IllegalArgumentException("Invalid processing mode!");
        }

        createAndShowGUI(fileName, squareSize, threadCount);
    }

    private static void createAndShowGUI(String fileName, int squareSize, int threadCount) {
        //Creating a new frame with the specified title.
        JFrame frame = new JFrame("Image Processing");
        //Making the close button to ex
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Loading the specified image file
        BufferedImage image = ImageUtils.loadImageFromFile(fileName);

        if (image != null) {
            ImagePanel imagePanel = new ImagePanel(image);

            //Adding the custom JPanel object to the frame.
            frame.add(imagePanel);
            //Adjust the frame size to fit the size of the image
            frame.pack();
            frame.setVisible(true);

            //Using ceil function in case of a remainder to process the pixels that don't make a square
            int horizontalSqCount = (int) Math.ceil((double) image.getWidth() / squareSize);
            int totalVerticalSqCount = (int) Math.ceil((double) image.getHeight() / squareSize);
            //The threads are vertically divided to work on different rows of the image.
            int verticalSqCount = totalVerticalSqCount / threadCount;
            //The program should consider remainders after dividing by the number of threads not to skip any square
            int extraVerticalSq = totalVerticalSqCount % threadCount;
            //This integer will be decreased by 1 each time an extra row of squares is assigned to a thread
            int remainingExtraSq = extraVerticalSq;

            List<Thread> threads = new ArrayList<>();

            //Assigning the custom runnable objects to threads and adding the threads to the list by the number of cores
            for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
                int verticalSqCountWithExtra = verticalSqCount;
                int startingIndex;

                //Deciding the number of square rows that a thread will process as well as where the thread will start
                if (remainingExtraSq > 0) {
                    verticalSqCountWithExtra++;
                    startingIndex = threadIndex * verticalSqCountWithExtra;
                    remainingExtraSq--;
                } else {
                    startingIndex = (threadIndex * verticalSqCount) + extraVerticalSq;
                }

                ImageProcessorRunnable runnable = new ImageProcessorRunnable(threadIndex,
                        startingIndex,
                        squareSize,
                        verticalSqCountWithExtra,
                        horizontalSqCount,
                        image,
                        imagePanel);

                Thread thread = new Thread(runnable);
                thread.start();
                threads.add(thread);
            }

            //The main thread must wait until all threads that process the image finish
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            //Now all threads have finished, the result image is saved
            ImageUtils.saveImageToFile(image);
        } else {
            //If the image is not found, show a message dialog in the frame
            JOptionPane.showMessageDialog(frame, "Failed to load the image.");
        }
    }

}