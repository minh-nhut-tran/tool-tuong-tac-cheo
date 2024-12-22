package com.example.selenium.service.automation;

import com.example.selenium.pojo.Account;
import com.example.selenium.selenium.FacebookTask;
import com.example.selenium.selenium.TiktokTask;

import java.util.Map;

public class AutomationService  implements  IAutomationService{
    public AutomationService(){

    }
    public void run(String type, Account account, Map<String,Integer> tasks) throws InterruptedException {
        switch (type){
            case "facebook":
                FacebookTask facebookTask = new FacebookTask();
                facebookTask.run(account,tasks);
                break;
            case "youtube":

            case "tiktok":
                TiktokTask tiktokTask = new TiktokTask();
                tiktokTask.run(account,tasks);
                break;
        }
    }

}
