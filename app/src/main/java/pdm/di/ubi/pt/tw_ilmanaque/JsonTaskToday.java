package pdm.di.ubi.pt.tw_ilmanaque;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by saraiva on 17-12-2017.
 */

public class JsonTaskToday extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... strings) {

        String result =""; //dados do json
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
                result+=current;
                data = reader.read();
            }

            return result;

        } catch (MalformedURLException e) {
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
            JSONObject jsonObject = new JSONObject(result);
            JSONObject weatherData = new JSONObject(jsonObject.getString("main"));

            double temperature = Double.parseDouble(weatherData.getString("temp"));
            double temperaturaCelsius = temperature - 273.15;
            String placeName = jsonObject.getString("name");

        }catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
