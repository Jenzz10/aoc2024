package year2024.day12;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static int GRIDSIZE;
    static String file = "src/year2024/day12/input.txt";
    static List<point> mapped = new ArrayList<>();

    static Map<String, List<region>> regions = new HashMap<>();

    static PositionAndMap positionAndMap;

    static char[][] map;

    public static void main(String[] args) {
        int cost = 0;
        PositionAndMap position = initiateMap();
        positionAndMap = position;
        map = position.map;
        //double start = System.nanoTime();
        //double end = System.nanoTime();
        // System.out.println((end - start)/1000000 + "ms");
        findRegions(position);
        cost = findCost();

        System.out.println(cost);
        // printMap(position.map);
        //System.out.println(regions);
    }


    private static int findCost() {
        int cost = 0;
        for (String s : regions.keySet()) {
            for (region r : regions.get(s)) {
                int perimeter = findPerimeter(r.points);
                int size = r.points().size();
                System.out.println("Cost " + s + " perimeter " + perimeter + " size " + size);

                cost += (perimeter * size);
            }
        }

        return cost;
    }

    private static int findPerimeter(List<point> points) {

      /*  if (points.size() < 5) {
            Set<Integer> xpoints = new HashSet<>();
            Set<Integer> ypoints = new HashSet<>();

            for (point p : points) {
                xpoints.add(p.x);
                ypoints.add(p.y);
            }
            return xpoints.size() * 2 + ypoints.size() * 2;
        } else {*/
            int perimeter = 0;
            for (point p : points) {
              perimeter+= howManyDifferentNeighbours(p);
            }
            return perimeter;
       // }
    }

    public static int howManyDifferentNeighbours(point p) {
        int neighbours = 0;
        char c = positionAndMap.map[p.x][p.y];
        Step up = takeStep("up", positionAndMap.map, p.x, p.y);
        if (up != null) {
            if (up.object != c) {
                neighbours++;
            }
        }
        Step down = takeStep("down", positionAndMap.map, p.x, p.y);
        if (down != null) {
            if (down.object != c) {
                neighbours++;
            }
        }
        Step left = takeStep("left", positionAndMap.map, p.x, p.y);
        if (left != null) {
            if (left.object != c) {
                neighbours++;
            }
        }
        Step right = takeStep("right", positionAndMap.map, p.x, p.y);
        if (right != null) {
            if (right.object != c) {
                neighbours++;
            }
        }
        return neighbours;
    }

    public static void findRegions(PositionAndMap positionAndMap) {
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                if (!mapped.contains(new point(i, j))) {
                    createRegion(positionAndMap.map[i][j], i, j);
                    mapped.add(new point(i, j));
                }
            }
        }
    }

    private static void createRegion(char c, int x, int y) {
        String id = String.valueOf(c);
        region r = new region(id, new ArrayList<>());
        findPoints(c, x, y, r);
        if (regions.get(id) == null) {
            List<region> regions1 = new ArrayList<>();
            regions1.add(r);
            regions.put(id, regions1);
        } else {
            regions.get(id).add(r);
        }


    }

    private static void findPoints(char id, int x, int y, region r) {
        try {
            if (positionAndMap.map[x][y] == id && !mapped.contains(new point(x, y))) {
                r.points.add(new point(x, y));
                mapped.add(new point(x, y));
                findPoints(id, x + 1, y, r);
                findPoints(id, x - 1, y, r);
                findPoints(id, x, y + 1, r);
                findPoints(id, x, y - 1, r);
            }
        } catch (IndexOutOfBoundsException e) {
            var s = "";
        }


    }

    public static boolean isNeighbour(point p, point q) {
        if (p.x == q.x) {
            return (p.y + 1 == q.y || p.y - 1 == q.y);
        }
        if (p.y == q.y) {
            return (p.x + 1 == q.x || p.x - 1 == q.x);
        }
        return false;
    }


    private static String changeDirection(String direction) {
        if (direction == "up") {
            direction = "right";
        } else if (direction.equals("right")) {
            direction = "down";
        } else if (direction.equals("down")) {
            direction = "left";
        } else {
            direction = "up";
        }

        return direction;
    }

    private static Step takeStep(String direction, char[][] map, int x, int y) {
        try {
            if (direction.equals("up")) {
                return new Step(map[x - 1][y], x - 1, y);
            }
            if (direction.equals("down")) {
                return new Step(map[x + 1][y], x + 1, y);
            }
            if (direction.equals("right")) {
                return new Step(map[x][y + 1], x, y + 1);
            }
            if (direction.equals("left")) {
                return new Step(map[x][y - 1], x, y - 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return new Step('=', x, y);
        }
        return null;
    }

    public static PositionAndMap initiateMap() {
        BufferedReader reader;
        char[][] map;
        int x = 0;
        int y = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            map = new char[line.length()][line.length()];
            GRIDSIZE = line.length();
            int i = 0;
            while (line != null) {
                map[i] = line.toCharArray();
                i++;
                line = reader.readLine();
            }
            return new PositionAndMap(map, x, y);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printMap(char[][] map) {
        for (int i = 0; i < GRIDSIZE; i++) {
            System.out.println("" + new String(map[i]));
        }
    }

    record PositionAndMap(char[][] map, int x, int y) {
    }

    record Step(char object, int x, int y) {
    }

    record point(int x, int y) {
    }

    record region(String id, List<point> points) {

    }

}

