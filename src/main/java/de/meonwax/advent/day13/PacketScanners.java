package de.meonwax.advent.day13;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class PacketScanners {

    private static final Pattern PATTERN = Pattern.compile(": ");

    public static void main(String[] args) throws IOException, URISyntaxException {

        String[] input = loadInput();
        int severity = 1;

        for (int delay = 0; severity > 0; delay++) {
            Layer[] firewall = generateFirewall(input);

            severity = 0;

            for (int i = 0; i < delay; i++) {
                stepScanners(firewall);
            }

            for (int depth = 0; depth < firewall.length; depth++) {
                severity += step(depth, firewall);
            }

            System.out.println("delay: " + delay + " severity: " + severity);
        }
    }

    private static int step(int nextDepth, Layer[] firewall) {
        int severity = 0;

        // Check if we get caught in next layer
        Layer layer = firewall[nextDepth];
        if (layer != null && layer.position == 0) {
            severity = nextDepth * layer.range;
        }

        // Move the scanner on step further
        stepScanners(firewall);

        return severity;
    }

    private static void stepScanners(Layer[] firewall) {
        for (int i = 0; i < firewall.length; i++) {
            Layer layer = firewall[i];
            if (layer != null) {
                layer.step();
            }
        }
    }

    private static Layer[] generateFirewall(String[] input) {
        Layer[] firewall = new Layer[getInputValues(input[input.length - 1])[0] + 1];
        for (int i = input.length - 1; i >= 0; i--) {
            String row = input[i];
            int[] values = getInputValues(row);

            int depth = values[0];
            int range = values[1];

            Layer layer = new Layer(range);
            firewall[depth] = layer;
        }
        return firewall;
    }

    private static int[] getInputValues(String row) {
        String[] values = PATTERN.split(row);
        return new int[]{Integer.valueOf(values[0]), Integer.valueOf(values[1])};
    }

    private static String[] loadInput() throws IOException, URISyntaxException {
        String fileName = "day13/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return Files.lines(Paths.get(uri)).toArray(String[]::new);
    }

    private static class Layer {

        Layer(int range) {
            this.range = range;
        }

        void step() {
            if ((direction == 1 && position == range - 1) ||
                    (direction == -1 && position == 0)) {
                direction *= -1;
            }
            position += direction;
        }

        int range;
        int position = 0;
        int direction = 1;
    }
}

