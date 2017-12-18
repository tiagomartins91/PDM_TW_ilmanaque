package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by TiagoMartins on 18/12/2017.
 */

public class Lembretes extends AppCompatActivity {


    ArrayList<String> lembretes_array = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lembretes);


        AjudanteBD ajudanteBD = new AjudanteBD(this);
        SQLiteDatabase db = ajudanteBD.getWritableDatabase();

        final ListView lembretes = (ListView) findViewById(R.id.listalembretes);


        Cursor queryres = ajudanteBD.getLembretes(); //query para obter todos os lembretes

        queryres.moveToFirst();

        while (!queryres.isAfterLast()) {

            lembretes_array.add(queryres.getString(0) + " - " + queryres.getString(1)); //id + nome do lembrete
            queryres.moveToNext();

        }

        queryres.close();
        db.close(); //fechar acesso à bd

        //criar a listview

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, lembretes_array);
        lembretes.setAdapter(adapter);



        lembretes.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView cb = (CheckedTextView) view;

                if (cb.isChecked()==false)
                    cb.setChecked(true);
                else
                    cb.setChecked(false);




            }
        });


    }




}
