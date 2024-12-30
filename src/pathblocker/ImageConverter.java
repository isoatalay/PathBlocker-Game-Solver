package pathblocker;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class ImageConverter {

    List<PathBlockerState> solutionSequence;

    public ImageConverter(List<PathBlockerState> solutionSequence) {
        this.solutionSequence = solutionSequence;
    }

    public void saveAsPng() {
        int cellSize = 40;   // Size of each square
        int count = 1;       // Counter for the image filenames

        // Assume all states have the same levelName
        String baseName = solutionSequence.get(0).levelname;

        // Create a directory with the name based on levelName
        File directory = new File(baseName);
        if (!directory.exists()) {
            if (directory.mkdir()) {
                System.out.println("");
                System.out.println("Directory created: " + directory.getAbsolutePath());
            } else {
                System.err.println("Failed to create directory: " + directory.getAbsolutePath());
                return;
            }
        }

        // Save each state as an image in the created directory
        for (PathBlockerState state : solutionSequence) {
            int size = state.getWorld().length * cellSize;
            BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setFont(new Font("Arial", Font.BOLD, 20));

            for (int i = 0; i < state.getWorld().length; i++) {
                for (int j = 0; j < state.getWorld()[i].length; j++) {
                    // String cell = state.world[j][i].toString();
                    int x = i * cellSize;
                    int y = j * cellSize;

                    if (state.getWorld()[j][i].equals(Features.wall)) {
                        g2d.setColor(Color.BLUE);  // Wall
                        g2d.fillRect(x, y, cellSize, cellSize);
                    } else if (state.getWorld()[j][i].equals(Features.goal)) {
                        g2d.setColor(Color.BLACK); // Goal
                        g2d.fillRect(x, y, cellSize, cellSize);
                        g2d.setColor(Color.WHITE); // Set color for text
                        g2d.drawString("G", x + cellSize / 3, y + cellSize / 1.5f);  // Draw "G" in the center
                    } else if (state.getWorld()[j][i].equals(Features.player)) {
                        g2d.setColor(Color.YELLOW); // Player
                        g2d.fillRect(x, y, cellSize, cellSize);
                        g2d.setColor(Color.BLACK);  // Set color for text
                        g2d.drawString("P", x + cellSize / 3, y + cellSize / 1.5f);  // Draw "P" in the center
                    } else {
                        g2d.setColor(Color.WHITE);  // Empty space
                        g2d.fillRect(x, y, cellSize, cellSize);
                    }
                }
            }

            g2d.dispose();

            // Create unique file name by appending the counter
            File outputFile = new File(directory, baseName + "." + count + ".png");

            try {
                ImageIO.write(image, "png", outputFile);
                //  System.out.println("State saved as PNG: " + outputFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error saving PNG file: " + e.getMessage());
            }

            count++;  // Increment the counter for the next state
        }
    }
}
