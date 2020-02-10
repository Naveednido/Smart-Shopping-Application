package com.example.smartshoppingapp.Model;

public class Brands_model_class {

    public String image_url;
    public String address;
    public String phone;
    public String brand;
    public String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Brands_model_class()
    {

    }

    public Brands_model_class(String image_url, String address, String phone , String brand , String category) {
        this.image_url = image_url;
        this.address = address;
        this.phone = phone;
        this.brand = brand;
        this.category = category;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
