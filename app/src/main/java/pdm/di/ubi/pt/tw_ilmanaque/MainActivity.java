package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity{


    static ImageView tempo;
    static TextView cidade_name;
    static TextView temperatura;
    static ArrayList<TempoSemanal> arrayListAssyncTask = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempo = (ImageView) findViewById(R.id.imagemTempo);
        cidade_name = (TextView) findViewById(R.id.cidadeTV);
        temperatura = (TextView) findViewById(R.id.tempTV);

        GPSTracker gpsTracker = new GPSTracker(this);
        Location location = gpsTracker.getLocation();

        double latitude = location.getLatitude();
        double longiture = location.getLongitude();

        JsonTaskToday jsonExtracter = new JsonTaskToday();

        jsonExtracter.execute("http://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(latitude) + "&lon="  + String.valueOf(longiture) + "&appid=1e49fc78a012d7a8d3cff3325ab72334");



        JsonTaskWeek ojsonTaskWeek = new JsonTaskWeek();

        try {
            arrayListAssyncTask = ojsonTaskWeek.execute("http://api.openweathermap.org/data/2.5/forecast?lat="+String.valueOf(latitude)+"&lon="+String.valueOf(longiture)+"&appid=1e49fc78a012d7a8d3cff3325ab72334").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


    public void abrirMenuAtividades(View v){
        Intent abrirMenuAtividades = new Intent(this, Atividades.class);
        startActivity(abrirMenuAtividades);
    }

    public void abrirLembretes(View v){
        Intent abrirLembretes = new Intent(this, Lembretes.class);
        startActivity(abrirLembretes);
    }


    public void abrirCalendario (View v){
        Intent iIntent = new Intent (this, Calendario.class);
        startActivity(iIntent);
    }


}
