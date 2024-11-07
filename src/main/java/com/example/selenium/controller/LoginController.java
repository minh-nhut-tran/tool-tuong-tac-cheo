package com.example.selenium.controller;

import com.example.selenium.pojo.Account;
import com.example.selenium.service.login.ILoginService;
import com.example.selenium.service.navigation.INavigationService;
import com.example.selenium.service.login.LoginService;
import com.example.selenium.service.navigation.NavigationService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private PasswordField accessToken;
    private  ILoginService loginService;
    private INavigationService navigationService;

    @FXML
    public void login(MouseEvent event) throws IOException {
       Account account =  loginService.loginByAccessToken(accessToken.getText().trim());
       if(account.isActive()){
           navigationService.router("home",account,event);
       }else{
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setHeaderText("access token is invalid!");
           alert.show();
       }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginService = new LoginService();
        navigationService = new NavigationService();
    }



}
