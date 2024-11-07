package com.example.selenium.service.navigation;

import com.example.selenium.pojo.Account;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public interface INavigationService {

    public void router(String type, Account account, MouseEvent event) throws IOException;

}
