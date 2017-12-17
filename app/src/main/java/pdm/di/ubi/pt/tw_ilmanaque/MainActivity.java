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

        //teste
        AjudanteBD ajudanteBD = new AjudanteBD(this);
        SQLiteDatabase db = ajudanteBD.getWritableDatabase();

    }


    public void abrirMenuAtividades(View v){

        Intent abrirMenuAtividades = new Intent(this, Atividades.class);

        startActivity(abrirMenuAtividades);


    }




    public void abrirMenuMetereologia(View v){


        Intent abrirMenuMetereologia = new Intent(this , Meteorologia.class);

        startActivity(abrirMenuMetereologia);


    }



}
