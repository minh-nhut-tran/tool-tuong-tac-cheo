package com.example.selenium.controller;

import com.example.selenium.pojo.Account;
import com.example.selenium.service.ILoginService;
import com.example.selenium.service.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class LoginController {

    @FXML
    private PasswordField accessToken;

    private final ILoginService loginService;

    public LoginController(){
        loginService = new LoginService();
    }

    @FXML
    public void login(){
       Account account =  loginService.loginByAccessToken(accessToken.getText().trim());
       if(account.isActive()){
           System.out.println("jsjdjjsdj");
       }
    }



}
