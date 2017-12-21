package pdm.di.ubi.pt.tw_ilmanaque;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Informacoes extends AppCompatActivity {


    private ExpandableListView eListView;
    private ExpandableListAdapter oListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;
    AjudanteBD oDB = new AjudanteBD(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);


        eListView = (ExpandableListView) findViewById(R.id.expandableListView);
        putInitData();
        oListAdapter = new ExpandableListAdapter(this, listDataHeader, listHashMap);
        eListView.setAdapter(oListAdapter);

        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                listDataHeader.get(groupPosition);

                String stringTitle = oListAdapter.getChild(groupPosition, childPosition).toString();


                return true;

            }


        });

    }
        public void putInitData(){
            listDataHeader = new ArrayList<>();
            listHashMap = new HashMap<>();

            Cursor oCursor = oDB.getNameFromInformacoes();
            oCursor.moveToFirst();

            while(!oCursor.isAfterLast()){
                String nomePlanta = "Nome da Planta: " + oCursor.getString(0).toString();
                listDataHeader.add(nomePlanta);
                ArrayList<String> infos = new ArrayList<>();

                infos.add("Época de Cultivo: " + oCursor.getString(1).toString());
                infos.add("Pragas e Doenças: " + oCursor.getString(2).toString());

                listHashMap.put(nomePlanta,infos);

                oCursor.moveToNext();

            }





        }


    }



