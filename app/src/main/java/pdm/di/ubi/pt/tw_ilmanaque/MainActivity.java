package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity{


    ImageView tempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempo = (ImageView) findViewById(R.id.imagemTempo);




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
