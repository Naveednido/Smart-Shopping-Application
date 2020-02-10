package com.example.smartshoppingapp.Model;

public class Users {


    private String f_name , l_name , phone_num;

    public Users() {
    }

    public Users(String f_name, String l_name, String phone_num) {
        this.f_name = f_name;
        this.l_name = l_name;
        this.phone_num = phone_num;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }
}
