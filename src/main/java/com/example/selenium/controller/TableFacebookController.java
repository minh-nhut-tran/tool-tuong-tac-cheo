package com.example.selenium.controller;

import com.example.selenium.service.account.AccountService;
import com.example.selenium.service.account.IAccountService;
import com.example.selenium.service.account_facebook.AccountFacebookService;
import com.example.selenium.service.account_facebook.IAccountFacebookService;
import com.example.selenium.service.navigation.INavigationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TableFacebookController implements Initializable {
    @FXML
    private Label facebookID;

    @FXML
    private Label name;

    @FXML
    private Label no;

    @FXML
    private Label status;

    @FXML
    private Label type;

    private IAccountService accountService;


    public void  setInformation(int no,String facebookID, String name,boolean status, String type){
        this.no.setText(String.valueOf(no+1));
        this.facebookID.setText(facebookID);
        this.name.setText(name);
        this.status.setText(status ? "ACTIVE" : "INACTIVE");
        this.type.setText(type);
    }

    @FXML
    public void deleteAccount(MouseEvent event) throws IOException {
        accountService.delete("facebook",facebookID.getText().trim(), event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountService = new AccountService();
    }
}
