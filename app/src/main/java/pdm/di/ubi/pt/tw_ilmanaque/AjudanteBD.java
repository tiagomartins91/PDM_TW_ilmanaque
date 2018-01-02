package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AjudanteBD extends SQLiteOpenHelper {  // Base de Dados

    private static final int VERSAO_BASEDADOS = 1;
    private static final String NOME_BASEDADOS = "DBilmanaque";
    protected static final String NOME_TABELA1 = "Atividades";
    protected static final String NOME_TABELA2 = "Lembretes";
    protected static final String T1_COLUNA1 = "id";
    protected static final String T1_COLUNA2 = "nomeatividade";
    protected static final String T1_COLUNA3 = "terreno";
    protected static final String T1_COLUNA4 = "quantidade";
    protected static final String T1_COLUNA5 = "data";
    protected static final String T2_COLUNA1 = "id";
    protected static final String T2_COLUNA2 = "descricao";
    protected static final String T2_COLUNA3 = "data";       //data do lembrete
    protected static final String T2_COLUNA4 = "estado";      //0-por fazer, 1-feito
    protected static final String T2_COLUNA5 = "id_atividade";


    protected static final String TABELA_INFORMACAO = "Informacao";
    protected static final String T3_COLUNA1 = "plantas";
    protected static final String T3_COLUNA2 = "epoca";
    protected static final String T3_COLUNA3 = "pragas_doencas";



    private static final String CRIAR_TABELA_Atividades =
            "CREATE TABLE " + NOME_TABELA1 + "(" +
                    T1_COLUNA1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    T1_COLUNA2 + " VARCHAR(30) NOT NULL, " +
                    T1_COLUNA3 + " VARCHAR(30) NOT NULL, " +
                    T1_COLUNA4 + " INTEGER NOT NULL, " +
                    T1_COLUNA5 + " TEXT NOT NULL);";                  //data ano-mes-dia


    private static final String CRIAR_TABELA_Lembretes =
            "CREATE TABLE " + NOME_TABELA2 + "(" +
                    T2_COLUNA1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    T2_COLUNA2 + " TEXT NOT NULL, " +
                    T2_COLUNA3 + " TEXT NOT NULL, " +
                    T2_COLUNA4 + " INTEGER NOT NULL, " +
                    T2_COLUNA5 + " INTEGER REFERENCES " + NOME_TABELA1 + "( " + T1_COLUNA1 + "), " +
                    "FOREIGN KEY (" +  T2_COLUNA5  + ") REFERENCES " + NOME_TABELA1 + "(" + T1_COLUNA1 + "));";

    private static final String CRIAR_TABELA_Informacao =
            "CREATE TABLE " + TABELA_INFORMACAO + "(" +
                    T3_COLUNA1 + " TEXT PRIMARY KEY, " +
                    T3_COLUNA2 + " TEXT NOT NULL, " +
                    T3_COLUNA3 + " TEXT NOT NULL);";




    AjudanteBD(Context context) {

        super(context, NOME_BASEDADOS, null, VERSAO_BASEDADOS);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CRIAR_TABELA_Atividades);
        db.execSQL(CRIAR_TABELA_Lembretes);
        db.execSQL(CRIAR_TABELA_Informacao);
        insertDataIntoInformacao(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {


    }




    public boolean RegistarAtividade (String nomeatividade, String terreno, int quantidade, String data){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues novatividade = new ContentValues();
        novatividade.put(T1_COLUNA2, nomeatividade);
        novatividade.put(T1_COLUNA3, terreno);
        novatividade.put(T1_COLUNA4, quantidade);
        novatividade.put(T1_COLUNA5, data);





        long resultado = db.insert(NOME_TABELA1,null,novatividade);

        db.close();

        if (resultado==-1)
            return false;

        return true;


    }


    public boolean EditarAtividade(int id_atividade, String nomedaatividade, String terreno, int quant, String data){ //update dos campos da atividade


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put(T1_COLUNA2, nomedaatividade);
        newValues.put(T1_COLUNA3, terreno);
        newValues.put(T1_COLUNA4, quant);
        newValues.put(T1_COLUNA5, data);


        long resultado = db.update(NOME_TABELA1,newValues, T1_COLUNA1 +  " = " + "'"+id_atividade+"'" , null);


        db.close();


        if (resultado==-1)
            return false;


        return true;

    }


    public boolean EditarLembrete(int id_lemb){ //update dos campos da atividade


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put(T2_COLUNA4, 1);



        long resultado = db.update(NOME_TABELA2,newValues, T2_COLUNA1 +  " = " + "'"+id_lemb+"'" , null);


        db.close();


        if (resultado==-1)
            return false;


        return true;

    }




    public boolean RegistarLembrete (String descricao, String data, int estado, int id_atividade){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues novolembrete = new ContentValues();
        novolembrete.put(T2_COLUNA2, descricao);
        novolembrete.put(T2_COLUNA3, data);
        novolembrete.put(T2_COLUNA4, estado);
        novolembrete.put(T2_COLUNA5, id_atividade);

        long resultado = db.insert(NOME_TABELA2,null,novolembrete);

        db.close();

        if (resultado==-1)
            return false;

        return true;


    }

    public Cursor getAtividades (){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor queryres;

        queryres = db.rawQuery("select * "  + " from  " + NOME_TABELA1, null);


        return queryres;


    }




    public int getIdAtividade (String nomeplanta){

        Cursor queryres;

        SQLiteDatabase db = this.getWritableDatabase();

        queryres = db.rawQuery("select id "  + " from  " + NOME_TABELA1 + " where " + T1_COLUNA2 + " = " + "'"+nomeplanta+"'", null);

        queryres.moveToFirst();

        return queryres.getInt(0);
    }

    public Cursor showinfoAtividade(int id_atividade){ //ver dados de uma atividade


        Cursor queryres;

        SQLiteDatabase db = this.getWritableDatabase();

        queryres = db.rawQuery("select * "  + " from  " + NOME_TABELA1 + " where " + T1_COLUNA1 + " = " + "'"+id_atividade+"'", null);



        return queryres;


    }

    public Cursor getLembretes (){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor queryres;

        queryres = db.rawQuery("select * "  + " from  " + NOME_TABELA2 + " where " + T2_COLUNA4 + " = " + "'"+0+"'", null);


        return queryres;


    }

    public boolean getLembretesexists (String data, String nome){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor queryres;

        queryres = db.rawQuery("select * "  + " from  " + NOME_TABELA2 + " where " + T2_COLUNA4 + "=" + "'"+0+"'" +" and " +
                         T2_COLUNA3 + "=" + "'"+data+"'" + " and " + T2_COLUNA2 + "=" + "'" + nome + "'", null);

        if(queryres.getCount()>0)
            return true;
        return false;


    }


    public Cursor getNameFromInformacoes (){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor query;

        query = db.rawQuery("SELECT * " + " FROM " + TABELA_INFORMACAO, null);

        return query;
    }



    public void insertDataIntoInformacao (SQLiteDatabase db){

        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Abóbora', 'Março-Abril', 'Afídios, Cochonilhas')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Aipo', 'Março-Abril', 'Septoriose')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Alface', 'Abril-Junho', 'Pulgão, lagarta')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Alho', 'Primavera e Outono', 'Tripes, piolho,')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Alho francês', 'Março-Maio', 'Sclerotinia, míldio')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Batata', 'Fevereiro-Abril', 'Afídio, escaravelho')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Beringela', 'Fevereiro-Abril', 'ácaro-vermelho, vaquinha')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Beterraba', 'Maio-Junho', 'Afídios,Lagartas')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Beterraba branca (acelga)', 'Abril-Maio', 'Afídios, Lagartas')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Cebola', 'Março Abril', 'ácaros, afídios')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Cebolinho', 'Março-Abril', 'lagarta-rosca, cigarrinhas')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Cenoura', 'Maio-Junho', 'pulgão, lagarta')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Coentro', 'Março-Junho', 'Pulgão, tripes')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Couves', 'Setembro-Dezembro', 'Pulgão, tripes')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Ervilha', 'Outubro-Novembro', 'ácaros, afídeos')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Espinafre', 'Janeiro-Abril Setembro-Outubro', 'Afídeos, mosca')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Feijão', 'Depende da Variedade', 'Cigarrinha, lagarta')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Feijão verde', 'Abril-Junho', 'afídeos, alfinete')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Morango', 'Novembro-Janeiro', 'Pulgão, ácaro')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Nabo', 'Abril-Setembro', 'áltica, mosca')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Pepino', 'Abril-Junho', 'Ácaros, afídeos')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Pimento', 'Março-Maio', 'Lagarta, mosca')");
        db.execSQL("INSERT into " + TABELA_INFORMACAO + " ("+ T3_COLUNA1 +", " + T3_COLUNA2 + ", " + T3_COLUNA3 + ") " + "values ('Rabanete', 'Março-Abril', 'áltica, a mosca da couve')");


    }





}
