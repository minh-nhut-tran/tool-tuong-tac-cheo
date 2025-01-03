package com.example.selenium.service.account_youtube;

import com.example.selenium.pojo.AccountSocial;
import javafx.scene.layout.HBox;

import java.util.List;

public interface IAccountYoutubeService {
    public List<HBox> loadAccountYoutube();
    public boolean loginAccountYoutube(AccountSocial account) throws InterruptedException;
    public List<AccountSocial> getAllAccountYoutube();
    public void setStatus(String tiktokID, String status);
    public void deleteAccountTiktok(String tiktokID);
}
