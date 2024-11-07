package com.example.selenium.model;

import lombok.Data;

@Data
public class Youtube {
    private String chanelID;
    private String name;
    private boolean status;

    public Youtube(){}

    public Youtube(String chanelID, String name, boolean status) {
        this.chanelID = chanelID;
        this.name = name;
        this.status = status;
    }
}
