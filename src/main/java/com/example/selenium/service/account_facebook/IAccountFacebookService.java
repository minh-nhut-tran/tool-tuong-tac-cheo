package com.example.selenium.service.account_facebook;


import com.example.selenium.pojo.AccountSocial;
import javafx.scene.layout.HBox;

import java.util.List;


public interface IAccountFacebookService {

    public List<HBox> loadAccountFacebook();

    public boolean loginAccountFacebook(AccountSocial account) throws InterruptedException;

    public List<AccountSocial> getAllAccountFacebook();
}
