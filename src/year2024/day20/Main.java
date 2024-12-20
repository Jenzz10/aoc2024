package year2024.day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static int GRIDSIZE = 71;
    static String file = "src/year2024/day20/input.txt";
    static char[][] map = new char[GRIDSIZE][GRIDSIZE];
    static int startX, startY = 0;
    static int endY = 70;
    static int endX = 70;
    static Map<String, position> visited = new HashMap<>();
    static Map<String, position> unvisited = new HashMap<>();
    static Set<position> shortestPaths = new HashSet<>();
    static int shortestDistance = Integer.MAX_VALUE;


    public static void main(String[] args) throws IOException {
        int total = 0;

        double start = System.nanoTime();

        initiateMap();
        while (!unvisited.isEmpty()) {
            walk();
        }

        shortestDistance = visited.get(endX + "," + endY).distance;

        for (int i = 1; i < (GRIDSIZE - 1); i++) {
            for (int j = 1; j < (GRIDSIZE - 1); j++) {
                if (map[i][j] == '#' && checkIfUsefulToRemoveLocation(i, j)) {
                    position p = new position(i, j, 0, "");
                    total += getTotal(p, Arrays.asList("right", "left"));
                    total += getTotal(p, Arrays.asList("up", "down"));
                }
            }
        }

        System.out.println("total number of shorter path " + total);
        double end = System.nanoTime();
        System.out.println("number of paths on the shortest path " + (shortestPaths.size()));
        System.out.println((end - start) / 1000000 + "ms");
    }

    private static int getTotal(position p, List<String> directions) {
        position first = null;
        position second = null;

        for (String direction : directions) {
            String n = findNeighbour(p, direction);
            if (n != null) {
                position position = visited.get(n);
                if (first == null) {
                    position.directionFromPrevious = direction;
                    first = position;
                } else {
                    position.directionFromPrevious = direction;
                    second = position;
                }
            }
        }
        if (
                first != null && second != null &&
                        ((Math.max(first.distance, second.distance) - Math.min(first.distance, second.distance)) - 2) >= 100) {
            return 1;
        }
        return 0;
    }

    private static boolean checkIfUsefulToRemoveLocation(int x, int y) {
        return map[x + 1][y] != '#' || map[x - 1][y] != '#' || map[x][y + 1] != '#' || map[x][y - 1] != '#';
    }

    private static void walk() {

        if (!unvisited.isEmpty()) {
            position p = findClosedPosition();
            if (p != null) {
                for (String direction : Arrays.asList("right", "left", "up", "down")) {
                    String n = findNeighbour(p, direction);
                    if (n != null && !visited.containsKey(n)) {
                        position position = unvisited.get(n);
                        if (position.distance > (p.distance + 1)) {
                            position.distance = p.distance + 1;
                            position.directionFromPrevious = direction;
                        }
                    }
                }
                unvisited.remove(p.coordinate());
                visited.put(p.coordinate(), p);
            }
        }
    }

    static String findNeighbour(position p, String direction) {
        try {
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
        } catch (IndexOutOfBoundsException e) {
            return null;
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


    public static void resetMap() throws IOException {
        unvisited = new HashMap<>();
        visited = new HashMap<>();
        shortestPaths = new HashSet<>();
        initiateMap();
    }


    public static void initiateMap() throws IOException {
        visited = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        map = new char[line.length()][line.length()];
        GRIDSIZE = line.length();
        for (int i = 0; i < GRIDSIZE; i++) {
            map[i] = line.toCharArray();
            if (line.contains("E")) {
                endX = i;
                endY = line.indexOf("E");
                position s = new position(endX, endY, Integer.MAX_VALUE, "");
                unvisited.put(s.coordinate(), s);
            }
            if (line.contains("S")) {
                startX = i;
                startY = line.indexOf("S");
                position s = new position(startX, startY, 0, "");
                unvisited.put(s.coordinate(), s);
            }
            for (int j = 0; j < GRIDSIZE; j++) {
                if (map[i][j] != '#' && map[i][j] != 'S' && map[i][j] != 'E') {
                    position p = new position(i, j, Integer.MAX_VALUE, "");
                    unvisited.put(p.coordinate(), p);
                }
            }
            line = reader.readLine();
        }
    }

    private static void printMap(Set<position> currentPath, List<position> possiblePaths) {
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                position p = new position(i, j, 0, "");
                if (currentPath.contains(p)) {
                    System.out.print("0");
                } else if (possiblePaths.contains(p)) {
                    System.out.print("X");
                } else {
                    System.out.print(map[i][j]);
                }
            }
            System.out.println();
        }
    }


    private static void printMap(Collection<position> currentPath) {
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                position p = new position(i, j, 0, "");
                if (currentPath.contains(p)) {
                    System.out.print("0");
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

        public List<position> nodes;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            position position = (position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public position(int x, int y, int distance, String directionFromPrevious) {
            this.x = x;
            this.y = y;
            this.distance = distance;
            this.directionFromPrevious = directionFromPrevious;
            nodes = new ArrayList<>();
        }

        public List<position> getNodes() {
            return nodes;
        }

        public void setNodes(List<position> nodes) {
            this.nodes = nodes;
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

