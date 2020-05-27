package com.example.unipool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountType {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("value")
    @Expose
    private int value;

    public AccountType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public AccountType() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
