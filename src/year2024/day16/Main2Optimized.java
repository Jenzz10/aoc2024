package year2024.day16;

import javax.swing.text.Position;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main2Optimized {
    static int GRIDSIZE = 50;
    static String file = "src/year2024/day16/test.txt";

    static char[][] map;
    static int startX, startY, endX, endY;
    static Map<String, position> visited = new HashMap<>();
    static Map<String, position> unvisited = new HashMap<>();
    static Set<position> shortestPaths = new HashSet<>();
    static List<position> startPositionsToCheck = new ArrayList<>();

    static int shortestDistance = Integer.MAX_VALUE;


    public static void main(String[] args) throws IOException {
        initiateMap();
        double start = System.nanoTime();
        while (!unvisited.isEmpty()) {
            walk();
        }
        shortestDistance = visited.get(endX + "," + endY).distance;

        shortestPaths.addAll(findShortestPath(visited.get(endX + "," + endY), new ArrayList<>()));
        //System.out.println(shortestPaths);
        System.out.println("testing "+shortestPaths.size());
        for (position p : shortestPaths) {
            if (p.nodes.size() > 2) {
                for (position node : p.nodes) {
                    if (!shortestPaths.contains(node)) {
                        //this is a neighbour on the shortest path that is not a part of the shortestPath
                        System.out.println(node);
                        startPositionsToCheck.add(node);
                    }
                }
            }
        }
       // printMap(shortestPaths, startPositionsToCheck);

        //findIfTheShortestPathFromACornerPosition
        for (position p : startPositionsToCheck) {
            resetMap(p);
           // System.out.println("Testing " + startX + "," + startY);
            while (!unvisited.isEmpty()) {
                walk();
            }
            if (shortestDistance == visited.get(endX + "," + endY).distance) {
                shortestPaths.addAll(findShortestPath(visited.get(endX + "," + endY), new ArrayList<>()));
            }
        }

        //printMap(shortestPaths);

        double end = System.nanoTime();
        System.out.println("number of paths on the shortest path " + shortestPaths.size());
        System.out.println((end - start) / 1000000 + "ms");
        System.out.println("Cost " + visited.get(endX + "," + endY).distance);

    }

    private static List<position> findShortestPath(position p, List<position> shortestPath) {
        int currentLowest = Integer.MAX_VALUE;
        position shortestNeighbour = null;
        if (p.x == startX && p.y == startY) {
            return shortestPath;
        }

        for (String direction : Arrays.asList("right", "left", "up", "down")) {
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
        shortestPaths.add(shortestNeighbour);
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
                        p.nodes.add(position);
                    }
                    if (visited.containsKey(n)) {
                        p.nodes.add(visited.get(n));
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


    private static void printMap(Set<position> currentPath) {
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

