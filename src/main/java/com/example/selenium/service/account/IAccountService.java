package com.example.selenium.service.account;

import com.example.selenium.pojo.AccountSocial;

public interface IAccountService {
    public boolean save(AccountSocial account) throws InterruptedException;

    public boolean delete(AccountSocial account);

    public AccountSocial getAccount(String accountID);
}
