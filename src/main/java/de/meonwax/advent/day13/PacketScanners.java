package de.meonwax.advent.day13;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PacketScanners {

    private static final String PATTERN = ": ";

    public static void main(String[] args) throws IOException, URISyntaxException {
        String[] input = loadInput();
        Layer[] firewall = generateFirewall(input);

        int delay = 0;
        while (isCaught(firewall, delay)) {
            delay++;
        }

        System.out.println("With a delay of " + delay + " picoseconds we will not be caught");
    }

    private static boolean isCaught(Layer[] firewall, int delay) {
        int severity = 0;
        boolean caught = false;
        for (int i = 0; i < 100; i++) {
            int step = i + delay;
            if (firewall[i] != null && !firewall[i].isOpen(step)) {
                severity += i * firewall[i].range;
                caught = true;
            }
        }

        if (delay == 0) {
            System.out.println("First run has severity of " + severity);
        }

        return caught;
    }

    private static Layer[] generateFirewall(String[] input) {
        Layer[] firewall = new Layer[100];
        for (String row : input) {
            String[] values = row.split(PATTERN);
            firewall[Integer.valueOf(values[0])] = new Layer(Integer.valueOf(values[1]));
        }
        return firewall;
    }

    private static String[] loadInput() throws IOException, URISyntaxException {
        String fileName = "day13/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return Files.lines(Paths.get(uri)).toArray(String[]::new);
    }

    private static class Layer {
        int range;

        Layer(int range) {
            this.range = range;
        }

        public boolean isOpen(int step) {
            return !(step % ((range - 1) * 2) == 0);
        }
    }
}

