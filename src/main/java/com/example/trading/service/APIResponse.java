package com.example.trading.service;
import com.example.trading.entities.Product;
import com.example.trading.entities.UserProfile;
import com.example.trading.entities.UserShop;


public class APIResponse {

    public int status;
    public int state;
    public String msg;
    public int userId;


    public APIResponse(){

    }

    public int setState(int state) {
        this.state = state;
        return this.state;
    }

    public int setStatus(int status){
        this.status = status;
        return this.status;
    }

    public String setMsg(String msg){
        this.msg = msg;
        return this.msg;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setData(UserProfile userProfile) {

    }

    public void setData(UserShop userShop) {

    }

    public void  setData(Product product){

    }

}
