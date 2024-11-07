package com.example.selenium.model;

import lombok.Data;

@Data
public class Tiktok {
    private String tiktokID;
    private String name;
    private boolean status;


    public Tiktok() {
    }

    public Tiktok(String tiktokID, String name, boolean status) {
        this.tiktokID = tiktokID;
        this.name = name;
        this.status = status;
    }
}
