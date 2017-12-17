package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

            atividades_array.add(queryres.getString(1)); //nome da atividade
            queryres.moveToNext();

        }

        queryres.close();
        db.close(); //fechar acesso à bd

        //criar a listview

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, atividades_array);
        atividades.setAdapter(adapter);


       /*

        pratos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String nome = (String) pratos.getItemAtPosition(position);


                Bundle bundle = new Bundle();
                bundle.putString("nome", nome);
                Intent showinfoprato = new Intent(getApplicationContext(), ConfirmRemoverPrato.class);
                showinfoprato.putExtras(bundle); // anexar extras ao intento para ir para a outra view
                startActivity(showinfoprato);
            }
        });

        */

    }



    public void abrirRegistoAtividades(View v){

        Intent abrirRegistoAtividades = new Intent(this, RegistoAtividades.class);

        startActivity(abrirRegistoAtividades);


    }


}