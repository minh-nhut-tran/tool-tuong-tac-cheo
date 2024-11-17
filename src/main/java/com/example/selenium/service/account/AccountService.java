package com.example.selenium.service.account;

import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.pojo.Facebook;
import com.example.selenium.service.account_facebook.AccountFacebookService;
import com.example.selenium.service.account_facebook.IAccountFacebookService;


public class AccountService implements IAccountService{


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
    public boolean delete(AccountSocial account) {
        return false;
    }

    @Override
    public AccountSocial getAccount(String accountID) {
        return null;
    }


}
