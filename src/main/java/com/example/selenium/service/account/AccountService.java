package com.example.selenium.service.account;

import com.example.selenium.pojo.*;
import com.example.selenium.service.account_facebook.AccountFacebookService;
import com.example.selenium.service.account_facebook.IAccountFacebookService;
import com.example.selenium.service.account_tiktok.AccountTiktokService;
import com.example.selenium.service.account_tiktok.IAccountTiktokService;
import com.example.selenium.service.account_youtube.AccountYoutubeService;
import com.example.selenium.service.account_youtube.IAccountYoutubeService;
import com.example.selenium.service.login.ILoginService;
import com.example.selenium.service.login.LoginService;
import com.example.selenium.service.navigation.INavigationService;
import com.example.selenium.service.navigation.NavigationService;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class AccountService implements IAccountService{

    private final ILoginService loginService;
    private final INavigationService navigationService;
    private final IAccountFacebookService accountFacebookService;
    private final IAccountTiktokService accountTiktokService;
    private final IAccountYoutubeService accountYoutubeService;

    public AccountService(){
        accountFacebookService = new AccountFacebookService();
        loginService = new LoginService();
        navigationService = new NavigationService();
        accountTiktokService = new AccountTiktokService();
        accountYoutubeService = new AccountYoutubeService();
    }


    @Override
    public boolean save(AccountSocial account, String typeLogin) throws InterruptedException {
        if(account instanceof Facebook){
            return accountFacebookService.loginAccountFacebook(account);
        }else if(account instanceof Tiktok){
            return accountTiktokService.loginAccountTiktok(account,typeLogin);
        }else if(account instanceof Youtube){
            return accountYoutubeService.loginAccountYoutube(account);
        }
        return false;
    }

    @Override
    public void delete(String socialType, String socialID, Stage stage) throws IOException {
        Account account = loginService.loginByAccessToken(loginService.getAccessTokenAvailable());
        switch (socialType) {
            case "facebook":
                accountFacebookService.deleteAccountFacebook(socialID);
                account.setCurrentState("facebook");
                break;
            case "tiktok":
                accountTiktokService.deleteAccountTiktok(socialID);
                account.setCurrentState("tiktok");
                break;
            case "youtube":
                accountYoutubeService.deleteAccountTiktok(socialID);
                account.setCurrentState("youtube");
                break;
        }
        navigationService.router("account",account,stage);
    }



    @Override
    public AccountSocial getAccount(String accountID) {
        return null;
    }

    @Override
    public void setStatusAccount(String socialType, String socialID,String status, Stage stage) throws IOException {
        Account account = loginService.loginByAccessToken(loginService.getAccessTokenAvailable());
        if(socialType.equals("facebook")){
            accountFacebookService.setStatus(socialID,status);
            account.setCurrentState("facebook");
        }else if(socialType.equals("tiktok")){
            accountTiktokService.setStatus(socialID,status);
            account.setCurrentState("tiktok");
        }else if(socialType.equals("youtube")){
            accountYoutubeService.setStatus(socialID,status);
            account.setCurrentState("youtube");
        }
        navigationService.router("account", account,stage);
    }


}
