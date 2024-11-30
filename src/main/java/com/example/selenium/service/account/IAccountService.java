package com.example.selenium.service.account;

import com.example.selenium.pojo.AccountSocial;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public interface IAccountService {
    public boolean save(AccountSocial account) throws InterruptedException;

    public void delete(String socialType, String socialID, Stage stage) throws IOException;

    public AccountSocial getAccount(String accountID);

    public void setStatusAccount(String socialType, String socialID, String status, Stage stage) throws IOException;
}
