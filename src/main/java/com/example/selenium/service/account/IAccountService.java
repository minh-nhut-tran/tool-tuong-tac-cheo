package com.example.selenium.service.account;

import com.example.selenium.pojo.AccountSocial;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public interface IAccountService {
    public boolean save(AccountSocial account) throws InterruptedException;

    public void delete(String socialType, String socialID, MouseEvent event) throws IOException;

    public AccountSocial getAccount(String accountID);
}
