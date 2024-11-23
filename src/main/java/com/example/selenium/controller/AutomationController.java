package com.example.selenium.controller;

import com.example.selenium.pojo.Account;
import com.example.selenium.service.automation.AutomationService;
import com.example.selenium.service.automation.IAutomationService;
import com.example.selenium.service.navigation.INavigationService;
import com.example.selenium.service.navigation.NavigationService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AutomationController  implements Initializable {

    @FXML
    private Label balance;

    @FXML
    private Label userName;


    private INavigationService navigationService;

    private IAutomationService automationService;

    private Account accountData;



    public void setAccountData(Account account) {
        this.accountData = account;
        if(this.accountData != null){
            this.balance.setText(String.valueOf(accountData.getBalance()));
            this.userName.setText(accountData.getUser());
        }
    }

    @FXML
    public void switchScreen(MouseEvent event) throws IOException {
        String screenName = ((Node)event.getSource()).getUserData().toString().trim();
        navigationService.router(screenName,this.accountData,event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        navigationService = new NavigationService();
        automationService = new AutomationService();
    }


    @FXML
    public void run() throws InterruptedException {
        automationService.run("facebook",this.accountData);
    }
}
