package pdm.di.ubi.pt.tw_ilmanaque;
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

        return s;
    }






}
