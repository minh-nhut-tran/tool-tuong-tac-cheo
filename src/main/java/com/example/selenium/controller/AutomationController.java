package com.example.selenium.controller;

import com.example.selenium.pojo.Account;
import com.example.selenium.service.automation.AutomationService;
import com.example.selenium.service.automation.IAutomationService;
import com.example.selenium.service.navigation.INavigationService;
import com.example.selenium.service.navigation.NavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AutomationController  implements Initializable {

    @FXML
    private Label balance;

    @FXML
    private Label userName;

    @FXML
    private TextField emotionFacebook;

    @FXML
    private TextField followFacebook;

    @FXML
    private TextField likePageFacebook;

    @FXML
    private TextField likePostFacebook;

    @FXML
    private MenuButton modeFacebook;

    @FXML
    private MenuItem modeFacebookAuto;

    @FXML
    private MenuItem modeFacebookCustomize;

    @FXML
    private MenuButton modeTiktok;

    @FXML
    private MenuButton modeYoutube;



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
    public void setMode(ActionEvent event) {
       String type  = ((MenuItem)event.getSource()).getUserData().toString().trim();
       switch (type){
           case "facebookAuto":
               this.modeFacebook.setText(this.modeFacebookAuto.getText());
               disableCustomizeTask(true);
               break;
           case "facebookCustomize":
               this.modeFacebook.setText(this.modeFacebookCustomize.getText());
               disableCustomizeTask(false);
               break;
           case "tiktok":
               break;
           default:

       }

    }
    private void disableCustomizeTask(boolean disable){
       this.emotionFacebook.setDisable(disable);
       this.followFacebook.setDisable(disable);
       this.likePageFacebook.setDisable(disable);
       this.likePostFacebook.setDisable(disable);
    }


    @FXML
    public void switchScreen(MouseEvent event) throws IOException {
        String screenName = ((Node)event.getSource()).getUserData().toString().trim();
        navigationService.router(screenName,this.accountData,(Stage)((Node)event.getSource()).getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigationService = new NavigationService();
        automationService = new AutomationService();
        this.modeFacebook.setText(this.modeFacebookAuto.getText());
        disableCustomizeTask(true);
    }


    @FXML
    public void run(ActionEvent event) throws InterruptedException {
        String type = ((Node)event.getSource()).getUserData().toString();
        switch (type){
            case "facebook":
                Map<String,Integer> tasks = new HashMap<>();
                tasks.put("likePostFacebook",Integer.valueOf(this.likePostFacebook.getText()));
                tasks.put("likePageFacebook",Integer.valueOf(this.likePageFacebook.getText()));
                tasks.put("followFacebook",Integer.valueOf(this.followFacebook.getText()));
                tasks.put("emotionFacebook",Integer.valueOf(this.emotionFacebook.getText()));
                automationService.run("facebook",this.accountData,tasks);
                break;
            case "youtube":
                break;
            case "tiktok":
                break;
        }

    }
}
