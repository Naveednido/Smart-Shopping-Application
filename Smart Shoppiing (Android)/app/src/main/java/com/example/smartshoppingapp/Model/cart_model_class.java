package com.example.smartshoppingapp.Model;

public class cart_model_class {

    private String brand_name;
    private String id;
    private String image_url;
    private String name;
    private String price;
    private String description;

    public cart_model_class() {
    }

    public cart_model_class(String brand_name, String id, String image_url, String name, String price, String description) {
        this.brand_name = brand_name;
        this.id = id;
        this.image_url = image_url;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
