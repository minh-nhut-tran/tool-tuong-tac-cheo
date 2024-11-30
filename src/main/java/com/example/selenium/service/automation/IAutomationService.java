package com.example.selenium.service.automation;

import com.example.selenium.pojo.Account;

import java.util.Map;

public interface IAutomationService {

    public void run(String type, Account account, Map<String,Integer> tasks) throws InterruptedException;

}
