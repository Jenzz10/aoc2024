package year2024.day15;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {
    static int GRIDSIZE = 50;
    static String file = "src/year2024/day15/test.txt";

    static char[][] map;

    static char[] moves;

    static int robot_X;
    static int robot_Y;


    public static void main(String[] args) throws IOException {
        initiateMap();
        double start = System.nanoTime();
        startWalking();
        System.out.println("GPS : " + findGPS());
        double end = System.nanoTime();
        System.out.println(new String(moves));
        System.out.println("Robot start at " + robot_X + ", " + robot_Y);
        System.out.println((end - start) / 1000000 + "ms");
    }

    private static int findGPS() {
        int total = 0;
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                if (map[i][j] == 'O') {
                    total += ((i * 100) + j);
                }
            }
        }
        return total;

    }

    private static void startWalking() {

        for (int i = 0; i < moves.length; i++) {
            System.out.println("");
            char move = moves[i];
            System.out.println("Move " + move);
            position emptySpace = canMove(robot_X, robot_Y, move);
            if (emptySpace != null) {
                move(move, emptySpace);
            }
            printMap();
        }
    }

    private static void move(char move, position emptySpace) {
        if (move == '<') {
            int i = emptySpace.y;
            while (i <= robot_Y) {
                map[robot_X][i] = map[robot_X][i + 1];
                i++;
            }
            map[robot_X][robot_Y] = '.';
            robot_Y = robot_Y - 1;
        } else if (move == '^') {
            int i = emptySpace.x;
            while (i <= robot_X) {
                map[i][robot_Y] = map[i + 1][robot_Y];
                i++;
            }
            map[robot_X][robot_Y] = '.';
            robot_X = robot_X - 1;
        } else if (move == '>') {
            int i = emptySpace.y;
            while (i >= robot_Y) {
                map[robot_X][i] = map[robot_X][i - 1];
                i--;
            }
            map[robot_X][robot_Y] = '.';
            robot_Y = robot_Y + 1;
        } else {
            int i = emptySpace.x;
            while (i >= robot_X) {
                map[i][robot_Y] = map[i - 1][robot_Y];
                i--;
            }
            map[robot_X][robot_Y] = '.';
            robot_X = robot_X + 1;
        }
    }


    private static position canMove(int robotX, int robotY, char move) {
        if (move == '<') {
            //there must be 1 space open to the left in order to be able to move
            for (int i = robotY - 1; i > 0; i--) {
                if (map[robotX][i] == '.') {
                    return new position(robotX, i);
                }
                if (map[robotX][i] == '#') {
                    return null;
                }
            }

        } else if (move == '^') {
            for (int i = robotX - 1; i > 0; i--) {
                if (map[i][robotY] == '.') {
                    return new position(i, robotY);
                }
                if (map[i][robotY] == '#') {
                    return null;
                }
            }
        } else if (move == '>') {
            for (int i = robotY + 1; i < GRIDSIZE; i++) {
                if (map[robotX][i] == '.') {
                    return new position(robotX, i);
                }
                if (map[robotX][i] == '#') {
                    return null;
                }
            }
        } else {
            for (int i = robotX + 1; i < GRIDSIZE; i++) {
                if (map[i][robotY] == '.') {
                    return new position(i, robotY);
                }
                if (map[i][robotY] == '#') {
                    return null;
                }
            }
        }

        return null;
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

    public static void initiateMap() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        String moveString = "";
        map = new char[line.length()][line.length()];
        GRIDSIZE = line.length();
        for (int i = 0; i < GRIDSIZE; i++) {
            map[i] = line.toCharArray();
            if (line.contains("@")) {
                robot_X = i;
                robot_Y = line.indexOf("@");
            }
            line = reader.readLine();
        }
        while (line != null) {
            moveString += line;
            line = reader.readLine();
        }
        moves = moveString.toCharArray();
    }


    public static void printMap() {
        for (int i = 0; i < GRIDSIZE; i++) {
            System.out.println("" + new String(map[i]));
        }
    }

    record position(int x, int y) {
    }

}

