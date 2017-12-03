package de.meonwax.advent.day03;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SpiralMemory {

    private static Map<Integer, Map<Integer, Integer>> memory;

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Memory part one: " + getSteps(Integer.valueOf(loadInput())));
        System.out.println("Memory part two: " + getValue(Integer.valueOf(loadInput())));
    }

    private static int getSteps(int input) {
        int x = 0;
        int y = 0;
        int inc = 1;
        int current = 1;
        int direction = 1;

        while (true) {
            for (int i = 1; i < inc + 1; i++) {
                current++;

                x = (direction == 1) ? x + 1 : (direction == 3) ? x - 1 : x;
                y = (direction == 2) ? y + 1 : (direction == 4) ? y - 1 : y;

                if (current == input) {
                    return Math.abs(x) + Math.abs(y);
                }
            }

            direction = (direction == 4) ? 1 : direction + 1;
            if ((direction == 1) || (direction == 3)) {
                inc++;
            }
        }
    }

    private static int getValue(int input) {
        int x = 0;
        int y = 0;
        int inc = 1;
        int direction = 1;
        write(0, 0, 1);

        while (true) {
            for (int i = 1; i < inc + 1; i++) {
                x = (direction == 1) ? x + 1 : (direction == 3) ? x - 1 : x;
                y = (direction == 2) ? y + 1 : (direction == 4) ? y - 1 : y;

                write(x, y, read(x + 1, y - 1) +
                        read(x + 1, y) +
                        read(x + 1, y + 1) +
                        read(x - 1, y - 1) +
                        read(x - 1, y) +
                        read(x - 1, y + 1) +
                        read(x, y - 1) +
                        read(x, y + 1));

                if (read(x, y) > input) {
                    return read(x, y);
                }
            }

            direction = (direction == 4) ? 1 : direction + 1;
            if ((direction == 1) || (direction == 3)) {
                inc++;
            }
        }
    }

    private static void write(int x, int y, int value) {
        if (memory == null) {
            memory = new HashMap<>();
        }
        Map<Integer, Integer> map = memory.get(x);
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(y, value);
        memory.put(x, map);
    }

    private static int read(int x, int y) {
        Map<Integer, Integer> yMap = memory.get(x);
        if (yMap != null) {
            Integer value = yMap.get(y);
            if (value != null) {
                return value;
            }
        }
        return 0;
    }

    private static String loadInput() throws IOException, URISyntaxException {
        String fileName = "day03/input.txt";
        URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return new String(Files.readAllBytes(Paths.get(uri)));
    }
}
