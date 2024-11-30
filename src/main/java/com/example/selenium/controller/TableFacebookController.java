package com.example.selenium.controller;

import com.example.selenium.service.account.AccountService;
import com.example.selenium.service.account.IAccountService;
import com.example.selenium.service.account_facebook.AccountFacebookService;
import com.example.selenium.service.account_facebook.IAccountFacebookService;
import com.example.selenium.service.navigation.INavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
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
    private MenuButton status;

    @FXML
    private Label type;

    private IAccountService accountService;

    private Stage stage;

    public void  setInformation(int no,String facebookID, String name,boolean status, String type){
        this.no.setText(String.valueOf(no+1));
        this.facebookID.setText(facebookID);
        this.name.setText(name);
        if(status){
            this.status.setText( "ACTIVE");
            this.status.setTextFill(Paint.valueOf("#18FA0C"));
        }else{
            this.status.setText( "INACTIVE");
            this.status.setTextFill(Paint.valueOf("#FF0033"));
        }
        this.type.setText(type);
    }

    @FXML
    public void deleteAccount(MouseEvent event) throws IOException {
        accountService.delete("facebook",facebookID.getText().trim(), (Stage)((Node)event.getSource()).getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountService = new AccountService();
    }

    @FXML
    public void setInActive() throws IOException {
        if(!this.status.getText().equals("INACTIVE")  && this.stage != null){
            accountService.setStatusAccount("facebook",facebookID.getText().trim(), "INACTIVE",this.stage);
        }
    }

    @FXML
    public void setActive() throws IOException {
        if(!this.status.getText().equals("ACTIVE") && this.stage != null){
            accountService.setStatusAccount("facebook",facebookID.getText().trim(), "ACTIVE",this.stage);
        }
    }

    @FXML
    public void getStage(MouseEvent event){
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    }


}
