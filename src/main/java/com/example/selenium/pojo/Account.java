package com.example.selenium.pojo;


import lombok.Data;

@Data
public class Account {
    private String accessToken;
    private String session;
    private int balance;
    private String user;
    private boolean isActive;
    private String currentState;
}
