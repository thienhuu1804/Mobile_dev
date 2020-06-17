package com.example.get_nation_info;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CountrySimpleData implements Parcelable {
    String countryCode, name, population, areaInSqKm = "";


    public CountrySimpleData(String code, String name, String population, String areaInSqKm){
        this.countryCode = code;
        this.name = name;
        this.population = population;
        this.areaInSqKm = areaInSqKm;
    }

    protected CountrySimpleData(Parcel in) {
        countryCode = in.readString();
        name = in.readString();
        population = in.readString();
        areaInSqKm = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countryCode);
        dest.writeString(name);
        dest.writeString(population);
        dest.writeString(areaInSqKm);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CountrySimpleData> CREATOR = new Creator<CountrySimpleData>() {
        @Override
        public CountrySimpleData createFromParcel(Parcel in) {
            return new CountrySimpleData(in);
        }

        @Override
        public CountrySimpleData[] newArray(int size) {
            return new CountrySimpleData[size];
        }
    };

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }
}
