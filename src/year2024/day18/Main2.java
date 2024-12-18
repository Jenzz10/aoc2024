package year2024.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main2 {
    static int GRIDSIZE = 71;
    static String file = "src/year2024/day18/input.txt";

    static int max_bytes = 1024;

    static char[][] map = new char[GRIDSIZE][GRIDSIZE];
    static int startX, startY = 0;
    static int endY = 70;
    static int endX = 70;
    static Map<String, position> visited = new HashMap<>();
    static Map<String, position> unvisited = new HashMap<>();
    static Set<position> shortestPaths = new HashSet<>();
    static int shortestDistance = Integer.MAX_VALUE;


    public static void main(String[] args) throws IOException {


        double start = System.nanoTime();
        // for(int i= 1025; i<3451; i++){
        while (true) {
            System.out.println("trying "+max_bytes);
            map = new char[GRIDSIZE][GRIDSIZE];
            visited = new HashMap<>();
            unvisited = new HashMap<>();
            initiateMap();
            while (!unvisited.isEmpty()) {
                walk();
            }
            if (visited.get(endX + "," + endY).distance == Integer.MAX_VALUE || visited.get(endX + "," + endY).distance < 0) {
                System.out.println("it breaks at" + max_bytes);
                break;
            }
            max_bytes++;
        }
        // }


        double end = System.nanoTime();
        System.out.println((end - start) / 1000000 + "ms");
        System.out.println("Cost " + visited.get(endX + "," + endY).distance);

    }


    private static List<position> findShortestPath(position p, List<position> shortestPath) {
        int currentLowest = Integer.MAX_VALUE;
        position shortestNeighbour = null;
        if (p.x == startX && p.y == startY) {
            return shortestPath;
        }

        for (String direction : Arrays.asList("right", "up", "down", "left")) {
            String n = findNeighbour(p, direction);
            if (n != null) {
                if (shortestNeighbour == null) {
                    shortestNeighbour = visited.get(n);
                    currentLowest = visited.get(n).distance;
                }
                if (visited.get(n).distance < currentLowest) {
                    shortestNeighbour = visited.get(n);
                    currentLowest = visited.get(n).distance;
                }
            }
        }
        shortestPath.add(shortestNeighbour);
        return findShortestPath(shortestNeighbour, shortestPath);
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


    public static void resetMap(position start) throws IOException {
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
            for (int j = 0; j < GRIDSIZE; j++) {
                if (map[i][j] != '#') {
                    position p = new position(i, j, Integer.MAX_VALUE, "");
                    unvisited.put(p.coordinate(), p);
                }
            }
            line = reader.readLine();
        }
        startX = start.x;
        startY = start.y;
        position s = new position(startX, startY, start.distance, start.directionFromPrevious);
        unvisited.put(s.coordinate(), s);
    }

    public static void initiateMap() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                map[i][j] = '.';
                position p = new position(i, j, Integer.MAX_VALUE, "");
                unvisited.put(p.coordinate(), p);
            }
        }
        int i = 0;
        while (i < max_bytes) {
            String[] coor = line.split(",");
            int x = Integer.parseInt(coor[1]);
            int y = Integer.parseInt(coor[0]);
            map[x][y] = '#';
            unvisited.remove(x + "," + y);
            line = reader.readLine();
            i++;
        }
        position p = new position(0, 0, 0, "");
        unvisited.put("0,0", p);
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

