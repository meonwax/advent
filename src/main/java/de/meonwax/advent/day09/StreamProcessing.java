package de.meonwax.advent.day09;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StreamProcessing {

    public static void main(String[] args) throws IOException, URISyntaxException {
        int[] result = parseChars(loadInput());
        System.out.println("Total groups score: " + result[0]);
        System.out.println("Garbage chars: " + result[1]);
    }

    private static int[] parseChars(char[] input) {
        int openGroups = 0;
        int totalScore = 0;
        int garbageChars = 0;
        boolean inGarbage = false;
        for (int i = 0; i < input.length; i++) {
            char c = input[i];
            if (c == '{' && !inGarbage) {
                totalScore += ++openGroups;
            } else if (c == '}' && !inGarbage) {
                openGroups--;
            } else if (c == '<' && !inGarbage) {
                inGarbage = true;
            } else if (c == '>') {
                inGarbage = false;
            } else if (c == '!') {
                i++;
            } else if ((inGarbage && c == ',') || c != ',') {
                garbageChars++;
            }
        }
        return new int[]{totalScore, garbageChars};
    }

    private static char[] loadInput() throws IOException, URISyntaxException {
        String fileName = "day09/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return new String(Files.readAllBytes(Paths.get(uri))).toCharArray();
    }
}

