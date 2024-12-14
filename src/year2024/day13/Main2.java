package year2024.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2 {
    static String file = "src/year2024/day13/test.txt";

    private static List<game> games = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        double tokens = 0;
        findGames();
        System.out.println(games);

        System.out.println(games.size());
        int i = 0;
        for(game g: games){
            tokens += winPrize(g);
        }

        System.out.println(tokens);


    }

    private static double winPrize(game g){

        double maxTimesButtonA = Math.max(Math.abs(g.x / g.b.x ),Math.abs(g.x / g.a.x ));
        double maxTimesButtonB = Math.max(Math.abs(g.y / g.b.y ),Math.abs(g.y / g.a.y ));

        double startA = Math.min(Math.abs(g.x / g.b.x ),Math.abs(g.x / g.a.x ));
        double startB = Math.min(Math.abs(g.y/ g.b.y ),Math.abs(g.y / g.a.y )) ;

        double minTokens = Double.MAX_VALUE;

        for(double i = 0; i<=maxTimesButtonA; i++){
            for(double j = 0; j<=maxTimesButtonB; j++){
                if(g.x == (i * g.b.x) + (j* g.a.x) && g.y == (i * g.b.y) + (j* g.a.y)){
                    double tokens = (j * 3) + (i * 1);
                            if(tokens < minTokens){
                                minTokens = tokens;
                            }
                }
            }
        }


        if(minTokens == Double.MAX_VALUE){
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
            double a = Double.parseDouble(m.group());
            m.find();
            double b = Double.parseDouble(m.group());

            button buttonA = new button(a, b);
            m = Pattern.compile("(?<=\\+)(\\d+)")
                    .matcher(buttonBLine);
            m.find();
            a = Double.parseDouble(m.group());
            m.find();
            b = Double.parseDouble(m.group());
            button buttonb = new button(a, b);
            m = Pattern.compile("(?<==)(\\d+)")
                    .matcher(prizeLine);
            m.find();
            a = Double.parseDouble(m.group()) + 1000;
           // a = Long.parseLong(m.group()) + 10000000000000l;
            m.find();
            b = Double.parseDouble(m.group()) + 1000;
          //  b = Integer.parseInt(m.group()) + 10000000000000l;
            game game = new game(a, b, buttonA, buttonb);

            games.add(game);

            buttonALine = reader.readLine();
            buttonBLine = reader.readLine();
            prizeLine = reader.readLine();
            empty = reader.readLine();
        }
        reader.close();
    }


    record game(double x, double y, button a, button b) {

    }

    record button(double x, double y) {

    }
}