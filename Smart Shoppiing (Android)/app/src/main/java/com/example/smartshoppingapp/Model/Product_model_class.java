package com.example.smartshoppingapp.Model;

public class Product_model_class {

    private String image_url;
    private String price;
    private String product_name;
    private String product_id;
    private String product_Discription;
    private String brand_name;

    public Product_model_class() {
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public Product_model_class(String image_url, String price, String product_name, String product_id, String product_Discription, String brand_name) {
        this.image_url = image_url;
        this.price = price;
        this.product_name = product_name;
        this.product_id = product_id;
        this.product_Discription = product_Discription;
        this.brand_name = brand_name;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_Discription() {
        return product_Discription;
    }

    public void setProduct_Discription(String product_Discription) {
        this.product_Discription = product_Discription;
    }
}
