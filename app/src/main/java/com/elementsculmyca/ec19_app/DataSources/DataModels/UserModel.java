package com.elementsculmyca.ec19_app.DataSources.DataModels;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("_id")
    String _id;

    @SerializedName("name")
    String name;

    @SerializedName("college")
    String college;

    @SerializedName("email")
    String email;

    @SerializedName("phone")
    String phone;

    @SerializedName("__v")
    String __v;

    public UserModel(String name, String college, String email, String phone) {
        this.name = name;
        this.college = college;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
