import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;

public class Main {

    public static String calculSHA256Hexa(String word) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(word.getBytes(StandardCharsets.UTF_8));

        // Convert the byte hash to hexadecimal format"
        StringBuilder hexaStringBuilder = new StringBuilder();
        for (byte b : hash) {
            hexaStringBuilder.append(String.format("%02x", b));
        }

        return hexaStringBuilder.toString();
    }

    public static List<String> readWordsFromFile(String ruteFile) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruteFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {

                String[] wordsLine = linea.trim().split("[\\s,]+");
                words.addAll(Arrays.asList(wordsLine));
            }
        }
        return words;
    }

    public static void main(String[] args) {
        try {
            String routeFile = "src/recursos/palabras2.txt";

            List<String> words = readWordsFromFile(routeFile);

            List<String> hashes = new ArrayList<>();
            for (String word : words) {
                String hash = calculSHA256Hexa(word);
                hashes.add(hash);
            }

            System.out.println("Hashes SHA-256 words:");
            for (int i = 0; i < words.size(); i++) {
                System.out.println("word: " + words.get(i) + ", Hash: " + hashes.get(i));
            }

            System.out.println("\nMatches with 'fb80':");
            for (int i = 0; i < hashes.size(); i++) {
                if (hashes.get(i).toLowerCase().contains("fb80")) {
                    System.out.println("Word: " + words.get(i) + ", Hash: " + hashes.get(i));
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
