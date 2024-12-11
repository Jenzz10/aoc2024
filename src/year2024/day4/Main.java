package year2024.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    static int MAX = 140;

    public static void main(String[] args) {
        int total = 0;

        char[] xmas = new char[]{'X','M','A','S'};
        char[][] grid = new char[MAX][MAX];
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("src/year2024/day4/input.txt"));
            String line = reader.readLine();
            int index = 0;
            while (line != null) {
                grid[index] = line.toCharArray();
                line = reader.readLine();
                index++;
            }

            for (int i = 0; i < MAX; i++) {
                for (int j = 0; j < MAX; j++) {
                    if(grid[i][j] == 'X') {
                        total += checkNorth(grid, i, j, grid[i][j]);
                        total += checkEast(grid, i, j, grid[i][j]);
                        total += checkSouth(grid, i, j, grid[i][j]);
                        total += checkWest(grid, i, j, grid[i][j]);
                        total += checkNorthEast(grid, i, j, grid[i][j]);
                        total += checkSouthEast(grid, i, j, grid[i][j]);
                        total += checkSouthWest(grid, i, j, grid[i][j]);
                        total += checkNorthWest(grid, i, j, grid[i][j]);
                    }
                }
            }
            System.out.println("TOTAL :::::");
            System.out.println(total);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int checkNorthWest(char[][] grid, int row, int column, char c) {
        if(row < 3 || column < 3){
            return 0;
        }
        return "XMAS".equals(testXMAS(grid[row][column], grid[row - 1][column - 1],grid[row - 2][column - 2], grid[row - 3][column - 3 ])) ? 1 : 0;
    }

    private static int checkSouthWest(char[][] grid, int row, int column, char c) {
        if(row > (MAX -4) || column < 3){
            return 0;
        }
        return "XMAS".equals(testXMAS(grid[row][column], grid[row + 1][column - 1],grid[row + 2][column - 2], grid[row + 3][column - 3 ])) ? 1 : 0;

    }

    private static int checkSouthEast(char[][] grid, int row, int column, char c) {
        if(row > (MAX - 4) || column > (MAX - 4)){
            return 0;
        }
        return "XMAS".equals(testXMAS(grid[row][column], grid[row + 1][column + 1],grid[row + 2][column + 2], grid[row + 3][column + 3 ])) ? 1 : 0;
    }

    private static int checkNorthEast(char[][] grid, int row, int column, char c) {
        if(row < 3 || column > (MAX - 4)){
            return 0;
        }
        return "XMAS".equals(testXMAS(grid[row][column], grid[row - 1][column + 1],grid[row - 2][column + 2], grid[row - 3][column + 3 ])) ? 1 : 0;

    }

    private static int checkWest(char[][] grid, int row, int column, char c) {
        if(column < 3){
            return 0;
        }
        return "XMAS".equals(testXMAS(grid[row][column], grid[row][column - 1],grid[row][column - 2], grid[row][column - 3 ])) ? 1 : 0;

    }

    private static int checkSouth(char[][] grid, int row, int column, char c) {
        if(row > (MAX - 4)){
            return 0;
        }
        return "XMAS".equals(testXMAS(grid[row][column], grid[row + 1][column],grid[row + 2][column], grid[row + 3][column])) ? 1 : 0;

    }

    private static int checkEast(char[][] grid, int row, int column, char c) {
        if(column > (MAX - 4)){
            return 0;
        }
        return "XMAS".equals(testXMAS(grid[row][column], grid[row][column + 1],grid[row][column + 2], grid[row][column + 3 ])) ? 1 : 0;

    }

    private static int checkNorth(char[][] grid, int row, int column, char c) {
        //can't form the word XMAS to north in first 3 rows
        if(row < 3){
            return 0;
        }
        return "XMAS".equals(testXMAS(grid[row][column], grid[row - 1][column],grid[row - 2][column], grid[row - 3][column])) ? 1 : 0;
    }


    private static String testXMAS(char X, char M, char A, char S){
        char[] test = new char[]{X,M,A,S};
        return String.valueOf(test);
    }
}