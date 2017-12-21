package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity{


    static ImageView tempo;
    static TextView cidade_name;
    static TextView temperatura;
    static ArrayList<TempoSemanal> arrayListAssyncTask = new ArrayList<>();
    ConnectionDetector oCd;
    int check_flag=0; //var que check se desde que o user ja abriu a app, ja teve gps e net ou n

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempo = (ImageView) findViewById(R.id.imagemTempo);
        cidade_name = (TextView) findViewById(R.id.cidadeTV);
        temperatura = (TextView) findViewById(R.id.tempTV);




        GPSTracker gpsTracker = new GPSTracker(this);
        Location location = gpsTracker.getLocation();//gpsTracker.getLocation();

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
            check_flag++;
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

        if(check_flag==0)
            Toast.makeText(this, "Precisa de conectar ao GPS e a Internet pelo menos uma vez", Toast.LENGTH_SHORT).show();
        else {
            Intent iIntent = new Intent(this, Calendario.class);
            startActivity(iIntent);
        }
    }

    public void abrirMainActivity(View v) {


        Intent iIntent = new Intent (this, MainActivity.class);
        startActivity(iIntent);

    }


    public void abrirInformacoes (View v){

        Intent intent = new Intent(this, Informacoes.class);
        startActivity(intent);
    }


}
