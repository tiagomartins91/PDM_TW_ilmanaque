package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class EditarAtividade extends AppCompatActivity {

    String id_atividade;
    EditText editar_nome, editar_terreno, editar_quant;
    TextView data;
    int id_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_atividades);

        //recebe o intento que vem da outra actividade
        Bundle bundle = getIntent().getExtras();
        id_atividade = bundle.getString("id");


        AjudanteBD ajudanteBD = new AjudanteBD(this); //ajudante da bd

        editar_nome = (EditText) findViewById(R.id.etEditarNomeAtividade);
        editar_terreno = (EditText) findViewById(R.id.etEditarTerreno);
        editar_quant = (EditText) findViewById(R.id.etEditarQuantidade);
        data = (TextView) findViewById(R.id.etEditarData);

        id_int = Integer.parseInt(id_atividade);


        Cursor queryres = ajudanteBD.showinfoAtividade(id_int);

        queryres.moveToFirst();

        System.out.println(queryres.toString());

        editar_nome.setText(queryres.getString(1));
        editar_terreno.setText(queryres.getString(2));
        editar_quant.setText(queryres.getString(3));
        data.setText(queryres.getString(4));


        queryres.close();




    }

    public void buttonEditar (View V){ //metodo do botão editar com verificação dos campos e dupla confirmação para alterar informação da atividade

        int flag=0;

        final AjudanteBD ajudanteBD = new AjudanteBD(this);
        final Intent menuAtiv = new Intent(this, Atividades.class);


        if (TextUtils.isEmpty(editar_nome.getText().toString())) {


            editar_nome.requestFocus();
            editar_nome.setError("Campo Obrigatório!");

        }
        else if (TextUtils.isEmpty(editar_terreno.getText().toString())) {

            editar_terreno.requestFocus();
            editar_terreno.setError("Campo Obrigatório");

        }
            else if (TextUtils.isEmpty(editar_quant.getText().toString())) {

            editar_quant.requestFocus();
            editar_quant.setError("Campo Obrigatório");

        }
        else
            flag = 1;


        if (flag==1) {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);

            a_builder.setMessage("Pretende mesmo fazer esta alteração?")
                    .setCancelable(false)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            boolean updatesucesso = ajudanteBD.EditarAtividade(id_int,
                                    editar_nome.getText().toString(),
                                    editar_terreno.getText().toString(),
                                    Integer.parseInt(editar_quant.getText().toString()),
                                            data.getText().toString());

                            if (updatesucesso == true) {

                                Toast.makeText(EditarAtividade.this, "Atividade editada com sucesso!", Toast.LENGTH_SHORT).show();
                                startActivity(menuAtiv);

                            }
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




}
