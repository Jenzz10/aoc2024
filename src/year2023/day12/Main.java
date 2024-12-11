package year2023.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        BufferedReader reader;
        int total = 0;
        try {
            reader = new BufferedReader(new FileReader("src/year2023/day12/test.txt"));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                String[] split = line.split(" ");
                String[] map = split[1].split(",");
                total += findPossibilities(split[0], map, 0);
                line = reader.readLine();
            }
            reader.close();
            System.out.println(total);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int findPossibilities(String springs, String[] map, int index){
        System.out.println("testing "+springs);
        if(isCorrect(springs,map)){
            return 1;
        }

        if(!isPossible(springs,map)){
            return 0;
        }

        for(int i = index; i<springs.length();i++){
            if(springs.toCharArray()[i] == '?'){
                StringBuilder tempHash = new StringBuilder(springs);
                StringBuilder tempDot = new StringBuilder(springs);
                tempHash.setCharAt(i,'#');
                tempDot.setCharAt(i,'.');
                return findPossibilities(tempHash.toString(), map, i) + findPossibilities(tempDot.toString(), map, i);
            }
        }
        return 0;
    }

    private static boolean isPossible(String springs, String[] map){

       // "[\\.|\\?]?[\\#|\\?]{1}[\\.|\\?]+[\\#|\\?]{1}[\\.|\\?]+[\\#|\\?]{3}[\\.|\\?]+"
        String s = "[\\.|\\?]*";
        for( int i =0; i<map.length;i++){
            s+="[\\#|\\?]{"+map[i]+"}[\\.|\\?]+";
        }
        return springs.matches(s);
    }


    private static boolean isCorrect(String springs, String[] map){
        //\#{1}\.+\#{1}\.+\#{3}
        String s = "";
        for( int i =0; i<map.length-1;i++){
            s+="\\#{"+map[i]+"}\\.+";
        }
        s+="\\#{"+map[map.length-1]+"}";

        return springs.matches(s);
    }


}