package no.emagnus.tsp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TspDatasetReader {

    public List<TspDataPoint> readTspFile(String filename) {
        List<TspDataPoint> result = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("resources", filename));
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file " + filename);
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        File file = new File("");

        TspDatasetReader.class.getClassLoader().getResource("pom.xml").getPath();

        List<TspDataPoint> tspDataPoints = new TspDatasetReader().readTspFile("dj38.tsp");
        for (TspDataPoint tspDataPoint : tspDataPoints) {
            System.out.println(tspDataPoint);
        }
    }
}
