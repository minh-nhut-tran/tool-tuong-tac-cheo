package com.example.selenium.controller;

import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.pojo.Tiktok;
import com.example.selenium.service.account.AccountService;
import com.example.selenium.service.account.IAccountService;
import com.example.selenium.service.account_tiktok.IAccountTiktokService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginTiktokController implements Initializable {

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    private IAccountService accountService;

    private IAccountTiktokService accountTiktokService;

    private HBox facebook;

    private HBox youtube;

    private HBox tiktok;

    private VBox containerAccount;

    private VBox containerContentAccount;

    private String state;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountService = new AccountService();
    }

    @FXML
    public void login() throws InterruptedException, IOException {
        AccountSocial accountSocial = new Tiktok();
        accountSocial.setUsername(userName.getText());
        accountSocial.setPassword(password.getText());
        if(accountService.save(accountSocial)){
            changeTypeAccount();
            this.stage.close();
        }
    }

    public void setData(HBox facebook, HBox youtube, HBox tiktok, VBox containerAccount, VBox containerContentAccount, String state, Stage stage){
        this.facebook = facebook;
        this.youtube = youtube;
        this.tiktok = tiktok;
        this.containerAccount = containerAccount;
        this.containerContentAccount = containerContentAccount;
        this.state = state;
        this.stage = stage;
    }

    public void changeTypeAccount() throws IOException {
        facebook.getStyleClass().replaceAll(s -> "account-button-non-active");
        youtube.getStyleClass().replaceAll(s -> "account-button-non-active");
        tiktok.getStyleClass().replaceAll(s -> "account-button-non-active");
        containerAccount.getChildren().remove(0);
        containerContentAccount.getChildren().clear();
        facebook.getStyleClass().add("account-button-tiktok-active");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../account-tiktok-view.fxml"));
        VBox header = loader.load();
        containerAccount.getChildren().add(0,header);
        containerContentAccount.getChildren().addAll(accountTiktokService.loadAccountTiktok());
        this.state = "tiktok";
    }

}
