package com.example.selenium.service.account;

import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.pojo.Facebook;
import com.example.selenium.service.account_facebook.AccountFacebookService;
import com.example.selenium.service.account_facebook.IAccountFacebookService;
import com.example.selenium.service.login.ILoginService;
import com.example.selenium.service.login.LoginService;
import com.example.selenium.service.navigation.INavigationService;
import com.example.selenium.service.navigation.NavigationService;
import javafx.scene.input.MouseEvent;

import java.io.IOException;


public class AccountService implements IAccountService{

    private ILoginService loginService;
    private INavigationService navigationService;

    private final IAccountFacebookService accountFacebookService;

    public AccountService(){
        accountFacebookService = new AccountFacebookService();
    }


    @Override
    public boolean save(AccountSocial account) throws InterruptedException {
        if(account instanceof Facebook){
            return accountFacebookService.loginAccountFacebook(account);
        }
        return false;
    }

    @Override
    public void delete(String socialType, String socialID, MouseEvent event) throws IOException {
       if(socialType.equals("facebook")){
           loginService = new LoginService();
           navigationService = new NavigationService();
            accountFacebookService.deleteAccountFacebook(socialID);
            navigationService.router("account", loginService.loginByAccessToken(loginService.getAccessTokenAvailable()),event);
       }
    }

    @Override
    public AccountSocial getAccount(String accountID) {
        return null;
    }


}
