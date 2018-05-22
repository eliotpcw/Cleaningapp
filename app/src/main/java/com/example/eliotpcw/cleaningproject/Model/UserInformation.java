package com.example.eliotpcw.cleaningproject.Model;

/**
 * Created by eliotpcw on 10.04.2018.
 */

public class UserInformation {
    private String name;
    private String phoneNumber;

    public UserInformation(){}
    public UserInformation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
