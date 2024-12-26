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
import java.sql.SQLOutput;
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
    private TextField followTiktok;

    @FXML
    private TextField loveTiktok;

    @FXML
    private TextField followYoutube;

    @FXML
    private TextField commentYoutube;

    @FXML
    private MenuButton modeFacebook;

    @FXML
    private MenuItem modeFacebookAuto;

    @FXML
    private MenuItem modeFacebookCustomize;

    @FXML
    private MenuItem modeTiktokAuto;

    @FXML
    private MenuItem modeTiktokCustomize;

    @FXML
    private MenuButton modeTiktok;

    @FXML
    private MenuItem modeYoutubeAuto;

    @FXML
    private MenuItem modeYoutubeCustomize;

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
               disableCustomizeFacebookTask(true);
               break;
           case "facebookCustomize":
               this.modeFacebook.setText(this.modeFacebookCustomize.getText());
               disableCustomizeFacebookTask(false);
               break;
           case "tiktokAuto":
               this.modeTiktok.setText(this.modeTiktokAuto.getText());
               disableCustomizeTiktokTask(true);
               break;
           case "tiktokCustomize":
               this.modeTiktok.setText(this.modeTiktokCustomize.getText());
               disableCustomizeTiktokTask(false);
               break;
           case "youtubeAuto":
               this.modeYoutube.setText(this.modeYoutubeAuto.getText());
               disableCustomizeYoutubeTask(true);
               break;
           case "youtubeCustomize":
               this.modeYoutube.setText(this.modeYoutubeCustomize.getText());
               disableCustomizeYoutubeTask(false);
               break;
           default:
       }

    }
    private void disableCustomizeFacebookTask(boolean disable){
       this.emotionFacebook.setDisable(disable);
       this.followFacebook.setDisable(disable);
       this.likePageFacebook.setDisable(disable);
       this.likePostFacebook.setDisable(disable);
    }

    private void disableCustomizeTiktokTask(boolean disable){
        this.followTiktok.setDisable(disable);
        this.loveTiktok.setDisable(disable);
    }

    private void disableCustomizeYoutubeTask(boolean disable){
        this.followYoutube.setDisable(disable);
        this.commentYoutube.setDisable(disable);
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
        disableCustomizeFacebookTask(true);
    }


    @FXML
    public void run(ActionEvent event) throws InterruptedException {
        String type = ((Node)event.getSource()).getUserData().toString();
        switch (type){
            case "facebook":
                Map<String,Integer> tasksFacebook = new HashMap<>();
                tasksFacebook.put("likePostFacebook",Integer.valueOf(this.likePostFacebook.getText()));
                tasksFacebook.put("likePageFacebook",Integer.valueOf(this.likePageFacebook.getText()));
                tasksFacebook.put("followFacebook",Integer.valueOf(this.followFacebook.getText()));
                tasksFacebook.put("emotionFacebook",Integer.valueOf(this.emotionFacebook.getText()));
                automationService.run("facebook",this.accountData,tasksFacebook);
                break;
            case "youtube":
                Map<String,Integer> tasksYoutube = new HashMap<>();
                tasksYoutube.put("commentYoutube",Integer.valueOf(this.commentYoutube.getText()));
                tasksYoutube.put("followYoutube",Integer.valueOf(this.followYoutube.getText()));
                automationService.run("youtube",this.accountData,tasksYoutube);
                break;
            case "tiktok":
                Map<String,Integer> tasksTiktok = new HashMap<>();
                tasksTiktok.put("loveTiktok",Integer.valueOf(this.loveTiktok.getText()));
                tasksTiktok.put("followTiktok",Integer.valueOf(this.followTiktok.getText()));
                automationService.run("tiktok",this.accountData,tasksTiktok);
                break;
        }

    }
}
