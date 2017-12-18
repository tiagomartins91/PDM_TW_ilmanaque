package pdm.di.ubi.pt.tw_ilmanaque;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by TiagoMartins on 15/12/2017.
 */

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
        final Intent menuP = new Intent(this, MainActivity.class);
        int flag = 0;

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

                ajudanteBD.RegistarLembrete("Teste", "2018/01/02", 0, id_atividade);

                AlarmManager alarme = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

                Receiver receiver = new Receiver();
                IntentFilter filter = new IntentFilter("ALARM_ACTION");
                registerReceiver(receiver,filter);

                Intent intento = new Intent("ALARM_ACTION");
                PendingIntent operation = PendingIntent.getBroadcast(this,0,intento,0);
                alarme.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 9000, operation);

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

                                        boolean inserirsucesso = ajudanteBD.RegistarAtividade(
                                                nomeplanta.getText().toString(),
                                                terreno.getText().toString(),
                                                Integer.parseInt(quantidade.getText().toString()),
                                                data.getText().toString());

                                        int id_atividade = ajudanteBD.getIdAtividade(nomeplanta.getText().toString());


                                        ajudanteBD.RegistarLembrete(lembrete_descricao.getText().toString(), lembrete_data.getText().toString(), 0, id_atividade);


                                        if (inserirsucesso == true) {


                                            Toast.makeText(RegistoAtividades.this, "Atividade registada com sucesso!", Toast.LENGTH_SHORT).show();
                                            startActivity(menuP);

                                        } else {

                                            Toast.makeText(RegistoAtividades.this, "Erro!", Toast.LENGTH_LONG).show();
                                        }


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


    }



}


