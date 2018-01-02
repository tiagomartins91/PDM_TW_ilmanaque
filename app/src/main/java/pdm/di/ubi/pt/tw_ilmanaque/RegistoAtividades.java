package pdm.di.ubi.pt.tw_ilmanaque;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;




public class RegistoAtividades extends AppCompatActivity {



    EditText nomeplanta, terreno, quantidade, data;
    Button registar;
    CheckBox lembrete_automatico, lembrete_manual;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registoatividades);

        nomeplanta = (EditText) findViewById(R.id.etNomePlanta);
        terreno = (EditText) findViewById(R.id.etTerreno);
        quantidade= (EditText) findViewById(R.id.etQuantidade);
        data = (EditText) findViewById(R.id.etData);
        registar = (Button) findViewById(R.id.bRegistar);
        lembrete_automatico = (CheckBox) findViewById(R.id.cbAut);
        lembrete_manual = (CheckBox) findViewById(R.id.cbManual);
    }

    public void checkButtonAutomatico(View V){

        lembrete_manual.setChecked(false);


    }

    public void checkButtonManual(View V){

        lembrete_automatico.setChecked(false);


    }



    public void RegistarAtividade (View v){

        final AjudanteBD ajudanteBD = new AjudanteBD(this);
        final Intent menuP = new Intent(this, Atividades.class);
        int flag = 0;
        final Auxiliar aux = new Auxiliar();

        if (flag == 0) { // verificação se as caixas estão vazias

            if (TextUtils.isEmpty(nomeplanta.getText().toString())) {

                nomeplanta.requestFocus();
                nomeplanta.setError("Campo Obrigatório");

            }
            else if (TextUtils.isEmpty(terreno.getText().toString())) {


                terreno.requestFocus();
                terreno.setError("Campo Obrigatório");

            }
            else if (TextUtils.isEmpty(quantidade.getText().toString())) {

                quantidade.requestFocus();
                quantidade.setError("Campo Obrigatório");

            }
            else if (TextUtils.isEmpty(data.getText().toString())) {

                data.requestFocus();
                data.setError("Campo Obrigatório");


            }
            else if(aux.verify(data.getText().toString())== 0)
            {
                data.setError("Formato Errado yyyy/mm/dd");

            }
            else
                flag = 1;


        }


        if (flag == 1) {


            if (lembrete_automatico.isChecked()) {

                boolean inserirsucesso = ajudanteBD.RegistarAtividade(
                        nomeplanta.getText().toString(),
                        terreno.getText().toString(),
                        Integer.parseInt(quantidade.getText().toString()),
                        data.getText().toString());


                int id_atividade = ajudanteBD.getIdAtividade(nomeplanta.getText().toString());



                long ml = System.currentTimeMillis() + (86400000*3);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");


                //Converter data e tempo em milisegundos
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(ml);
               formatter.format(calendar.getTime());
                ajudanteBD.RegistarLembrete("Lembrete " + nomeplanta.getText().toString(), formatter.format(calendar.getTime()), 0, id_atividade);
                if (inserirsucesso == true) {


                    Toast.makeText(RegistoAtividades.this, "Atividade registada com sucesso!", Toast.LENGTH_SHORT).show();
                    startActivity(menuP);

                } else {

                    Toast.makeText(RegistoAtividades.this, "Erro!", Toast.LENGTH_LONG).show();
                }


            }


            if (lembrete_manual.isChecked()) {


                AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog_lembrete, null);
                mbuilder.setView(mView);

                final EditText lembrete_descricao = (EditText) mView.findViewById(R.id.et_inputdescricao);
                final EditText lembrete_data = (EditText) mView.findViewById(R.id.et_inputdata);


                // set dialog message
                mbuilder
                        .setCancelable(false)
                        .setPositiveButton("Registar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {




                                    }
                                })
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                final AlertDialog dialog = mbuilder.create();
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean naofechardiaglog = false;

                        if(aux.verify(lembrete_data.getText().toString())== 1) {

                            boolean inserirsucesso = ajudanteBD.RegistarAtividade(
                                    nomeplanta.getText().toString(),
                                    terreno.getText().toString(),
                                    Integer.parseInt(quantidade.getText().toString()),
                                    data.getText().toString());


                            int id_atividade = ajudanteBD.getIdAtividade(nomeplanta.getText().toString());

                            boolean lembretesucesso = ajudanteBD.RegistarLembrete(lembrete_descricao.getText().toString(),
                                    lembrete_data.getText().toString(), 0,id_atividade); //-1 se não estiver associada a nenhuma atividade

                            naofechardiaglog = true;
                            if (lembretesucesso == true) {


                                Toast.makeText(RegistoAtividades.this, "Atividade registada com sucesso!", Toast.LENGTH_SHORT).show();
                                startActivity(menuP);

                            } else {

                                Toast.makeText(RegistoAtividades.this, "Erro!", Toast.LENGTH_SHORT).show();
                            }


                        }
                        else {

                            lembrete_data.setError("Formato de data errado - yyyy/mm/dd");

                        }

                        if(naofechardiaglog)
                            dialog.dismiss();

                    }
                });




            }


        }


    }



}


