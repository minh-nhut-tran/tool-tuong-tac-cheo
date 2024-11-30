package com.example.selenium.service.navigation;

import com.example.selenium.pojo.Account;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public interface INavigationService {

    public void router(String type, Account account, Stage event) throws IOException;

}
