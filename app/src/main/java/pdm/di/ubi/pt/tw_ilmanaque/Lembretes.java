package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;


public class Lembretes extends AppCompatActivity {


    ArrayList<String> lembretes_array = new ArrayList<String>();
    private EditText mDateEntryField;

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

            lembretes_array.add(queryres.getString(0) + " - " + queryres.getString(1) + "   " + queryres.getString(2)); //id + nome do lembrete + data
            queryres.moveToNext();

        }

        queryres.close();
        db.close(); //fechar acesso à bd

        //criar a listview

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, lembretes_array);
        lembretes.setAdapter(adapter);
        final Intent menuLem = new Intent(this, Lembretes.class);


        lembretes.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                CheckedTextView cb = (CheckedTextView) view;

                if (cb.isChecked()==false)
                    cb.setChecked(true);


                else
                    cb.setChecked(false);

                if(cb.isChecked() == true){

                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Lembretes.this);
                   final AjudanteBD ajudanteBD = new AjudanteBD(Lembretes.this);
                     SQLiteDatabase db = ajudanteBD.getWritableDatabase();

                    a_builder.setMessage("Confirmar a resolução do lembrete:")
                            .setCancelable(false)
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {




                                        String linha = (String) lembretes.getItemAtPosition(position);
                                        String[] tokens = linha.split("-");
                                        String id_string = tokens[0].replace(" ","");
                                        int id = Integer.parseInt(id_string);
                                        ajudanteBD.EditarLembrete(id);
                                       startActivity(menuLem);

                                }
                            })
                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                    AlertDialog alert = a_builder.create();
                    alert.setTitle("AVISO");
                    alert.show();


                }


            }
        });


    }


    public void abrirNovoLembrete (View v){
        final Auxiliar aux = new Auxiliar();
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog_lembrete, null);
        mbuilder.setView(mView);

        final EditText lembrete_descricao = (EditText) mView.findViewById(R.id.et_inputdescricao);
        final EditText lembrete_data = (EditText) mView.findViewById(R.id.et_inputdata);
        final Intent menuLem = new Intent(this, Lembretes.class);

        final AjudanteBD ajudanteBD= new AjudanteBD(this);
        final SQLiteDatabase db = ajudanteBD.getWritableDatabase();
        final EditText setData;


        // set dialog message
        mbuilder
                .setCancelable(false)
                .setPositiveButton("Criar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                setData = (EditText) findViewById(R.id.);
                                mEditInit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showDialog(DATEINIT_DIALOG);
                                    }

                                });



                                 boolean lembretesucesso = ajudanteBD.RegistarLembrete(lembrete_descricao.getText().toString(),
                                         lembrete_data.getText().toString(), 0, -1); //-1 se não estiver associada a nenhuma atividade
                                         if(aux.verify(lembrete_data.getText().toString())== 1)
                                         {
                                             if (lembretesucesso == true) {


                                                 Toast.makeText(Lembretes.this, "Lembrete registado com sucesso!", Toast.LENGTH_SHORT).show();
                                                 db.close();
                                                 startActivity(menuLem);

                                             } else {

                                                 Toast.makeText(Lembretes.this, "Erro!", Toast.LENGTH_SHORT).show();
                                             }


                                         }
                                         else
                                             Toast.makeText(Lembretes.this, "Formato de data Errado(yyyy/mm/dd)", Toast.LENGTH_SHORT).show();

                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog dialog = mbuilder.create();
        dialog.show();




    }









}
