package year2024.day12;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main2 {
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
        double start = System.nanoTime();
        findRegions(position);
        cost = findCost();
        double end = System.nanoTime();
        System.out.println((end - start)/1000000 + "ms");

        //System.out.println(cost);
        // printMap(position.map);
        //System.out.println(regions);
    }


    private static int findCost() {
        int cost = 0;
        for (String s : regions.keySet()) {
            for (region r : regions.get(s)) {


                int perimeter = findPerimeter(r.points);
                int size = r.points().size();
                //println("Cost " + s + " size " + size +  " x " + perimeter + " sides" );

                cost += (perimeter * size);
            }
        }

        return cost;
    }

    private static int findPerimeter(List<point> points) {

        int sides = 0;
        if (points.size() == 1 || points.size() == 2) {
            return 4;
        }

        Map<Integer, List<point>> xaxis = new HashMap<>();
        Map<Integer, List<point>> yaxis = new HashMap<>();
        Set<point> pointstoCheck = new HashSet<>();

        //Only sides that have at least 1 neighboor has to be checked
        for (point p : points) {
            if (howManyDifferentNeighbours(p) > 0) {
                if (xaxis.get(p.x) == null) {
                    List<point> l = new ArrayList<>();
                    l.add(p);
                    xaxis.put(p.x, l);
                } else {
                    xaxis.get(p.x).add(p);
                }
                if (yaxis.get(p.y) == null) {
                    List<point> l = new ArrayList<>();
                    l.add(p);
                    yaxis.put(p.y, l);
                } else {
                    yaxis.get(p.y).add(p);
                }
                pointstoCheck.add(p);
            }
        }

        //check all different X axis
        sides += getSidesForYaxis(yaxis);

        sides += getSidesForXaxis(xaxis);


        return sides;
    }

    private static int getSidesForXaxis(Map<Integer, List<point>> xaxis) {
        int sides = 0;
        for (Integer y : xaxis.keySet()) {
            List<point> p1 = xaxis.get(y);
            p1.sort(((o1, o2) -> o1.y > o2.y ? 1 : o1.y < o2.y ? -1 : 0));

            //Find how many segments this wall is
            List<List<point>> segments = new ArrayList<>();
            List<point> segment = new ArrayList<>();
            segment.add(p1.get(0));
            if(p1.size() == 1){
                segments.add(segment);
            }
            for (int i = 1; i < p1.size(); i++) {
                if (Math.abs(p1.get(i).y - p1.get(i - 1).y) == 1) {
                    segment.add(p1.get(i));
                } else {
                    segments.add(segment);
                    segment = new ArrayList<>();
                    segment.add(p1.get(i));
                }
                if (i == p1.size() - 1) {
                    segments.add(segment);
                }
            }
            //check the left and right side of the segment. count the number of times it has a neighbour of the same region and other regions
            /***
             * If this is 1 segment. Region B has 3 sides because of the interuption by one of its own
             *              ABC
             *              ABC
             *              BBC
             *              ABC
             *              ABC
             */
            for (List<point> segmentToCheck : segments) {
                int sidesOnUp = 0;
                int sidesOnDown = 0;
                Boolean ownRegionUp = null;
                Boolean ownRegionDown = null;
                for (point p_ : segmentToCheck) {
                    //set first toggle
                    Step up = takeStep("up", positionAndMap.map, p_.x, p_.y);
                    Step down = takeStep("down", positionAndMap.map, p_.x, p_.y);

                    if (up == null) {
                        sidesOnUp = 1;
                        up = new Step('!', 0, 0);
                    }
                    if (down == null) {
                        sidesOnDown = 1;
                        down = new Step('!', 0, 0);
                    }
                    //initialise the booleans
                    if (ownRegionUp == null) {
                        ownRegionUp = up.object == map[p_.x][p_.y];
                        if (!ownRegionUp) {
                            sidesOnUp = 1;
                        }
                        ownRegionDown = down.object == map[p_.x][p_.y];
                        if (!ownRegionDown) {
                            sidesOnDown = 1;
                        }
                    }

                    if (down.object != map[p_.x][p_.y] && ownRegionDown) {
                        sidesOnDown++;
                    }
                    if (up.object != map[p_.x][p_.y] && ownRegionUp) {
                        sidesOnUp++;
                    }

                    ownRegionDown = (down.object == map[p_.x][p_.y]);
                    ownRegionUp = (up.object == map[p_.x][p_.y]);
                }
                sides += sidesOnUp + sidesOnDown;
            }
        }
        return sides;
    }

    private static int getSidesForYaxis(Map<Integer, List<point>> yaxis) {
        int sides = 0;
        for (Integer x : yaxis.keySet()) {

            //sort all points on X axis
            List<point> p1 = yaxis.get(x);

            p1.sort(((o1, o2) -> o1.x > o2.x ? 1 : o1.x < o2.x ? -1 : 0));

            //Find how many segments this wall is
            List<List<point>> segments = new ArrayList<>();
            List<point> segment = new ArrayList<>();
            segment.add(p1.get(0));
            if(p1.size() == 1){
                segments.add(segment);
            }
            for (int i = 1; i < p1.size(); i++) {
                if (Math.abs(p1.get(i).x - p1.get(i - 1).x) == 1) {
                    segment.add(p1.get(i));
                } else {
                    segments.add(segment);
                    segment = new ArrayList<>();
                    segment.add(p1.get(i));
                }
                if (i == p1.size() - 1) {
                    segments.add(segment);
                }
            }
            //check the left and right side of the segment. count the number of times it has a neighbour of the same region and other regions
            /***
             * If this is 1 segment. Region B has 3 sides because of the interuption by one of its own
             *              ABC
             *              ABC
             *              BBC
             *              ABC
             *              ABC
             */
            for (List<point> segmentToCheck : segments) {
                int sidesOnTheLeft = 0;
                int sidesOnTheRight = 0;
                Boolean ownRegionLeft = null;
                Boolean ownRegionRight = null;
                for (point p_ : segmentToCheck) {
                    //set first toggle
                    Step left = takeStep("left", positionAndMap.map, p_.x, p_.y);
                    Step right = takeStep("right", positionAndMap.map, p_.x, p_.y);

                    if (left == null) {
                        sidesOnTheLeft = 1;
                        left = new Step('!', 0, 0);
                    }
                    if (right == null) {
                        sidesOnTheRight = 1;
                        right = new Step('!', 0, 0);
                    }
                    //initialise the booleans
                    if (ownRegionRight == null) {
                        ownRegionRight = right.object == map[p_.x][p_.y];
                        if (!ownRegionRight) {
                            sidesOnTheRight = 1;
                        }
                        ownRegionLeft = left.object == map[p_.x][p_.y];
                        if (!ownRegionLeft) {
                            sidesOnTheLeft = 1;
                        }
                    }
                    if (left.object != map[p_.x][p_.y] && ownRegionLeft) {
                        sidesOnTheLeft++;
                    }
                    if (right.object != map[p_.x][p_.y] && ownRegionRight) {
                        sidesOnTheRight++;
                    }

                    ownRegionLeft = (left.object == map[p_.x][p_.y]);
                    ownRegionRight = (right.object == map[p_.x][p_.y]);

                }
                sides += sidesOnTheLeft + sidesOnTheRight;
            }
        }
        return sides;
    }


    public static int howManyDifferentNeighbours(point p) {
        int neighbours = 0;
        char c = positionAndMap.map[p.x][p.y];
        Step up = takeStep("up", positionAndMap.map, p.x, p.y);
        if (up == null || up.object != c) {
            neighbours++;
        }
        Step down = takeStep("down", positionAndMap.map, p.x, p.y);
        if (down == null || down.object != c) {
            neighbours++;
        }
        Step left = takeStep("left", positionAndMap.map, p.x, p.y);
        if (left == null || left.object != c) {
            neighbours++;
        }
        Step right = takeStep("right", positionAndMap.map, p.x, p.y);
        if (right == null || right.object != c) {
            neighbours++;
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
            return null;
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

