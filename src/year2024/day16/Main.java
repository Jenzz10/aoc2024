package year2024.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static int GRIDSIZE = 50;
    static String file = "src/year2024/day16/input.txt";

    static char[][] map;
    static int startX, startY, endX, endY;
    static Map<String, position> visited = new HashMap<>();
    static Map<String, position> unvisited = new HashMap<>();

    public static void main(String[] args) throws IOException {
        initiateMap();
        double start = System.nanoTime();
        while (!unvisited.isEmpty()) {
            walk();
        }
        double end = System.nanoTime();

        System.out.println((end - start) / 1000000 + "ms");
        System.out.println("Cost " + visited.get(endX + "," + endY).distance);

    }

    private static void walk() {

        if (!unvisited.isEmpty()) {
            position p = findClosedPosition();
            if (p != null) {
                for (String direction : Arrays.asList("right", "left", "up", "down")) {
                    String n = findNeighbour(p, direction);
                    if (n != null && !visited.containsKey(n)) {
                        position position = unvisited.get(n);
                        if (p.directionFromPrevious.equals(direction)) {
                            if (position.distance > (p.distance + 1)) {
                                position.distance = p.distance + 1;
                                position.directionFromPrevious = direction;
                            }
                        } else {
                            if (position.distance > (p.distance + 1001)) {
                                position.distance = p.distance + 1001;
                                position.directionFromPrevious = direction;
                            }
                        }
                    }
                }

                unvisited.remove(p.coordinate());
                visited.put(p.coordinate(), p);
            }
        }
    }

    static String findNeighbour(position p, String direction) {
        if (direction.equals("up")) {
            if (map[p.x - 1][p.y] != '#') {
                return (p.x - 1) + "," + p.y;
            }
        }
        if (direction.equals("down")) {
            if (map[p.x + 1][p.y] != '#') {
                return (p.x + 1) + "," + p.y;
            }
        }
        if (direction.equals("right")) {
            if (map[p.x][p.y + 1] != '#') {
                return (p.x) + "," + (p.y + 1);
            }
        }
        if (direction.equals("left")) {
            if (map[p.x][p.y - 1] != '#') {
                return p.x + "," + (p.y - 1);
            }
        }
        return null;
    }

    private static position findClosedPosition() {
        position p = null;
        for (String s : unvisited.keySet()) {
            if (p == null) {
                p = unvisited.get(s);
            } else if (p.distance > unvisited.get(s).distance) {
                p = unvisited.get(s);
            }
        }
        return p;
    }


    public static void initiateMap() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        map = new char[line.length()][line.length()];
        GRIDSIZE = line.length();
        for (int i = 0; i < GRIDSIZE; i++) {
            map[i] = line.toCharArray();
            if (line.contains("S")) {
                startX = i;
                startY = line.indexOf("S");
                position s = new position(startX, startY, 0, "right");
                unvisited.put(s.coordinate(), s);
            }
            if (line.contains("E")) {
                endX = i;
                endY = line.indexOf("E");
                position s = new position(endX, endY, Integer.MAX_VALUE, "");
                unvisited.put(s.coordinate(), s);
            }
            for (int j = 0; j < GRIDSIZE; j++) {
                if (map[i][j] == '.') {
                    position p = new position(i, j, Integer.MAX_VALUE, "");
                    unvisited.put(p.coordinate(), p);
                }
            }
            line = reader.readLine();
        }
    }

    private static void printMap(List<position> currentPath) {
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                position p = new position(i, j, 0, "");
                if (currentPath.contains(p)) {
                    System.out.print("X");
                } else {
                    System.out.print(map[i][j]);
                }
            }
            System.out.println();
        }
    }


    public static void printMap() {
        for (int i = 0; i < GRIDSIZE; i++) {
            System.out.println("" + new String(map[i]));
        }
    }

    static class position {
        public int x, y, distance;
        public String directionFromPrevious;

        public position(int x, int y, int distance, String directionFromPrevious) {
            this.x = x;
            this.y = y;
            this.distance = distance;
            this.directionFromPrevious = directionFromPrevious;
        }

        @Override
        public String toString() {
            return "position{" +
                    "x=" + x +
                    ", y=" + y +
                    ", distance=" + distance +
                    ", directionFromPrevious='" + directionFromPrevious + '\'' +
                    '}';
        }

        public position() {
        }


        String coordinate() {
            return x + "," + y;
        }
    }
}

