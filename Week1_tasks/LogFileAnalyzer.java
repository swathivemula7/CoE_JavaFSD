package week1;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LogFileAnalyzer {

    public static void analyzeLogFile(String inputFile, String outputFile, List<String> keywords) {
        Map<String, Integer> keywordCounts = new HashMap<>();
        
        for (String keyword : keywords) {
            keywordCounts.put(keyword, 0);
        }

        try {
            File file = new File(inputFile);
            if (!file.exists()) {
                System.err.println("Input file does not exist.");
                return;
            }

            try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFile))) {
                String line;
                
                while ((line = br.readLine()) != null) {
                    for (String keyword : keywords) {
                        if (line.contains(keyword)) {
                            keywordCounts.put(keyword, keywordCounts.get(keyword) + 1);
                        }
                    }
                }

                try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(outputFile))) {
                    for (String keyword : keywords) {
                        bw.write(keyword + ": " + keywordCounts.get(keyword) + " occurrences\n");
                    }
                }
                
                System.out.println("Log file analysis complete. Results written to " + outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String inputFile = "input.log";
        String outputFile = "analysis_results.txt";
        
        List<String> keywords = Arrays.asList("ERROR", "WARNING", "INFO", "DEBUG");
        
        analyzeLogFile(inputFile, outputFile, keywords);
    }
}
