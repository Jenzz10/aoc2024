package year2024.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main_recursive {
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
                move(move, robot_X, robot_Y);
                changeRobotPosition(move);
            }
            printMap();
        }
    }

    private static void changeRobotPosition(char move) {
        if (move == '<') {
           robot_Y -=1;
        }
        if (move == '>') {
           robot_Y +=1;
        }
        if (move == '^') {
            robot_X -=1;
        }
        if (move == 'v') {
            robot_X +=1;
        }
    }

    private static void move(char move, int x, int y) {
        if (move == '<') {
            if (map[x][y - 1] != '.') {
                move(move, x, y - 1);
            }
            map[x][y - 1] = map[x][y];
            map[x][y] = '.';
        }
        if (move == '>') {
            if (map[x][y + 1] != '.') {
                move(move, x, y + 1);
            }
            map[x][y + 1] = map[x][y];
            map[x][y] = '.';
        }
        if (move == '^') {
            if (map[x - 1][y] != '.') {
                move(move, x - 1, y);
            }
            map[x - 1][y] = map[x][y];
            map[x][y] = '.';
        }
        if (move == 'v') {
            if (map[x + 1][y] != '.') {
                move(move, x + 1, y);
            }
            map[x + 1][y] = map[x][y];
            map[x][y] = '.';
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

