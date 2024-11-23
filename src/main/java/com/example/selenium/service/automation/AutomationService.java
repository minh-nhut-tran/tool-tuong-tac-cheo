package com.example.selenium.service.automation;

import com.example.selenium.pojo.Account;
import com.example.selenium.selenium.FacebookTask;

public class AutomationService  implements  IAutomationService{

    private final FacebookTask facebookTask;

    public AutomationService(){
        facebookTask = new FacebookTask();
    }

    public void run(String type, Account account) throws InterruptedException {
        switch (type){
            case "facebook":
                facebookTask.runModeAutomation(account);
                break;
            case "youtube":

                break;
            case "tiktok":
                break;
        }
    }

}
