package pdm.di.ubi.pt.tw_ilmanaque;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;




public class Receiver extends BroadcastReceiver { //Broadcast para detectar evento do sistema e enviar as notificações


    @Override
    public void onReceive(Context context, Intent intento) {


        if (intento.getAction().equals(Intent.ACTION_DATE_CHANGED)) {

            AjudanteBD ajudanteBD = new AjudanteBD(context);
            SQLiteDatabase db = ajudanteBD.getWritableDatabase();


            Cursor queryres = ajudanteBD.getLembretes(); //query para obter todos os lembretes

            queryres.moveToFirst();


            try {

                for (int i = 0; i < queryres.getCount(); i++) {

                    String data_lembrete = queryres.getString(2);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    Date data = format.parse(data_lembrete);


                if (data.getTime() > (System.currentTimeMillis() - (86400000/2)) && data.getTime() < (System.currentTimeMillis() + (86400000/2))) {

                    Intent notificationIntent = new Intent(context, Lembretes.class);
                    long[] pattern = {0,300,0};
                    PendingIntent p1 = PendingIntent.getActivity(context,i,notificationIntent,0);

                    NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.icon_app)
                            .setContentTitle("Lembrete")
                            .setContentText(queryres.getString(1))
                            .setVibrate(pattern)
                            .setAutoCancel(true);


                    nbuilder.setContentIntent(p1);
                    nbuilder.setDefaults(Notification.DEFAULT_SOUND);
                    nbuilder.setAutoCancel(true);
                    NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(i, nbuilder.build());

                }

                queryres.moveToNext();

                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            db.close();
        }




    }
}
