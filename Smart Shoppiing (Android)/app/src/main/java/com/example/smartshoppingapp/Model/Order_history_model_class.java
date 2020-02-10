package com.example.smartshoppingapp.Model;

public class Order_history_model_class {

    String idd , namee , image_url , quantity , date ,price;

    public Order_history_model_class(String idd, String namee, String image_url, String quantity, String date, String price) {
        this.idd = idd;
        this.namee = namee;
        this.image_url = image_url;
        this.quantity = quantity;
        this.date = date;
        this.price = price;
    }

    public Order_history_model_class() {
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public String getNamee() {
        return namee;
    }

    public void setNamee(String namee) {
        this.namee = namee;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
