package de.meonwax.advent.day17;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Spinlock {

    public static void main(String[] args) throws IOException, URISyntaxException {
        int increment = loadInput();
        List<Integer> buffer = new ArrayList<>();
        buffer.add(0);
        int position = 0;
        for (int i = 1; i <= 2017; i++) {
            position = ((position + increment) % buffer.size()) + 1;
            buffer.add(position, i);
        }
        System.out.println("Final value: " + buffer.get(position + 1));
    }

    private static int loadInput() throws IOException, URISyntaxException {
        String fileName = "day17/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return Integer.valueOf(new String(Files.readAllBytes(Paths.get(uri))));
    }
}
