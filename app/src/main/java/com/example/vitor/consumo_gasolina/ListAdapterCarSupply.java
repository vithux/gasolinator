package com.example.vitor.consumo_gasolina;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Vitor on 03/05/2016.
 */
public class ListAdapterCarSupply extends ArrayAdapter<CarSupply> {

    private Context context;
    private ArrayList<CarSupply> list;


    public ListAdapterCarSupply(Context context, ArrayList<CarSupply> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CarSupply carSupplyPosition = this.list.get(position);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.supply_list, null);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //adiciona o valor no layout custom da listview
        TextView nomePosto = (TextView) convertView.findViewById(R.id.textViewNomePosto);
        nomePosto.setText(" " + carSupplyPosition.getStationName());

        TextView precoAbastecida = (TextView) convertView.findViewById(R.id.textViewPreco);
        precoAbastecida.setText(" R$" + String.format("%.2f", carSupplyPosition.getSupplyPrice()));

        TextView quilometrosRodados = (TextView) convertView.findViewById(R.id.textViewQuilometros);
        quilometrosRodados.setText(" " + Double.toString(carSupplyPosition.getKilometersRotated()) + "KM");

        TextView litrosAbastecidos = (TextView) convertView.findViewById(R.id.textViewLitros);
        litrosAbastecidos.setText(" " + Double.toString(carSupplyPosition.getLitersSupplied()) + "L");

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        TextView mediaAbastecida = (TextView) convertView.findViewById(R.id.textViewAvg);
        mediaAbastecida.setText(" " + new DecimalFormat("0.00", decimalFormatSymbols).format(carSupplyPosition.getSupplyAverage()) + "KM/L");

        TextView dataAbastecida = (TextView) convertView.findViewById(R.id.textViewDate);
        dataAbastecida.setText(" " + dateFormat.format(carSupplyPosition.getSupplyDate()));


        return convertView;
    }


}
