package com.example.selenium.controller;

import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.pojo.Tiktok;
import com.example.selenium.service.account.AccountService;
import com.example.selenium.service.account.IAccountService;
import com.example.selenium.service.account_tiktok.AccountTiktokService;
import com.example.selenium.service.account_tiktok.IAccountTiktokService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
        accountTiktokService = new AccountTiktokService();
    }

    @FXML
    public void login(MouseEvent event) {
        String userData = ((Node)event.getSource()).getUserData().toString();
        try{
            AccountSocial accountSocial = new Tiktok();
            if(userData.equals("auto")){
                if(!checkAccountExistOnSystem() && checkUsernameAndPasswordNotEmpty()){
                    accountSocial.setUsername(userName.getText());
                    accountSocial.setPassword(password.getText());
                    accountService.save(accountSocial,userData);
                }else return;
            }else{
                 accountService.save(accountSocial,userData);
            }
            changeTypeAccount();
            this.stage.close();
        }catch (Exception e){
            e.printStackTrace();
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
        tiktok.getStyleClass().add("account-button-tiktok-active");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../account-tiktok-view.fxml"));
        VBox header = loader.load();
        containerAccount.getChildren().add(0,header);
        containerContentAccount.getChildren().addAll(accountTiktokService.loadAccountTiktok());
        this.state = "tiktok";
    }
    private boolean checkAccountExistOnSystem(){
        List<AccountSocial> accountsAvailable  = accountTiktokService.getAllAccountTiktok();
        for(AccountSocial accountAvailable : accountsAvailable){
            if( !accountAvailable.getUsername().isEmpty() && accountAvailable.getUsername().equals(userName.getText().trim())){
                alertError("Account already exists in system!");
                return true;
            }
        }
        return false;
    }

    private boolean checkUsernameAndPasswordNotEmpty(){
        if(this.userName.getText().isEmpty() || this.password.getText().isEmpty()){
            alertError("Username and password cannot be empty!");
            return false;
        }
        return true;
    }

    public void alertError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.show();
    }


}
