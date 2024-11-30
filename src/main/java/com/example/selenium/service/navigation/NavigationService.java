package com.example.selenium.service.navigation;

import com.example.selenium.controller.AccountController;
import com.example.selenium.controller.AutomationController;
import com.example.selenium.controller.HomeController;
import com.example.selenium.controller.SettingController;
import com.example.selenium.pojo.Account;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class NavigationService implements INavigationService {
    @Override
    public void router(String screenName, Account account, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../../"+screenName+"-view.fxml")));
        Parent root = loader.load();
        switch (screenName){
            case "home":
                HomeController homeController = loader.getController();
                if(homeController != null) homeController.setAccountData(account);
                break;
            case "automation":
                AutomationController automationController = loader.getController();
                if(automationController != null) automationController.setAccountData(account);
                break;
            case "account":
                AccountController accountController = loader.getController();
                if(accountController != null) accountController.setAccountData(account);
                break;
            case "setting":
                SettingController settingController = loader.getController();
                if(settingController != null) settingController.setAccountData(account);
                break;
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("../../home-view-css.css")).toExternalForm());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
