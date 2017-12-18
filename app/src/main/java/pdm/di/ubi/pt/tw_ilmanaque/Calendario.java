package pdm.di.ubi.pt.tw_ilmanaque;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Calendario extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    Auxiliar aux = new Auxiliar();
    TextView nome_mes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario);

        nome_mes = (TextView) findViewById(R.id.nome_mes);
        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        Date d = new Date();
        nome_mes.setText(dateFormatMonth.format(d));



        JsonTaskWeek teste = new JsonTaskWeek();

        ArrayList<TempoSemanal> x = new ArrayList<>();
        try {
            x = teste.execute("http://api.openweathermap.org/data/2.5/forecast?lat=42.9886&lon=-81.2467&appid=1e49fc78a012d7a8d3cff3325ab72334").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(x.toString());

        String x0 = x.get(0).getDt()+"000";
        long n0 = Long.parseLong(x0);


        String x1 = x.get(1).getDt() +"000";
        long n1 = Long.parseLong(x1);

        String x2 = x.get(2).getDt()+"000";
        long n2 = Long.parseLong(x2);

        String x3 = x.get(3).getDt()+"000";
        long n3 = Long.parseLong(x3);


        Event ev0 = new Event(aux.getColor(x.get(0).getWeather()),  n0, "");
        Event ev1 = new Event(aux.getColor(x.get(1).getWeather()),  n1, "");
        Event ev2 = new Event(aux.getColor(x.get(2).getWeather()),  n2, "");
        Event ev3 = new Event(aux.getColor(x.get(3).getWeather()),  n3, "");

        compactCalendar.addEvent(ev0);
        compactCalendar.addEvent(ev1);
        compactCalendar.addEvent(ev2);
        compactCalendar.addEvent(ev3);

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();

                if (dateClicked.toString().compareTo("Fri Oct 21 00:00:00 AST 2017") == 0) {
                    Toast.makeText(context, "Teachers' Professional Day", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "No Events Planned for that day", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));

                nome_mes.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }

}
