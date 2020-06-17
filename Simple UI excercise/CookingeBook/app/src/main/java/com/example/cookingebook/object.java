package com.example.cookingebook;

public class object {
    private String nameCooking;
    private String url;
    private int imageProduct;

    public object () {
    }

    public object(String nameCooking, String url) {
        this.nameCooking = nameCooking;
        this.url = url;
    }

    public object (String nameCooking, String url, int imageProduct) {
        this.nameCooking = nameCooking;
        this.imageProduct = imageProduct;
        this.url = url;
    }

    public String getNameCooking() {
        return nameCooking;
    }

    public void setNameCooking(String nameCooking) {
        this.nameCooking = nameCooking;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(int imageProduct) {
        this.imageProduct = imageProduct;
    }
}
