package com.example.selenium.model;


import lombok.Data;

@Data
public class Facebook {

    private String facebookID;
    private String name;
    private boolean status;
    private String type;

    public Facebook() {
    }

    public Facebook(String facebookID, String name, boolean status, String type) {
        this.facebookID = facebookID;
        this.name = name;

        this.status = status;
        this.type = type;
    }
}

