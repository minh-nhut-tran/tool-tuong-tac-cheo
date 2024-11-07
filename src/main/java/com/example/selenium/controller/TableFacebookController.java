package com.example.selenium.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TableFacebookController{
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

    public void  setInformation(int no,String facebookID, String name,boolean status, String type){
        this.no.setText(String.valueOf(no));
        this.facebookID.setText(facebookID);
        this.name.setText(name);
        this.status.setText(status ? "ACTIVE" : "INACTIVE");
        this.type.setText(type);
    }


}
