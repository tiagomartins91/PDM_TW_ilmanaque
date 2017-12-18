package pdm.di.ubi.pt.tw_ilmanaque;
import android.graphics.Color;

import java.util.Calendar;
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

        if(s.equals("Drizzle"))
            return "Chuva";

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





}
