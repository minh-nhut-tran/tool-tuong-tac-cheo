package com.example.selenium.service.automation;

import com.example.selenium.pojo.Account;
import com.example.selenium.selenium.FacebookTask;
import com.example.selenium.selenium.TiktokTask;
import com.example.selenium.selenium.YoutubeTask;

import java.util.Map;

public class AutomationService  implements  IAutomationService{
    public AutomationService(){

    }
    public void run(String type, Account account, Map<String,Integer> tasks) throws InterruptedException {
        try{
            switch (type){
                case "facebook":
                    FacebookTask facebookTask = new FacebookTask();
                    facebookTask.run(account,tasks);
                    break;
                case "youtube":
                    YoutubeTask youtubeTask = new YoutubeTask();
                    youtubeTask.run(account,tasks);
                case "tiktok":
                    TiktokTask tiktokTask = new TiktokTask();
                    tiktokTask.run(account,tasks);
                    break;
            }
        }catch (Exception ignored){}
    }

}
