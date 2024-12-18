package com.example.selenium.controller;

import com.example.selenium.service.account.AccountService;
import com.example.selenium.service.account.IAccountService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TableTiktokController implements Initializable {
    @FXML
    private Label tiktokID;

    @FXML
    private Label name;

    @FXML
    private Label no;

    @FXML
    private MenuButton status;

    private IAccountService accountService;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountService = new AccountService();
    }

    public void  setInformation(int no,String tiktokID, String name,boolean status){
        this.no.setText(String.valueOf(no+1));
        this.tiktokID.setText(tiktokID);
        this.name.setText(name);
        if(status){
            this.status.setText( "ACTIVE");
            this.status.setTextFill(Paint.valueOf("#18FA0C"));
        }else {
            this.status.setText("INACTIVE");
            this.status.setTextFill(Paint.valueOf("#FF0033"));
        }
    }

    @FXML
    public void setInActive() throws IOException {
        if(!this.status.getText().equals("INACTIVE")  && this.stage != null){
            accountService.setStatusAccount("tiktok",tiktokID.getText().trim(), "INACTIVE",this.stage);
        }
    }

    @FXML
    public void setActive() throws IOException {
        if(!this.status.getText().equals("ACTIVE") && this.stage != null){
            accountService.setStatusAccount("tiktok",tiktokID.getText().trim(), "ACTIVE",this.stage);
        }
    }

    @FXML
    public void getStage(MouseEvent event){
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    }

    @FXML
    public void deleteAccount(MouseEvent event) throws IOException {
        accountService.delete("tiktok",tiktokID.getText().trim(), (Stage)((Node)event.getSource()).getScene().getWindow());
    }


}
