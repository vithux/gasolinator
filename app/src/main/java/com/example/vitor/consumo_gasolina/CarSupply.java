package com.example.vitor.consumo_gasolina;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vitor on 03/05/2016.
 */
public class CarSupply implements Parcelable {
    private String stationName;
    private double supplyPrice;
    private double kilometersRotated;
    private double litersSupplied;
    private Date supplyDate;
    private double supplyAverage;
    private Calendar calendar = Calendar.getInstance();

    public CarSupply(String stationName, double supplyPrice, double quilometrosRodados, double litersSupplied) {
        this.stationName = stationName;
        this.supplyPrice = supplyPrice;
        this.kilometersRotated = quilometrosRodados;
        this.litersSupplied = litersSupplied;
        this.supplyAverage = this.kilometersRotated / this.litersSupplied;
        this.supplyDate = this.calendar.getTime();
    }

    public Date getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(Date supplyDate) {
        this.supplyDate = supplyDate;
    }

    public double getSupplyAverage() {
        return supplyAverage;
    }

    public void setSupplyAverage(double supplyAverage) {
        this.supplyAverage = supplyAverage;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public double getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(double supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public double getKilometersRotated() {
        return kilometersRotated;
    }

    public void setKilometersRotated(double kilometersRotated) {
        this.kilometersRotated = kilometersRotated;
    }

    public double getLitersSupplied() {
        return litersSupplied;
    }

    public void setLitersSupplied(double litersSupplied) {
        this.litersSupplied = litersSupplied;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(stationName);
        out.writeDouble(supplyPrice);
        out.writeDouble(kilometersRotated);
        out.writeDouble(litersSupplied);
        out.writeDouble(supplyAverage);
        out.writeLong(supplyDate.getTime());
    }

    private CarSupply(Parcel in) {
        stationName = in.readString();
        supplyPrice = in.readDouble();
        kilometersRotated = in.readDouble();
        litersSupplied = in.readDouble();
        supplyAverage = in.readDouble();
        supplyDate = new Date(in.readLong());
    }


    public static final Parcelable.Creator<CarSupply> CREATOR = new Creator<CarSupply>() {

        public CarSupply createFromParcel(Parcel source) {
            return new CarSupply(source);
        }

        public CarSupply[] newArray(int size) {
            return new CarSupply[size];
        }
    };
}
