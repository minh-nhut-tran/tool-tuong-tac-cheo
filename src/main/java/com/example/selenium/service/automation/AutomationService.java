package com.example.selenium.service.automation;

import com.example.selenium.pojo.Account;
import com.example.selenium.selenium.FacebookTask;

import java.util.Map;

public class AutomationService  implements  IAutomationService{

    private final FacebookTask facebookTask;

    public AutomationService(){
        facebookTask = new FacebookTask();
    }

    public void run(String type, Account account, Map<String,Integer> tasks) throws InterruptedException {
        switch (type){
            case "facebook":
                facebookTask.run(account,tasks);
                break;
            case "youtube":

                break;
            case "tiktok":
                break;
        }
    }

}
