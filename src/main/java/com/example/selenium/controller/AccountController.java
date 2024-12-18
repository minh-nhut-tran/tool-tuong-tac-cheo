package com.example.selenium.controller;

import com.example.selenium.pojo.Account;
import com.example.selenium.service.account_facebook.AccountFacebookService;
import com.example.selenium.service.account_facebook.IAccountFacebookService;
import com.example.selenium.service.account_tiktok.AccountTiktokService;
import com.example.selenium.service.account_tiktok.IAccountTiktokService;
import com.example.selenium.service.account_youtube.AccountYoutubeService;
import com.example.selenium.service.account_youtube.IAccountYoutubeService;
import com.example.selenium.service.navigation.INavigationService;
import com.example.selenium.service.navigation.NavigationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountController  implements Initializable {
    @FXML
    private VBox containerAccount;
    @FXML
    private VBox containerContentAccount;
    @FXML
    private Label balance;
    @FXML
    private Label userName;
    @FXML
    private HBox facebook;
    @FXML
    private HBox youtube;
    @FXML
    private HBox tiktok;

    private String state;
    private INavigationService navigationService;
    private IAccountFacebookService accountFacebookService;
    private IAccountTiktokService accountTiktokService;
    private IAccountYoutubeService accountYoutubeService;
    private Account accountData;
    public void setAccountData(Account account) {
        this.accountData = account;
        if(this.accountData != null){
            this.balance.setText(String.valueOf(accountData.getBalance()));
            this.userName.setText(accountData.getUser());
            try {
                facebook.getStyleClass().replaceAll(s -> "account-button-non-active");
                youtube.getStyleClass().replaceAll(s -> "account-button-non-active");
                tiktok.getStyleClass().replaceAll(s -> "account-button-non-active");

                loadTypeAccount(
                        this.accountData.getCurrentState() != null &&
                                !this.accountData.getCurrentState() .isEmpty()
                                ? this.accountData.getCurrentState()
                                : "facebook"
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    public void switchScreen(MouseEvent event) throws IOException {
        String screenName = ((Node)event.getSource()).getUserData().toString().trim();
        navigationService.router(screenName,this.accountData,(Stage)((Node)event.getSource()).getScene().getWindow());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigationService = new NavigationService();
        accountFacebookService = new AccountFacebookService();
        accountTiktokService = new AccountTiktokService();
        accountYoutubeService = new AccountYoutubeService();
    }

    @FXML
    public void changeTypeAccount(MouseEvent event){
        try {
            String type = ((Node) event.getSource()).getUserData().toString().trim();
            facebook.getStyleClass().replaceAll(s -> "account-button-non-active");
            youtube.getStyleClass().replaceAll(s -> "account-button-non-active");
            tiktok.getStyleClass().replaceAll(s -> "account-button-non-active");
            containerAccount.getChildren().remove(0);
            containerContentAccount.getChildren().clear();
            loadTypeAccount(type);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void loadTypeAccount(String type) throws IOException {
        switch (type) {
            case "facebook":
                facebook.getStyleClass().add("account-button-facebook-active");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../account-facebook-view.fxml"));
                VBox header = loader.load();
                containerAccount.getChildren().add(0,header);
                containerContentAccount.getChildren().addAll(accountFacebookService.loadAccountFacebook());
                this.state = "face";
                break;
            case "youtube":
                youtube.getStyleClass().add("account-button-youtube-active");
                FXMLLoader loaderYoutube = new FXMLLoader(getClass().getResource("../account-youtube-view.fxml"));
                VBox headerYoutube = loaderYoutube.load();
                containerAccount.getChildren().add(0, headerYoutube);
                containerContentAccount.getChildren().addAll(accountYoutubeService.loadAccountYoutube());
                this.state = "youtube";
                break;
            case "tiktok":
                tiktok.getStyleClass().add("account-button-tiktok-active");
                FXMLLoader loaderTiktok = new FXMLLoader(getClass().getResource("../account-tiktok-view.fxml"));
                VBox headerTiktok = loaderTiktok.load();
                containerAccount.getChildren().add(0, headerTiktok);
                containerContentAccount.getChildren().addAll(accountTiktokService.loadAccountTiktok());
                this.state = "tiktok";
                break;
        }
    }

    @FXML
    public void addAccount(){
        try {
            if(this.state == null) throw new Exception("state is not valid!");

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../login-"+this.state+"-view.fxml"));
            Parent root = loader.load();

            switch (this.state){
                case "face":
                    LoginFaceController loginFaceController = loader.getController();
                    if(loginFaceController != null) loginFaceController.setData(facebook,youtube,tiktok,containerAccount
                            ,containerContentAccount,state,stage);
                    break;
                case "youtube":

                    break;
                case "tiktok":
                    LoginTiktokController loginTiktokController = loader.getController();
                    if (loginTiktokController != null) {
                        loginTiktokController.setData(facebook,youtube,tiktok,containerAccount
                                ,containerContentAccount,state,stage);
                    }
                    break;
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
