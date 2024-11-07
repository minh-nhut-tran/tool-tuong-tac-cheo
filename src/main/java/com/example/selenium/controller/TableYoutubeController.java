package com.example.selenium.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TableYoutubeController {

    @FXML
    private Label chanelID;

    @FXML
    private Label name;

    @FXML
    private Label no;

    @FXML
    private Label status;

    public void  setInformation(int no,String chanelID, String name,boolean status){
        this.no.setText(String.valueOf(no));
        this.chanelID.setText(chanelID);
        this.name.setText(name);
        this.status.setText(status ? "ACTIVE" : "INACTIVE");
    }

}
