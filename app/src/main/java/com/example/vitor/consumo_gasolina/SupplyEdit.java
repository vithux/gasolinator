package com.example.vitor.consumo_gasolina;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Vitor on 07/05/2016.
 */
public class SupplyEdit extends AppCompatActivity {

    public static int RESULT_EDIT = 2;
    private Intent resultIntent = new Intent();
    private EditText editPosto;
    private EditText editPreco;
    private EditText editQuilometros;
    private EditText editLitros;
    private Date cData;

    private ArrayList<CarSupply> editedListObjects = new ArrayList<>();
    private Integer position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply__register);

        editPosto = (EditText) findViewById(R.id.textPosto);
        editPreco = (EditText) findViewById(R.id.textPreco);
        editQuilometros = (EditText) findViewById(R.id.textQuilometros);
        editLitros = (EditText) findViewById(R.id.textLitros);


        Bundle data = getIntent().getExtras();
        if (data != null) {
            editPosto.setText(data.getString("EDIT_POSTO"));
            editPreco.setText(Double.toString(data.getDouble("EDIT_PRECO", 0)));
            editQuilometros.setText(Double.toString(data.getDouble("EDIT_QUILOMETROS", 0)));
            editLitros.setText(Double.toString(data.getDouble("EDIT_LITROS", 0)));
            cData = new Date(data.getLong("EDIT_DATA"));
            position = data.getInt("EDIT_POSITION");
            editedListObjects = data.getParcelableArrayList("EDIT_LISTA");
        }


        Button botaoOk = (Button) findViewById(R.id.botao_ok);
        botaoOk.setText("EDITAR");
    }


    public void onClickCadastrar(View v) {
        if (TextUtils.isEmpty(editPosto.getText())) { //valida se o campo posto está vazio
            editPosto.setError(getResources().getString(R.string.campo_vazio));
        } else if (TextUtils.isEmpty(editPreco.getText())) {//valida se o campo preço está vazio
            editPreco.setError(getResources().getString(R.string.campo_vazio));
        } else if (TextUtils.isEmpty(editQuilometros.getText())) {//valida se o campo quilometros está vazio
            editQuilometros.setError(getResources().getString(R.string.campo_vazio));
        } else if (TextUtils.isEmpty(editLitros.getText())) {//valida se o campo litros está vazio
            editLitros.setError(getResources().getString(R.string.campo_vazio));
        } else {
            editedListObjects.get(position).setStationName(editPosto.getText().toString());
            editedListObjects.get(position).setSupplyPrice(Double.parseDouble(editPreco.getText().toString()));
            editedListObjects.get(position).setKilometersRotated(Double.parseDouble(editQuilometros.getText().toString()));
            editedListObjects.get(position).setLitersSupplied(Double.parseDouble(editLitros.getText().toString()));
            editedListObjects.get(position).setSupplyDate(cData);
            editedListObjects.get(position).setSupplyAverage(editedListObjects.get(position).getKilometersRotated() / editedListObjects.get(position).getLitersSupplied());

            resultIntent.putExtra("LIST_EDITED", editedListObjects);
            setResult(RESULT_EDIT, resultIntent);
            finish();
        }


    }

    public void onClickCancelar(View v) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
