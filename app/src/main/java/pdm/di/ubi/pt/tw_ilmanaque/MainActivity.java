package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void abrirMenuAtividades(View v){
        Intent abrirMenuAtividades = new Intent(this, Atividades.class);
        startActivity(abrirMenuAtividades);
    }

    public void abrirLembretes(View v){
        Intent abrirLembretes = new Intent(this, Lembretes.class);
        startActivity(abrirLembretes);
    }



    public void abrirMenuMetereologia(View v){
        Intent abrirMenuMetereologia = new Intent(this , Meteorologia.class);
        startActivity(abrirMenuMetereologia);
    }

    public void abrirCalendario (View v){
        Intent iIntent = new Intent (this, Calendario.class);
        startActivity(iIntent);
    }


}
