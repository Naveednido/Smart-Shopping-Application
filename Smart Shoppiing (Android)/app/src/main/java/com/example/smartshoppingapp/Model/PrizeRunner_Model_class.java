package com.example.smartshoppingapp.Model;

public class PrizeRunner_Model_class {

    String image_url , price , name ;


    public PrizeRunner_Model_class(String image_url, String price, String name) {
        this.image_url = image_url;
        this.price = price;
        this.name = name;
    }

    public PrizeRunner_Model_class() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
