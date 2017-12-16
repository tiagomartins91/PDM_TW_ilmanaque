package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by TiagoMartins on 15/12/2017.
 */

public class RegistoAtividades extends AppCompatActivity {



    EditText nomeplanta, terreno, quantidade, data;
    Button registar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registoatividades);

        nomeplanta = (EditText) findViewById(R.id.etNomePlanta);
        terreno = (EditText) findViewById(R.id.etTerreno);
        quantidade= (EditText) findViewById(R.id.etQuantidade);
        data = (EditText) findViewById(R.id.etData);
        registar = (Button) findViewById(R.id.bRegistar);
    }



    public void RegistarAtividade (View v){

        AjudanteBD ajudanteBD = new AjudanteBD(this);

        boolean inserirsucesso = ajudanteBD.inserirDados(
                nomeplanta.getText().toString(),
                terreno.getText().toString(),
                Integer.parseInt(quantidade.getText().toString()),
                data.getText().toString());

        if (inserirsucesso == true) {


            Toast.makeText(this, "Atividade registada com sucesso!", Toast.LENGTH_SHORT).show();
            Intent menuP = new Intent(this, MainActivity.class);
            startActivity(menuP);

        }
         else {

            Toast.makeText(this, "Erro!", Toast.LENGTH_LONG).show();
        }


    }





}


