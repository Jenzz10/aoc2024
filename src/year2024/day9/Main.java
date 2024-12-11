package year2024.day9;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    static String file = "src/year2024/day9/input.txt";


    public static void main(String[] args) throws IOException {
        String content = new Scanner(new File(file)).useDelimiter("\\Z").next();
        double start = System.nanoTime();
        List<String> exploded = explode(content);
        List<String> reorder = reformat(exploded);
        System.out.println(checksum(reorder));
        double end = System.nanoTime();
        //System.out.println(exploded);

       // System.out.println(reorder);


    }

    public static long checksum(List<String> reformated){
        long total = 0;
        for(int i =0; i<reformated.size(); i++){
            if(reformated.get(i) != "."){
                total+= (Long.parseLong(reformated.get(i)) * i);
            }

        }
        return total;
    }

    public static List<String> reformat(List<String> exploded) {

        int j = exploded.size() - 1;
        for (int i = 0; i < j; i++) {
            if (exploded.get(i) == ".") {
                String lastDigit = "";
                boolean found = false;
                while (!found) {
                    lastDigit = exploded.get(j);
                    if (lastDigit != ".") {
                        exploded.set(i, exploded.get(j));
                        exploded.set(j, ".");
                        found = true;
                    }
                    j--;
                }
                // j--;
            }
        }

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

