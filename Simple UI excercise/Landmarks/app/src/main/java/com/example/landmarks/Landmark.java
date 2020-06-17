package com.example.landmarks;

import android.print.PrinterId;

public class Landmark {
    private String name;
    private String local;
    private String url;

    public Landmark(){
    }

    public Landmark(String name, String local, String url) {
        this.name = name;
        this.local = local;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
