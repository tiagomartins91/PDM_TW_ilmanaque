package pdm.di.ubi.pt.tw_ilmanaque;


import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Calendario extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    private SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
    Auxiliar aux = new Auxiliar();
    TextView nome_mes;

    TextView tempTV;
    TextView humityTV;
    TextView cidadeTV, humidade;
    ImageView imgV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario);

        nome_mes = (TextView) findViewById(R.id.nome_mes);
        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        Date d = new Date();
        nome_mes.setText(dateFormatMonth.format(d));



        System.out.println(MainActivity.arrayListAssyncTask.toString());

        String x0 = MainActivity.arrayListAssyncTask.get(0).getDt()+"000";
        long n0 = Long.parseLong(x0);


        String x1 = MainActivity.arrayListAssyncTask.get(1).getDt() +"000";
        long n1 = Long.parseLong(x1);

        String x2 = MainActivity.arrayListAssyncTask.get(2).getDt()+"000";
        long n2 = Long.parseLong(x2);

        String x3 = MainActivity.arrayListAssyncTask.get(3).getDt()+"000";
        long n3 = Long.parseLong(x3);


        Event ev0 = new Event(aux.getColor(MainActivity.arrayListAssyncTask.get(0).getWeather()),  n0, "");
        Event ev1 = new Event(aux.getColor(MainActivity.arrayListAssyncTask.get(1).getWeather()),  n1, "");
        Event ev2 = new Event(aux.getColor(MainActivity.arrayListAssyncTask.get(2).getWeather()),  n2, "");
        Event ev3 = new Event(aux.getColor(MainActivity.arrayListAssyncTask.get(3).getWeather()),  n3, "");

        compactCalendar.addEvent(ev0);
        compactCalendar.addEvent(ev1);
        compactCalendar.addEvent(ev2);
        compactCalendar.addEvent(ev3);

        final ArrayList<TempoSemanal> finalArrayListAssyncTask = MainActivity.arrayListAssyncTask;

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();

                String dateClickedNF = sdf.format(dateClicked);
                DecimalFormat df = new DecimalFormat("#.##");

                tempTV = (TextView) findViewById(R.id.tempTV);
                humityTV = (TextView) findViewById(R.id.humidityTV);
                cidadeTV = (TextView) findViewById(R.id.cidadeTV);
                humidade = (TextView) findViewById(R.id.humidade);
                imgV = (ImageView) findViewById(R.id.imageView);


                System.out.println(dateClickedNF);


                if (dateClickedNF.equals(finalArrayListAssyncTask.get(0).getDate())) {
                    tempTV.setText(df.format(finalArrayListAssyncTask.get(0).getTemp()) + "ºC");
                    humityTV.setText(String.valueOf(finalArrayListAssyncTask.get(0).getHumity())+"%");
                    cidadeTV.setText(finalArrayListAssyncTask.get(0).getCity_name());
                    humidade.setText("Humidade");

                    if(finalArrayListAssyncTask.get(0).getWeather().equals("Céu Limpo")) {
                        imgV.setImageResource(R.mipmap.icon_sun);
                    }
                    else if(finalArrayListAssyncTask.get(0).getWeather().equals("Neve")) {
                        imgV.setImageResource(R.mipmap.icon_snow);
                    }
                    else if(finalArrayListAssyncTask.get(0).getWeather().equals("Chuva")) {
                        imgV.setImageResource(R.mipmap.icon_rain);
                    }
                    else if(finalArrayListAssyncTask.get(0).getWeather().equals("Céu nublado")) {
                        imgV.setImageResource(R.mipmap.icon_clouds);
                    }
                    else {
                        imgV.setImageResource(R.mipmap.icon_meteo);

                    }

                }
                else if (dateClickedNF.equals(finalArrayListAssyncTask.get(1).getDate())){

                    tempTV.setText(df.format(finalArrayListAssyncTask.get(1).getTemp()) + "ºC");
                    humityTV.setText(String.valueOf(finalArrayListAssyncTask.get(1).getHumity())+"%");
                    cidadeTV.setText(finalArrayListAssyncTask.get(1).getCity_name());
                    humidade.setText("Humidade");

                    if(finalArrayListAssyncTask.get(1).getWeather().equals("Céu Limpo")) {
                        imgV.setImageResource(R.mipmap.icon_sun);
                    }
                    else if(finalArrayListAssyncTask.get(1).getWeather().equals("Neve")) {
                        imgV.setImageResource(R.mipmap.icon_snow);
                    }
                    else if(finalArrayListAssyncTask.get(1).getWeather().equals("Chuva")) {
                        imgV.setImageResource(R.mipmap.icon_rain);
                    }
                    else if(finalArrayListAssyncTask.get(1).getWeather().equals("Céu nublado")) {
                        imgV.setImageResource(R.mipmap.icon_clouds);
                    }
                    else {
                        imgV.setImageResource(R.mipmap.icon_meteo);

                    }

                }
                else if (dateClickedNF.equals(finalArrayListAssyncTask.get(2).getDate())){

                    tempTV.setText(df.format(finalArrayListAssyncTask.get(2).getTemp()) + "ºC");
                    humityTV.setText(String.valueOf(finalArrayListAssyncTask.get(2).getHumity())+"%");
                    cidadeTV.setText(finalArrayListAssyncTask.get(2).getCity_name());
                    humidade.setText("Humidade");

                    if(finalArrayListAssyncTask.get(2).getWeather().equals("Céu Limpo")) {
                        imgV.setImageResource(R.mipmap.icon_sun);
                    }
                    else if(finalArrayListAssyncTask.get(2).getWeather().equals("Neve")) {
                        imgV.setImageResource(R.mipmap.icon_snow);
                    }
                    else if(finalArrayListAssyncTask.get(2).getWeather().equals("Chuva")) {
                        imgV.setImageResource(R.mipmap.icon_rain);
                    }
                    else if(finalArrayListAssyncTask.get(2).getWeather().equals("Céu nublado")) {
                        imgV.setImageResource(R.mipmap.icon_clouds);
                    }
                    else {
                        imgV.setImageResource(R.mipmap.icon_meteo);

                    }


                }
                else if (dateClickedNF.equals(finalArrayListAssyncTask.get(3).getDate())){

                    tempTV.setText(df.format(finalArrayListAssyncTask.get(3).getTemp())+ "ºC");
                    humityTV.setText(String.valueOf(finalArrayListAssyncTask.get(3).getHumity())+"%");
                    cidadeTV.setText(finalArrayListAssyncTask.get(3).getCity_name());
                    humidade.setText("Humidade");

                    if(finalArrayListAssyncTask.get(3).getWeather().equals("Céu Limpo")) {
                        imgV.setImageResource(R.mipmap.icon_sun);
                    }
                    else if(finalArrayListAssyncTask.get(3).getWeather().equals("Neve")) {
                        imgV.setImageResource(R.mipmap.icon_snow);
                    }
                    else if(finalArrayListAssyncTask.get(3).getWeather().equals("Chuva")) {
                        imgV.setImageResource(R.mipmap.icon_rain);
                    }
                    else if(finalArrayListAssyncTask.get(3).getWeather().equals("Céu nublado")) {
                        imgV.setImageResource(R.mipmap.icon_clouds);
                    }
                    else {
                        imgV.setImageResource(R.mipmap.icon_meteo);

                    }

                }

                else {
                    Toast.makeText(context, "Sem previsão para mostrar", Toast.LENGTH_LONG).show();
                    tempTV.setText("");
                    humityTV.setText("");
                    cidadeTV.setText("");
                    humidade.setText("");
                    imgV.setImageResource(0);
                }


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                nome_mes.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }

}
