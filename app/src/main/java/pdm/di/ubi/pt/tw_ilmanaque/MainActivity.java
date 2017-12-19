package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    ConnectionDetector oCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempo = (ImageView) findViewById(R.id.imagemTempo);
        cidade_name = (TextView) findViewById(R.id.cidadeTV);
        temperatura = (TextView) findViewById(R.id.tempTV);

        GPSTRACKER2 teste = new GPSTRACKER2(this);

        //System.out.println("TAG " + teste.getLocation().toString());


        GPSTracker gpsTracker = new GPSTracker(this);
        Location location = teste.getLocation();//gpsTracker.getLocation();

        oCd = new ConnectionDetector(this);

        if(oCd.isConnected() && location!=null) {

            double latitude = location.getLatitude();
            double longiture = location.getLongitude();
            final AjudanteBD ajudanteBD = new AjudanteBD(this);
            final SQLiteDatabase db = ajudanteBD.getWritableDatabase();
            JsonTaskToday jsonExtracter = new JsonTaskToday();

            jsonExtracter.execute("http://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(latitude) + "&lon=" + String.valueOf(longiture) + "&appid=1e49fc78a012d7a8d3cff3325ab72334");


            JsonTaskWeek ojsonTaskWeek = new JsonTaskWeek();

            try {
                arrayListAssyncTask = ojsonTaskWeek.execute("http://api.openweathermap.org/data/2.5/forecast?lat=" + String.valueOf(latitude) + "&lon=" + String.valueOf(longiture) + "&appid=1e49fc78a012d7a8d3cff3325ab72334").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            System.out.println("Data " + arrayListAssyncTask.get(0).getDate().replaceAll("-", "/"));
            boolean existe;

            if (arrayListAssyncTask.get(0).getWeather().equals("Chuva")) {
                boolean lembretesucesso;

                existe = ajudanteBD.getLembretesexists(arrayListAssyncTask.get(0).getDate().replaceAll("-", "/"), "Chuva esperada sem muita necessidade de regar");
                if (!existe)
                    lembretesucesso = ajudanteBD.RegistarLembrete("Chuva esperada sem muita necessidade de regar", arrayListAssyncTask.get(0).getDate().replaceAll("-", "/"), 0, -1);

            } else if (!arrayListAssyncTask.get(0).getWeather().equals("Chuva") && arrayListAssyncTask.get(0).getTemp() < 25.0) {
                boolean lembretesucesso;
                existe = ajudanteBD.getLembretesexists(arrayListAssyncTask.get(0).getDate().replaceAll("-", "/"), "Sem Chuva esperada não se esqueça de regar");
                if (!existe)
                    lembretesucesso = ajudanteBD.RegistarLembrete("Sem Chuva esperada não se esqueça de regar", arrayListAssyncTask.get(0).getDate().replaceAll("-", "/"), 0, -1);

            } else if (arrayListAssyncTask.get(0).getTemp() > 25.0) {
                boolean lembretesucesso;
                existe = ajudanteBD.getLembretesexists(arrayListAssyncTask.get(0).getDate().replaceAll("-", "/"), "Temperaturas altas, regue com regularidade");
                if (!existe)
                    lembretesucesso = ajudanteBD.RegistarLembrete("Temperaturas altas, regue com regularidade", arrayListAssyncTask.get(0).getDate().replaceAll("-", "/"), 0, -1);


            }

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
