package de.meonwax.advent.day01;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InverseCaptcha {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String input = loadInput();
        System.out.println("Captcha part one: " + captcha(input, 1));
        System.out.println("Captcha part two: " + captcha(input, input.length() / 2));
    }

    private static int captcha(String input, int offset) {
        int sum = 0;
        for (int i = 0; i < input.length(); i++) {
            int digit = getDigit(input, i);
            int other = getDigit(input, (i + offset) % input.length());
            if (digit == other) {
                sum += digit;
            }
        }
        return sum;
    }

    private static int getDigit(String s, int index) {
        return s.charAt(index) - '0';
    }

    private static String loadInput() throws IOException, URISyntaxException {
        String fileName = "day01/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return new String(Files.readAllBytes(Paths.get(uri)));
    }
}
