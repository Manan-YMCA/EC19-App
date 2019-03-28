package com.elementsculmyca.ec19_app.DataSources.DataModels;

import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserLocalModel;
import com.google.gson.annotations.SerializedName;

public class ResponseModel {

    @SerializedName("status")
    String status;

    @SerializedName("qrcode")
    String qrcode;

    @SerializedName("data")
    UserModel user;

    public ResponseModel(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
