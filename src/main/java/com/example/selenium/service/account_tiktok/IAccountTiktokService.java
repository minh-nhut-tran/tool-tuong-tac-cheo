package com.example.selenium.service.account_tiktok;

import com.example.selenium.pojo.AccountSocial;
import javafx.scene.layout.HBox;

import java.util.List;

public interface IAccountTiktokService {

    public List<HBox> loadAccountTiktok();

    public boolean loginAccountTiktok(AccountSocial account, String typeLogin) throws InterruptedException;

    public void deleteAccountTiktok(String tiktokID) ;

    public void setStatus(String tiktokID, String status) ;

    public List<AccountSocial> getAllAccountTiktok();
}
