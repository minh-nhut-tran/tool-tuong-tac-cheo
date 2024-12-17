package com.example.selenium.service.account;

import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.pojo.Facebook;
import com.example.selenium.pojo.Tiktok;
import com.example.selenium.service.account_facebook.AccountFacebookService;
import com.example.selenium.service.account_facebook.IAccountFacebookService;
import com.example.selenium.service.account_tiktok.AccountTiktokService;
import com.example.selenium.service.account_tiktok.IAccountTiktokService;
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

    public AccountService(){
        accountFacebookService = new AccountFacebookService();
        loginService = new LoginService();
        navigationService = new NavigationService();
        accountTiktokService = new AccountTiktokService();
    }


    @Override
    public boolean save(AccountSocial account) throws InterruptedException {
        if(account instanceof Facebook){
            return accountFacebookService.loginAccountFacebook(account);
        }else if(account instanceof Tiktok){
            return accountTiktokService.loginAccountTiktok(account);
        }
        return false;
    }

    @Override
    public void delete(String socialType, String socialID, Stage stage) throws IOException {
       if(socialType.equals("facebook")){
            accountFacebookService.deleteAccountFacebook(socialID);
            navigationService.router("account", loginService.loginByAccessToken(loginService.getAccessTokenAvailable()),stage);
       }
    }



    @Override
    public AccountSocial getAccount(String accountID) {
        return null;
    }

    @Override
    public void setStatusAccount(String socialType, String socialID,String status, Stage stage) throws IOException {
        if(socialType.equals("facebook")){
            accountFacebookService.setStatus(socialID,status);
            navigationService.router("account", loginService.loginByAccessToken(loginService.getAccessTokenAvailable()),stage);
        }
    }


}
