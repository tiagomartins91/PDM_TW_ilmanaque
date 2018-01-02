package pdm.di.ubi.pt.tw_ilmanaque;


import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectionDetector { // Classe que testa a ligação da app à internet

    Context context;

    public ConnectionDetector(Context context) {

        this.context = context;
    }


    public boolean isConnected(){
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Service.CONNECTIVITY_SERVICE);

        if(connectivity !=null){
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if(info != null){
                if(info.getState() == NetworkInfo.State.CONNECTED )
                    return true;
            }
        }



        return false;
    }


}