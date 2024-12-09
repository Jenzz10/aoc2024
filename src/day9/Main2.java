package day9;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main2 {

    static String file = "src/day9/input.txt";


    public static void main(String[] args) throws IOException {
        String content = new Scanner(new File(file)).useDelimiter("\\Z").next();
        double start = System.nanoTime();
        List<String> exploded = explode(content);
        //System.out.println(exploded);
        List<String> reorder = reformat(exploded);
        System.out.println(checksum(reorder));
        double end = System.nanoTime();
        //System.out.println(exploded);

        // System.out.println(reorder);


    }

    public static long checksum(List<String> reformated) {
        long total = 0;
        for (int i = 0; i < reformated.size(); i++) {
            if (reformated.get(i) != "." && reformated.get(i) != "!") {
                total += (Long.parseLong(reformated.get(i)) * i);
            }

        }
        return total;
    }

    public static List<String> reformat(List<String> exploded) {
        //exploded size 94592
        //System.out.println(exploded);
        List<String> contentString = new ArrayList<>();
        for(int s = 0; s<exploded.size();s++){
            if(exploded.get(s) == "."){
                contentString.add(".");
            }else{
                contentString.add("X");
            }
        }
        String content = String.join("", contentString);
        String currentChar = "";
        String currentFile = "";
        int size = 0;

        List<String> movedChars = new ArrayList<>();
        for (int i = (exploded.size() - 1); i > 0; i--) {
            if (!movedChars.contains(exploded.get(i)) && !".".equals(exploded.get(i)) && !"!".equals(exploded.get(i))) {
                if ("".equals(currentChar) || currentChar.equals(exploded.get(i))) {
                    currentChar = exploded.get(i);
                    currentFile += exploded.get(i);
                    size++;
                } else {
                    String sizeNeeded = ".".repeat(size);
                    int startPosition = content.indexOf(sizeNeeded);
                    int startPositionFileToBeMoved = exploded.indexOf(currentChar);
                    if (startPosition != -1 && startPosition < startPositionFileToBeMoved) {
                        movedChars.add(currentChar);
                        for (int o = 0; o < size; o++) {
                            exploded.set((o + startPosition), currentChar);
                            exploded.set((startPositionFileToBeMoved + o), "!");
                            contentString.set((o + startPosition), "!");
                            contentString.set((startPositionFileToBeMoved + o), "!");

                        }
                    //    System.out.println(exploded.toString().replace("!", "."));
                       content = String.join("", contentString);
                    }
                    currentChar = exploded.get(i);
                    currentFile = exploded.get(i);
                    size = 1;
                }

            }
        }
       // System.out.println(exploded.toString().replace("!", "."));
        return exploded;
    }


    public static List<String> explode(String content) {
        List<String> exploded = new ArrayList<>();
        char[] c = content.toCharArray();
        int currentIdNumber = 0;
        boolean file = true;
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < Integer.parseInt(String.valueOf(c[i])); j++) {
                if (file) {
                    exploded.add(currentIdNumber + "");
                } else {
                    exploded.add(".");
                }
            }
            if (file) {
                currentIdNumber++;
            }
            file = !file;
        }
        return exploded;

    }

}

