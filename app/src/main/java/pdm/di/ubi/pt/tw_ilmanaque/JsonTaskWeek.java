package pdm.di.ubi.pt.tw_ilmanaque;


import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class JsonTaskWeek extends AsyncTask<String, Void, ArrayList<TempoSemanal>> {

    Auxiliar aux = new Auxiliar();
    ArrayList<TempoSemanal> arrayTempoSemanal = new ArrayList<>();


    @Override
    protected ArrayList<TempoSemanal> doInBackground(String... strings) {

        String result =""; // dados do json
        URL url;
        HttpURLConnection urlConnection = null;

        try {

            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream instream = urlConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(instream);

            int data = reader.read();



            while (data!=-1){
                char current = (char) data;

                result += current;

                data = reader.read();
            }



            try {

                DecimalFormat df = new DecimalFormat("#.###");
                JSONObject jsonObject = new JSONObject(result);
                JSONObject jsonCity = new JSONObject(jsonObject.getString("city"));
                String cidade = jsonCity.getString("name");


                //lista dos dias
                JSONArray jsonList = new JSONArray(jsonObject.getString("list"));

                //pegar na data de hoje
                Date todayDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

                //criação dos arrays dos dias seguintes ao dia de hoje
                ArrayList<String> arrayDays = new ArrayList<>();
                for (int i = 1; i < 6; i++) {
                    Calendar calendar = new GregorianCalendar();
                    calendar.add(Calendar.DATE, i);
                    String day = sdf.format(calendar.getTime());
                    arrayDays.add(day);
                }

                int flag=0;

                for(int i = 0; i<jsonList.length(); i++){
                    TempoSemanal oTempoSemanal = new TempoSemanal();

                    JSONObject oJson = jsonList.getJSONObject(i);
                    String date_string = oJson.getString("dt_txt");
                    Date date_date = sdf.parse(date_string);
                    String final_date = sdf.format(date_date);//usar esta e o equals


                    if(final_date.equals(arrayDays.get(0)))
                    {


                        flag++;
                        if(flag==5) {

                            JSONObject ojsonMainObject = oJson.getJSONObject("main");

                            double temp_media_fake = Double.parseDouble(ojsonMainObject.getString("temp"))-273.15;

                            int humidade = Integer.parseInt(ojsonMainObject.getString("humidity"));

                            JSONArray jsonArray = oJson.getJSONArray("weather");

                            JSONObject ojsconObjectWeather = jsonArray.getJSONObject(0);

                            String estadoDoCeu = ojsconObjectWeather.getString("main");

                            String dt_string = oJson.getString("dt");
                            //


                            //

                            oTempoSemanal.setTemp(temp_media_fake);
                            oTempoSemanal.setHumity(humidade);
                            oTempoSemanal.setWeather(aux.parseWeatherCondition(estadoDoCeu));
                            oTempoSemanal.setDt(dt_string);
                            oTempoSemanal.setDate(final_date);

                            oTempoSemanal.setCity_name(cidade);//add ao objeto

                            arrayTempoSemanal.add(oTempoSemanal);

                            flag=0;
                            arrayDays.remove(0);

                            if(arrayDays.isEmpty())
                                break;
                        }
                    }

                }

                aux = new Auxiliar();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }






            return arrayTempoSemanal;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
