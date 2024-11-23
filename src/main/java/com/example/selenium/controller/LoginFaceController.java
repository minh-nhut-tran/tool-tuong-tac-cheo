package com.example.selenium.controller;

import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.pojo.Facebook;
import com.example.selenium.service.account.AccountService;
import com.example.selenium.service.account.IAccountService;
import com.example.selenium.service.account_facebook.AccountFacebookService;
import com.example.selenium.service.account_facebook.IAccountFacebookService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginFaceController implements Initializable {

    @FXML
    private TextField userName;
    @FXML
    private TextField pageName;

    @FXML
    private VBox containerForm;

    @FXML
    private Label usePage;

    @FXML
    private PasswordField password;

    private IAccountService accountService;

    private IAccountFacebookService accountFacebookService;

    private HBox facebook;

    private HBox youtube;

    private HBox tiktok;

    private VBox containerAccount;

    private VBox containerContentAccount;

    private String state;

    private Stage stage;


    public void setData(HBox facebook,HBox youtube,HBox tiktok,VBox containerAccount,VBox containerContentAccount,String state, Stage stage){
        this.facebook = facebook;
        this.youtube = youtube;
        this.tiktok = tiktok;
        this.containerAccount = containerAccount;
        this.containerContentAccount = containerContentAccount;
        this.state = state;
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        accountService = new AccountService();

        pageName = createPageNameTextField();

        accountFacebookService = new AccountFacebookService();
    }

    @FXML
    public void login() throws InterruptedException {
       try {
           if(checkAccountExistOnSystem()) return;
           AccountSocial account = new Facebook();
           account.setUsername(userName.getText().trim());
           account.setPassword(password.getText().trim());
           if(!usePage.getText().equals("Use page to run?")){
               ((Facebook)account).setType("PAGE");
               ((Facebook)account).setName(pageName.getText());
           }else ((Facebook)account).setType("PROFILE");
           if(accountService.save(account)) {
               changeTypeAccount();
                this.stage.close();
           }
       }catch (Exception e){
          e.printStackTrace();
       }
    }

    @FXML
    public void usePage(){
        if(usePage.getText().equals("Use page to run?")){
            containerForm.getChildren().add(1,pageName);
            usePage.setText("Use profile to run?");
        }else{
            containerForm.getChildren().remove(pageName);
            usePage.setText("Use page to run?");
        }
    }

    private TextField createPageNameTextField(){
        TextField pageName = new TextField();
        pageName.setId("pageName");
        pageName.setFont(Font.font(15.0));
        pageName.setPrefHeight(50.0);
        pageName.setPromptText("Page name");
        pageName.setStyle("-fx-background-color: transparent; -fx-border-color: rgba(0,0,0,0.2); -fx-border-radius: 10;");
        return pageName;
    }



    public void changeTypeAccount() throws IOException {
        facebook.getStyleClass().replaceAll(s -> "account-button-non-active");
        youtube.getStyleClass().replaceAll(s -> "account-button-non-active");
        tiktok.getStyleClass().replaceAll(s -> "account-button-non-active");
        containerAccount.getChildren().remove(0);
        containerContentAccount.getChildren().clear();
        facebook.getStyleClass().add("account-button-facebook-active");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../account-facebook-view.fxml"));
        VBox header = loader.load();
        containerAccount.getChildren().add(0,header);
        containerContentAccount.getChildren().addAll(accountFacebookService.loadAccountFacebook());
        this.state = "face";
    }


    private boolean checkAccountExistOnSystem(){
        List<AccountSocial> accountsAvailable  = accountFacebookService.getAllAccountFacebook();
        for(AccountSocial accountAvailable : accountsAvailable){

            System.out.println(accountAvailable.getUsername());

            if(accountAvailable.getUsername().equals(userName.getText().trim())){
                if(!usePage.getText().equals("Use page to run?")  &&   pageName.getText().equals(((Facebook)accountAvailable).getName())  &&
                        ((Facebook)accountAvailable).getType().equals("PAGE")
                ){
                    alertError("Page already exists in system!");
                    return true;
                }else if(usePage.getText().equals("Use page to run?") && ((Facebook)accountAvailable).getType().equals("PROFILE") ){
                    alertError("Account already exists in system!");
                    return true;
                }
            }
        }
        return false;
    }

    public void alertError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.show();
    }

}
