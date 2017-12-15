package de.meonwax.advent.day15;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelingGenerators {

    private static final Pattern PATTERN = Pattern.compile("Generator A starts with ([0-9]+).+Generator B starts with ([0-9]+)", Pattern.DOTALL);

    public static void main(String[] args) throws IOException, URISyntaxException {
        Long[] initialValues = getValues(loadInput());

        Generator A = new Generator(16807, null, initialValues[0]);
        Generator B = new Generator(48271, null, initialValues[1]);
        System.out.println("Part one count: " + getCount(A, B, 40000000));

        A = new Generator(16807, 4, initialValues[0]);
        B = new Generator(48271, 8, initialValues[1]);
        System.out.println("Part two count: " + getCount(A, B, 5000000));
    }

    private static int getCount(Generator A, Generator B, int pairs) {
        int count = 0;
        for (long pair = 0; pair < pairs; pair++) {
            long valueA = A.next() & 0xFFFF;
            long valueB = B.next() & 0xFFFF;
            if (valueA == valueB) {
                count++;
            }
        }
        return count;
    }

    private static Long[] getValues(String input) {
        Matcher m = PATTERN.matcher(input);
        m.find();
        return new Long[]{Long.valueOf(m.group(1)), Long.valueOf(m.group(2))};
    }

    private static String loadInput() throws IOException, URISyntaxException {
        String fileName = "day15/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return new String(Files.readAllBytes(Paths.get(uri)));
    }

    private static class Generator {

        Integer factor;
        Integer criteria;
        Long value;

        Generator(Integer factor, Integer criteria, Long value) {
            this.factor = factor;
            this.criteria = criteria;
            this.value = value;
        }

        Long next() {
            value = (value * factor) % 2147483647;
            if (criteria != null && value % criteria > 0) {
                return next();
            }
            return value;
        }
    }
}

