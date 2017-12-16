package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.w3c.dom.Text;

/**
 * Created by TiagoMartins on 15/12/2017.
 */

public class AjudanteBD extends SQLiteOpenHelper {

    private static final int VERSAO_BASEDADOS = 1;
    private static final String NOME_BASEDADOS = "DBilmanaque";
    protected static final String NOME_TABELA1 = "Atividades";
    protected static final String T1_COLUNA1 = "id";
    protected static final String T1_COLUNA2 = "planta";
    protected static final String T1_COLUNA3 = "terreno";
    protected static final String T1_COLUNA4 = "quantidade";
    protected static final String T1_COLUNA5 = "data";



    private static final String CRIAR_TABELA_Atividades =
            "CREATE TABLE " + NOME_TABELA1 + "(" +
                    T1_COLUNA1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    T1_COLUNA2 + " VARCHAR(30) NOT NULL, " +
                    T1_COLUNA3 + " VARCHAR(30) NOT NULL, " +
                    T1_COLUNA4 + " INTEGER NOT NULL, " +
                    T1_COLUNA5 + " TEXT NOT NULL);";                  //data ano-mes-dia


    AjudanteBD(Context context) {

        super(context, NOME_BASEDADOS, null, VERSAO_BASEDADOS);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CRIAR_TABELA_Atividades);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {


    }


    public boolean inserirDados (String nomeplanta, String terreno, int quantidade, String data){ //inserir prato na tabela

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues novatividade = new ContentValues();
        novatividade.put(T1_COLUNA2, nomeplanta);
        novatividade.put(T1_COLUNA3, terreno);
        novatividade.put(T1_COLUNA4, quantidade);
        novatividade.put(T1_COLUNA5, data);



        long resultado = db.insert(NOME_TABELA1,null,novatividade);

        db.close();

        if (resultado==-1)
            return false;

        return true;


    }






}
