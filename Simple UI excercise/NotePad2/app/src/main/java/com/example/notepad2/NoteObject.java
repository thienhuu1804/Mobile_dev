package com.example.notepad2;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteObject implements Parcelable {

    private String title;
    private String contents;
    private String date;
    private String time;

    NoteObject(){
        time = title = contents = date = "";
    }

    NoteObject(String title, String contents, String date, String time){
        this.title = title;
        this.contents = contents;
        this.date = date;
        this.time = time;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    protected NoteObject(Parcel in) {
        title = in.readString();
        contents = in.readString();
        date = in.readString();
        time = in.readString();
    }

    public static final Creator<NoteObject> CREATOR = new Creator<NoteObject>() {
        @Override
        public NoteObject createFromParcel(Parcel in) {
            return new NoteObject(in);
        }

        @Override
        public NoteObject[] newArray(int size) {
            return new NoteObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(contents);
        parcel.writeString(date);
        parcel.writeString(time);
    }
}
