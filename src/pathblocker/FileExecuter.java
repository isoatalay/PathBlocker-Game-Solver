package pathblocker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileExecuter {

    File[] levelFiles;
    private List<PathBlockerState> levels = new ArrayList<>();

    public FileExecuter(String pathname) {

        //  this.levelFiles = new File(pathname).listFiles((dir, name) -> name.endsWith(".txt"));
        this.levelFiles = Arrays.stream(new File(pathname).listFiles((dir, name) -> name.endsWith(".txt")))
                .sorted((f1, f2) -> {
                    int num1 = extractNumber(f1.getName());
                    int num2 = extractNumber(f2.getName());
                    return Integer.compare(num1, num2);
                })
                .toArray(File[]::new);
        checkPath();

    }

    public int extractNumber(String name) {
        String number = name.replaceAll("\\D+", ""); // Remove non-digit characters
        return number.isEmpty() ? 0 : Integer.parseInt(number); // Handle if no number is found
    }

    private void checkPath() {
        if (levelFiles != null) {
            // Loop through each file in the directory
            for (File levelFile : levelFiles) {

                System.out.println("Reading file: " + levelFile.getName());
                String levelName = levelFile.getName().substring(0, levelFile.getName().length() - 4);

                try (Scanner scanner = new Scanner(levelFile)) {
                    levels.add(new PathBlockerState(convertLevel(scanner), levelName));

                } catch (FileNotFoundException e) {
                    System.err.println("File not found: " + levelFile.getAbsolutePath());
                }
            }
        } else {
            System.err.println("No level files found in the specified directory.");
        }
    }

    public String convertLevel(Scanner sc) {
        StringBuffer sb = new StringBuffer();
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
            sb.append("\n");
        }
        return sb.toString();
    }

    public List<PathBlockerState> getLevels() {
        return this.levels;
    }
}
