package de.meonwax.advent.day04;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class HighEntropyPassphrases {

    private static Pattern PATTERN = Pattern.compile("\\s");

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Passphrases part one: " + getValidPartOne(loadInput()));
    }

    private static long getValidPartOne(Stream<String> input) {
        return input.filter(row -> areUnique(getWords(row))).count();
    }

    private static boolean areUnique(Stream<String> words) {
        final Set<String> seen = new HashSet<>();
        return words.allMatch(seen::add);
    }

    private static Stream<String> getWords(String row) {
        return PATTERN.splitAsStream(row);
    }

    private static Stream<String> loadInput() throws IOException, URISyntaxException {
        String fileName = "day04/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return Files.lines(Paths.get(uri));
    }
}
