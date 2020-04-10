package com.example.myapplication98;

import android.os.Parcel;
import android.os.Parcelable;

public class Poin implements Parcelable {


    private int id;
    private String nama_user, totalpoin;

    public Poin(int id, String nama_user, String totalpoin) {
        this.id = id;

        this.totalpoin = totalpoin;
        this.nama_user = nama_user;

    }

    protected Poin(Parcel in) {
        id = in.readInt();
        nama_user = in.readString();
        totalpoin = in.readString();
    }

    public static final Creator<Poin> CREATOR = new Creator<Poin>() {
        @Override
        public Poin createFromParcel(Parcel in) {
            return new Poin(in);
        }

        @Override
        public Poin[] newArray(int size) {
            return new Poin[size];
        }
    };

    public String getNama_user() {
        return nama_user;
    }
    public String getTotalpoin() { return totalpoin; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama_user);
        dest.writeString(totalpoin);
    }
}
