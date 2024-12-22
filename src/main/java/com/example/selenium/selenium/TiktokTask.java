package com.example.selenium.selenium;

import com.example.selenium.common.DirectWindows;
import com.example.selenium.common.EstablishAccount;
import com.example.selenium.common.SeleniumHandler;
import com.example.selenium.config.ChromeOptionsConfig;
import com.example.selenium.constants.URL;
import com.example.selenium.pojo.Account;
import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.service.account_tiktok.AccountTiktokService;
import com.example.selenium.service.account_tiktok.IAccountTiktokService;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TiktokTask {

    private static List<AccountSocial> accountSocialList;
    private ChromeOptionsConfig options;

    public TiktokTask() {
        IAccountTiktokService accountTiktokService = new AccountTiktokService();
        accountSocialList = accountTiktokService.getAllAccountTiktok();
    }
    public void run(Account account, Map<String, Integer> tasks) throws InterruptedException {
        if(accountSocialList == null || accountSocialList.isEmpty()) return;
        if(accountSocialList.get(0).getStatus().equals("INACTIVE")){
            accountSocialList.remove(0);
            run(account, tasks);
        }
        ChromeOptionsConfig options = new ChromeOptionsConfig();
        WebDriver driver = new ChromeDriver(options.setupOptionsChrome("Tiktok",accountSocialList.get(0).getProfile()));
        EstablishAccount.EstablishAccountTiktok(accountSocialList.get(0),account);
        loginWebTTC(driver,account);

        doTasks(
                driver,
                tasks.get("loveTiktok"),
                URL.URL_LOVE_TIKTOK+"?access_token="+account.getAccessToken(),
                "loveTiktok");
        doTasks(
                driver,
                tasks.get("followTiktok"),
                URL.URL_FOLLOW_TIKTOK+"?access_token="+account.getAccessToken(),
                "followTiktok");

        driver.close();
        accountSocialList.remove(0);
        run(account, tasks);
    }

    private void loginWebTTC(WebDriver driver, Account account) throws InterruptedException {
        driver.get(URL.URL_WEB_TTC);
        Thread.sleep(5000);
        driver.manage().addCookie(new Cookie("PHPSESSID", account.getSession().split("=")[1].trim()));
        driver.get(Objects.requireNonNull(driver.getCurrentUrl()));
        Thread.sleep(5000);
    }

    private void doTasks(WebDriver driver, int numberOfTasks, String urlTask, String type) throws InterruptedException {
        if(numberOfTasks == 0) return;
        int count = 0;
        driver.get(urlTask);
        Thread.sleep(5000);
        List<WebElement> tasks = getWebElementsTasks(driver);
        if(tasks.isEmpty()) return;
        for(WebElement task : tasks){
            WebElement doTaskButton = task.findElement(By.cssSelector(".form-group.text-center button"));
            doTaskButton.click();
            Thread.sleep(5000);
            DirectWindows.switchScreenToNextWindow(driver);
            /*
                check cookie
             */
            Thread.sleep(5000);

            switch (type){
                case "loveTiktok":
                    doLoveTiktok(driver,count,type);
                    break;
                case "followTiktok":
                    doFollowTiktok(driver,count,type);
                    break;
            }
            Thread.sleep(5000);
            count++;
            if(count == numberOfTasks) return;
        }
    }

    private void doLoveTiktok(WebDriver driver, int count, String type) throws InterruptedException {
        try{
            WebElement loveTiktokButton = SeleniumHandler.getElementFromXpaths(new String[]{
                    "//span[@data-e2e='like-icon']"
            },driver);
            if(loveTiktokButton != null){
                loveTiktokButton.click();
            }
            Thread.sleep(5000);
        }catch (Exception ignored){}
        getMoneyWhenDoneTask(driver,count,type);
    }

    private void doFollowTiktok(WebDriver driver,int count, String type) throws InterruptedException {
        WebElement followTiktokButton = SeleniumHandler.getElementFromXpaths(new String[]{
                "//button[@data-e2e='follow-button' and @type='button']"
        },driver);
        if(followTiktokButton != null){
            followTiktokButton.click();
        }
        Thread.sleep(5000);
        getMoneyWhenDoneTask(driver,count,type);
    }

    private List<WebElement> getWebElementsTasks(WebDriver driver) {
        WebElement containerTask = SeleniumHandler.getElementFromXpaths(new String[]{"//div[@id='dspost']"}, driver);
        if(containerTask == null) return new ArrayList<>();
        return containerTask.findElements(By.xpath("./*"));
    }

    private void getMoneyWhenDoneTask(WebDriver driver, int count, String type) throws InterruptedException {
        DirectWindows.closeTabAndSwitchToTabFirst(driver);
        WebElement buttonGetMoney = SeleniumHandler.getElementFromXpaths(new String[]{"//button[@id='nhanall']"}, driver);
        if(count != 0 && count % 8 == 0 && type.equals("followTiktok")){
            if(buttonGetMoney != null) buttonGetMoney.click();
        }
        if(count != 0 && count % 4 == 0 && type.equals("loveTiktok")){
            if(buttonGetMoney != null) buttonGetMoney.click();
        }
    }

}
