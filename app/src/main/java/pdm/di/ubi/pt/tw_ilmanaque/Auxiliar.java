package pdm.di.ubi.pt.tw_ilmanaque;
import android.graphics.Color;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by saraiva on 17-12-2017.
 */

public class Auxiliar {

    public Auxiliar() {
    }

    //Clear Rain Clouds
    String parseWeatherCondition(String s){

        if(s.equals("Clear"))
            return "Céu Limpo";

        if(s.equals("Rain"))
            return "Chuva";

        if(s.equals("Clouds"))
            return "Céu nublado";

        if(s.equals("Snow"))
            return "Neve";

        return s;
    }


    int getColor (String s){
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


    public int verify(String s)
    {
        Pattern p = Pattern.compile("\\d\\d\\d\\d/\\d\\d/\\d\\d");
        Matcher m = p.matcher(s);
        if(m.matches())
            return 1;
        return 0 ;

    }


}
