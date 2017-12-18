package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class Atividades extends AppCompatActivity {


    ArrayList<String> atividades_array = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.atividades);


        AjudanteBD ajudanteBD= new AjudanteBD(this);
        SQLiteDatabase db = ajudanteBD.getWritableDatabase();

        final ListView atividades = (ListView) findViewById(R.id.listatividades);


        Cursor queryres = ajudanteBD.getAtividades(); //query para obter todas as atividades

        queryres.moveToFirst();

        while (!queryres.isAfterLast()){

            atividades_array.add(queryres.getString(0) + " - "  + queryres.getString(1)); //id + nome da atividade
            queryres.moveToNext();

        }

        queryres.close();
        db.close(); //fechar acesso à bd

        //criar a listview

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, atividades_array);
        atividades.setAdapter(adapter);




        atividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String linha = (String) atividades.getItemAtPosition(position);
                String[] tokens = linha.split("-");
                String id_string = tokens[0].replace(" ","");

                Bundle bundle = new Bundle();
                bundle.putString("id", id_string);
                Intent editaratividade = new Intent(getApplicationContext(), EditarAtividade.class);
                editaratividade.putExtras(bundle); // anexar extras ao intento para ir para a outra view
                startActivity(editaratividade);
            }
        });



    }



    public void abrirRegistoAtividades(View v){

        Intent abrirRegistoAtividades = new Intent(this, RegistoAtividades.class);

        startActivity(abrirRegistoAtividades);


    }


}