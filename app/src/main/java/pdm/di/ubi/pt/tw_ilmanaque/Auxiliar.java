package pdm.di.ubi.pt.tw_ilmanaque;

import android.graphics.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Auxiliar {  // Classe Auxiliar para ajudar noutras classes java

    public Auxiliar() {
    }

    //Clear Rain Clouds
    String parseWeatherCondition(String s){   //tradução

        if(s.equals("Clear"))
            return "Céu Limpo";

        if(s.equals("Rain"))
            return "Chuva";

        if(s.equals("Clouds"))
            return "Céu nublado";

        if(s.equals("Snow"))
            return "Neve";

        if(s.equals("Drizzle"))
            return "Chuva";

        return s;
    }


    int getColor (String s){ //identificação por cores

        if (s.equals("Céu Limpo"))
            return Color.GREEN;

        if(s.equals("Chuva"))
            return Color.BLUE;

        if(s.equals("Céu nublado"))
            return Color.GRAY;

        if(s.equals("Neve"))
            return Color.WHITE;


        return Color.RED;
    }


    public int verify(String s) {   //verificação

        Pattern p = Pattern.compile("\\d\\d\\d\\d/\\d\\d/\\d\\d");
        Matcher m = p.matcher(s);
        if(m.matches())
            return 1;
        return 0 ;

    }


}
