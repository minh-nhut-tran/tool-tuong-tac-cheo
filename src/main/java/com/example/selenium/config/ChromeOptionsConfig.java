package com.example.selenium.config;

import com.example.selenium.constants.CurrentDirectory;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class ChromeOptionsConfig {


    public ChromeOptions setupOptionsChrome(String typeAccount,String profileName){
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("webdriver.chrome.driver", CurrentDirectory.currentDirectoryChromeDriver +"chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        String newProfilePath = "D:\\Youtube\\ChromeProfile\\"+typeAccount;
        options.addArguments("--user-data-dir=" + newProfilePath);
        options.addArguments("--profile-directory=" + profileName);
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("--no-first-run");
        options.addArguments("--no-service-autorun");
        options.addArguments("--password-store-basic");
        options.addArguments("--langen-US");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-features");
        options.addArguments("--disable-features=Translate");
        options.addArguments("--disable-features=Passwords");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-cpu");
        options.addArguments("--disable-blink-features=AutomationControlled");
        return options;
    }


}
