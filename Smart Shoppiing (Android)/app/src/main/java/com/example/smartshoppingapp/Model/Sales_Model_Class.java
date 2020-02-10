package com.example.smartshoppingapp.Model;

public class Sales_Model_Class {

    String brandtype , image, startdate, enddate, saletype, percent_off , brandcategory , brandname;

    public Sales_Model_Class() {
    }

    public String getBrandtype() {
        return brandtype;
    }

    public void setBrandtype(String brandtype) {
        this.brandtype = brandtype;
    }

    public Sales_Model_Class(String brandtype, String image, String startdate, String enddate, String saletype, String percent_off, String brandcategory, String brandname) {
        this.brandtype = brandtype;
        this.image = image;
        this.startdate = startdate;
        this.enddate = enddate;
        this.saletype = saletype;
        this.percent_off = percent_off;
        this.brandcategory = brandcategory;
        this.brandname = brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getBrandname() {
        return brandname;
    }

    public String getBrandcategory() {
        return brandcategory;
    }

    public void setBrandcategory(String brandcategory) {
        this.brandcategory = brandcategory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getSaletype() {
        return saletype;
    }

    public void setSaletype(String saletype) {
        this.saletype = saletype;
    }

    public String getPercent_off() {
        return percent_off;
    }

    public void setPercent_off(String percent_off) {
        this.percent_off = percent_off;
    }
}

