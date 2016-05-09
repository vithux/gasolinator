package com.example.vitor.consumo_gasolina;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class SupplyRegister extends AppCompatActivity {

    private Intent resultIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply__register);
    }


    public void onClickCadastrar(View v) {
        EditText editPosto = (EditText) findViewById(R.id.textPosto);
        EditText editPreco = (EditText) findViewById(R.id.textPreco);
        EditText editQuilometros = (EditText) findViewById(R.id.textQuilometros);
        EditText editLitros = (EditText) findViewById(R.id.textLitros);

        if (TextUtils.isEmpty(editPosto.getText())) { //valida se o campo posto está vazio
            editPosto.setError(getResources().getString(R.string.campo_vazio));
        } else if (TextUtils.isEmpty(editPreco.getText())) {//valida se o campo preço está vazio
            editPreco.setError(getResources().getString(R.string.campo_vazio));
        } else if (TextUtils.isEmpty(editQuilometros.getText())) {//valida se o campo quilometros está vazio
            editQuilometros.setError(getResources().getString(R.string.campo_vazio));
        } else if (TextUtils.isEmpty(editLitros.getText())) {//valida se o campo litros está vazio
            editLitros.setError(getResources().getString(R.string.campo_vazio));
        } else {
            resultIntent.putExtra("RETORNO_POSTO", editPosto.getText().toString());
            resultIntent.putExtra("RETORNO_PRECO", Double.parseDouble(editPreco.getText().toString()));
            resultIntent.putExtra("RETORNO_QUILOMETROS", Double.parseDouble(editQuilometros.getText().toString()));
            resultIntent.putExtra("RETORNO_LITROS", Double.parseDouble(editLitros.getText().toString()));
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    public void onClickCancelar(View v) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
