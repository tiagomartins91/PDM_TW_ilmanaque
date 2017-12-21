package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
                final String id_string = tokens[0].replace(" ","");

                AlertDialog.Builder mbuilder = new AlertDialog.Builder(Atividades.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_diagog_atividades, null);
                mbuilder.setView(mView);

                final TextView nomeact = (TextView) mView.findViewById(R.id.tv_nomeact);
                final TextView terreno = (TextView) mView.findViewById(R.id.tv_terreno);
                final TextView quant = (TextView) mView.findViewById(R.id.tv_quantidade);
                final TextView data = (TextView) mView.findViewById(R.id.tv_data);
                final TextView sugestao = (TextView) mView.findViewById(R.id.tv_sugestao);

                final Intent menuEdit = new Intent(Atividades.this, EditarAtividade.class);

                final AjudanteBD ajudanteBD= new AjudanteBD(Atividades.this);
                final SQLiteDatabase db = ajudanteBD.getWritableDatabase();

                int id_int = Integer.parseInt(id_string);


                Cursor queryres = ajudanteBD.showinfoAtividade(id_int);

                queryres.moveToFirst();

                System.out.println(queryres.toString());

                nomeact.setText("Nome: " + queryres.getString(1));
                terreno.setText("Terreno: " + queryres.getString(2));
                quant.setText("Quantidade: " + queryres.getString(3));
                data.setText("Data: " + queryres.getString(4));
                sugestao.setText("Sugestão: " );


                queryres.close();


                // set dialog message
                mbuilder
                        .setCancelable(false)
                        .setPositiveButton("Editar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", id_string);
                                        Intent editaratividade = new Intent(getApplicationContext(), EditarAtividade.class);
                                        editaratividade.putExtras(bundle); // anexar extras ao intento para ir para a outra view
                                        startActivity(editaratividade);


                                    }
                                })
                        .setNegativeButton("Voltar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                final AlertDialog dialog = mbuilder.create();
                dialog.setTitle("Detalhes da Atividade");
                dialog.show();





            }
        });



    }



    public void abrirRegistoAtividades(View v){

        Intent abrirRegistoAtividades = new Intent(this, RegistoAtividades.class);

        startActivity(abrirRegistoAtividades);


    }


}