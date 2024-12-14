package year2024.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static String file = "src/year2024/day13/input.txt";

    private static List<game> games = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int tokens = 0;
        findGames();
        System.out.println(games);

        for(game g: games){
           // System.out.println(winPrize(g));
            tokens += winPrize(g);
        }

        System.out.println(tokens);


    }

    private static int winPrize(game g){

        int maxTimesButtonA = Math.max(Math.abs(g.x / g.b.x ),Math.abs(g.x / g.a.x ));
        int maxTimesButtonB = Math.max(Math.abs(g.y / g.b.y ),Math.abs(g.y / g.a.y ));

        int minTokens = Integer.MAX_VALUE;

        for(int i = 1; i<=maxTimesButtonA; i++){
            for(int j = 1; j<=maxTimesButtonB; j++){
                if(g.x == (i * g.b.x) + (j* g.a.x) && g.y == (i * g.b.y) + (j* g.a.y)){
                    int tokens = (j * 3) + (i * 1);
                            if(tokens < minTokens){
                                minTokens = tokens;
                            }
                }
            }
        }
        if(minTokens == Integer.MAX_VALUE){
            return 0;
        }

        return minTokens;
    }


    private static void findGames() throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(file));
        String buttonALine = reader.readLine();
        String buttonBLine = reader.readLine();
        String prizeLine = reader.readLine();
        String empty = reader.readLine();
        while (buttonALine != null) {
            Matcher m = Pattern.compile("(?<=\\+)(\\d+)")
                    .matcher(buttonALine);
            m.find();
            int a = Integer.parseInt(m.group());
            m.find();
            int b = Integer.parseInt(m.group());

            button buttonA = new button(a, b);
            m = Pattern.compile("(?<=\\+)(\\d+)")
                    .matcher(buttonBLine);
            m.find();
            a = Integer.parseInt(m.group());
            m.find();
            b = Integer.parseInt(m.group());
            button buttonb = new button(a, b);
            m = Pattern.compile("(?<==)(\\d+)")
                    .matcher(prizeLine);
            m.find();
            a = Integer.parseInt(m.group());
            m.find();
            b = Integer.parseInt(m.group());
            game game = new game(a, b, buttonA, buttonb);

            games.add(game);

            buttonALine = reader.readLine();
            buttonBLine = reader.readLine();
            prizeLine = reader.readLine();
            empty = reader.readLine();
        }
        reader.close();
    }


    record game(int x, int y, button a, button b) {

    }

    record button(int x, int y) {

    }
}