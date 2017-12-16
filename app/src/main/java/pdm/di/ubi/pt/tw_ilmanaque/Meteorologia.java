package pdm.di.ubi.pt.tw_ilmanaque;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by TiagoMartins on 15/12/2017.
 */

public class Meteorologia extends AppCompatActivity {

    static TextView placeView;
    static TextView temperatureView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meteorologia);

        placeView = (TextView) findViewById(R.id.nameTextView);
        temperatureView = (TextView) findViewById(R.id.temperatureTextView);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String provider = locationManager.getBestProvider(new Criteria(), false);

        //se n der as permissoes ver \/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        GPSTracker gpsTracker = new GPSTracker(this);

        Location location = gpsTracker.getLocation();

        double latitude = location.getLatitude();
        double longiture = location.getLongitude();

        JsonTask jsonTask = new JsonTask();

        System.out.println("Lat e long " + latitude + " " + longiture);

        jsonTask.execute("http://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(latitude) + "&lon=" + String.valueOf(longiture) + "&appid=3dff1d0003eb884c7b1856b85f7f1e84");

    }



}
