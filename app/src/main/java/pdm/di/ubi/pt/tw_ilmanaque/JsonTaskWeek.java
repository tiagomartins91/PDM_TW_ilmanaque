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
import java.util.Calendar;
import java.util.Date;

/**
 * Created by joaosaraiva on 15-12-2017.
 */

public class JsonTaskWeek extends AsyncTask<String, Void, String> {

    Auxiliar aux;


    @Override
    protected String doInBackground(String... strings) {

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

            return result;

        } catch (MalformedURLException e) {//ver q execao meter aqui
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {

            DecimalFormat df = new DecimalFormat("#.###");
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonCity = new JSONObject(jsonObject.getString("city"));

            String cidade = jsonCity.getString("name");
            System.out.println("CIDADE: " + cidade);


            //lista dos dias
            JSONArray jsonList = new JSONArray(jsonObject.getString("list"));

            //fazer presivao para HOJE (3h dps do momento atual)
            JSONObject jsonDiaHoje = jsonList.getJSONObject(1);
            JSONObject jsonMainObject = jsonDiaHoje.getJSONObject("main");

            double temp_max_hoje = Double.parseDouble(jsonMainObject.getString("temp_max"))-273.15;
            double temp_mim_hoje = Double.parseDouble(jsonMainObject.getString("temp_min"))-273.15;
            int humidade = Integer.parseInt(jsonMainObject.getString("humidity"));


            JSONArray jsonArrayWeather = jsonDiaHoje.getJSONArray("weather");

            JSONObject jsonObjectWheather = jsonArrayWeather.getJSONObject(0);
            String estadoDoCeu = jsonObjectWheather.getString("main");

            aux = new Auxiliar();
            String parsedEstadoDoCeu = aux.parseWeatherCondition(estadoDoCeu);

            String teste = jsonDiaHoje.getString("dt_txt");



            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            Date date2 = sdf.parse(teste);//chave!




            String currentDateandTime = sdf.format(date);
            String data2 = sdf.format(date2);

            System.out.println(data2);

            if(date.before(date2))
                System.out.println("after");

            System.out.println(currentDateandTime);


            Meteorologia.ceuView.setText(parsedEstadoDoCeu);
            Meteorologia.temperatureView.setText(String.valueOf(df.format(temp_max_hoje))+ "ºC");
            Meteorologia.placeView.setText(String.valueOf(cidade));
            Meteorologia.temperatureminView.setText(String.valueOf(df.format(temp_mim_hoje))+"ºC");
            Meteorologia.humidityView.setText(String.valueOf(humidade) + "%");




        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
