package com.example.vitor.consumo_gasolina;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dacer.androidcharts.LineView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    public static int RESULT_EDIT = 2;

    private ArrayList<CarSupply> listObjects = new ArrayList<>();
    private ArrayList<CarSupply> listClone = new ArrayList<>();
    private ListView listView;
    private ListAdapterCarSupply listAdapterCarSupply;
    private LineView lineView;
    private ArrayList<String> dataList = new ArrayList<>();
    private ArrayList<Integer> pointsList = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> linesList = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM");
    private ArrayList<CarSupply> backupListObjects = new ArrayList<>();
    private AlertDialog alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listAdapterCarSupply = new ListAdapterCarSupply(this, listClone);
        listView = (ListView) findViewById(R.id.listaAbastecida);
        listView.setAdapter(listAdapterCarSupply);
        lineView = (LineView) findViewById(R.id.line_view);


        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("SAVED_LIST", "");
        Type type = new TypeToken<ArrayList<CarSupply>>() {
        }.getType();
        listObjects = gson.fromJson(json, type);
        if (listObjects.size() != 0) {
            listClone.clear();
            for (int x = listObjects.size(); x > 0; x--) {
                CarSupply obj = listObjects.get(x - 1);
                listClone.add(obj);
            }
            atualizaGrafico(listObjects);
            listAdapterCarSupply.notifyDataSetChanged();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                //faz backup dos objetos
                backupListObjects.clear();
                backupListObjects = new ArrayList<CarSupply>(listObjects);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Atenção!");
                builder.setMessage("O que deseja fazer com esse registro?");
                //define um botão como positivo
                builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //logica editar aqui
                        Intent editIntent = new Intent(MainActivity.this, SupplyEdit.class);
                        editIntent.putExtra("EDIT_POSTO", listClone.get(position).getStationName());
                        editIntent.putExtra("EDIT_PRECO", listClone.get(position).getSupplyPrice());
                        editIntent.putExtra("EDIT_QUILOMETROS", listClone.get(position).getKilometersRotated());
                        editIntent.putExtra("EDIT_LITROS", listClone.get(position).getLitersSupplied());
                        editIntent.putExtra("EDIT_DATA", listClone.get(position).getSupplyDate().getTime());
                        editIntent.putExtra("EDIT_LISTA", listClone);
                        editIntent.putExtra("EDIT_POSITION", position);
                        startActivityForResult(editIntent, 1);
                    }
                });


                //define um botão como negativo.
                builder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (listObjects.size() == 1) {
                            listObjects.clear();
                            lineView.setVisibility(View.INVISIBLE);
                            listAdapterCarSupply.clear();
                        } else {
                            Collections.reverse(listObjects);
                            listObjects.remove(position);
                            Collections.reverse(listObjects);
                            listClone.clear();
                            for (int x = listObjects.size(); x > 0; x--) {
                                CarSupply obj = listObjects.get(x - 1);
                                listClone.add(obj);
                            }
                            atualizaGrafico(listObjects);
                            listAdapterCarSupply.notifyDataSetChanged();
                        }

                        Snackbar snackbar = Snackbar
                                .make(findViewById(R.id.my_cord), "Mensagem Apagada!", Snackbar.LENGTH_LONG)
                                .setAction("DESFAZER", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Snackbar snackbar1 = Snackbar.make(findViewById(R.id.my_cord), "Mensagem Restaurada!", Snackbar.LENGTH_SHORT);
                                        snackbar1.show();
                                        listClone.clear();
                                        for (int x = backupListObjects.size(); x > 0; x--) {
                                            CarSupply obj = backupListObjects.get(x - 1);
                                            listClone.add(obj);
                                        }
                                        atualizaGrafico(backupListObjects);
                                        listObjects.clear();
                                        listObjects = new ArrayList<CarSupply>(backupListObjects);
                                        listAdapterCarSupply.notifyDataSetChanged();
                                    }
                                });
                        snackbar.show();
                    }
                });
                alerta = builder.create();
                alerta.show();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            CarSupply d = new CarSupply(data.getStringExtra("RETORNO_POSTO"), data.getDoubleExtra("RETORNO_PRECO", 0), data.getDoubleExtra("RETORNO_QUILOMETROS", 0), data.getDoubleExtra("RETORNO_LITROS", 0));
            adicionaElementoListaAbastecida(d);
            listClone.clear();
            for (int x = listObjects.size(); x > 0; x--) {
                CarSupply obj = listObjects.get(x - 1);
                listClone.add(obj);
            }
            atualizaGrafico(listObjects);
            listAdapterCarSupply.notifyDataSetChanged();
        } else if (resultCode == RESULT_EDIT) {

            listObjects.clear();
            listObjects = data.getParcelableArrayListExtra("LIST_EDITED");
            Collections.reverse(listObjects);
            listClone.clear();
            for (int x = listObjects.size(); x > 0; x--) {
                CarSupply obj = listObjects.get(x - 1);
                listClone.add(obj);
            }
            atualizaGrafico(listObjects);
            listAdapterCarSupply.notifyDataSetChanged();
        }
    }


    public void onClickIncluir(View v) {
        startActivityForResult(new Intent(this, SupplyRegister.class), 1);
    }

    public void adicionaElementoListaAbastecida(CarSupply elemento) {
        listObjects.add(elemento);
    }


    private void atualizaGrafico(ArrayList<CarSupply> lLista) {
        lineView.setVisibility(View.VISIBLE);
        dataList.clear();
        pointsList.clear();
        for (CarSupply obj : lLista) {
            dataList.add(dateFormat.format(obj.getSupplyDate()));
            pointsList.add((int) obj.getSupplyAverage());
        }

        linesList.clear();
        linesList.add(pointsList);
        lineView.setBottomTextList(dataList);
        lineView.setDataList(linesList);
    }


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listObjects);
        prefsEditor.putString("SAVED_LIST", json);
        prefsEditor.commit();
    }

}

