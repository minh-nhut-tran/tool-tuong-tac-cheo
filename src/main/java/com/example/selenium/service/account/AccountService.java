package com.example.selenium.service.account;

import com.example.selenium.pojo.AccountSocial;

public class AccountService implements IAccountService{
    @Override
    public boolean save(AccountSocial account) {
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
