package com.example.selenium.service.automation;

import com.example.selenium.pojo.Account;

public interface IAutomationService {

    public void run(String type, Account account) throws InterruptedException;

}
