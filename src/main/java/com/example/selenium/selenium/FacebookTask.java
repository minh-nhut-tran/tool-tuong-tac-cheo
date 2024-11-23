package com.example.selenium.selenium;

import com.example.selenium.common.DirectWindows;
import com.example.selenium.common.EstablishAccount;
import com.example.selenium.common.FacebookCookieHandler;
import com.example.selenium.common.SeleniumHandler;
import com.example.selenium.config.ChromeOptionsConfig;
import com.example.selenium.constants.URL;
import com.example.selenium.pojo.Account;
import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.pojo.Facebook;
import com.example.selenium.service.account_facebook.AccountFacebookService;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class FacebookTask {

    private static List<AccountSocial> accountSocials;

    public FacebookTask() {
        AccountFacebookService accountFacebookService = new AccountFacebookService();
        accountSocials = accountFacebookService.getAllAccountFacebook();
    }

    public void runModeCustomize() {

    }

    public void runModeAutomation(Account account) throws InterruptedException {
        if (accountSocials == null || accountSocials.isEmpty()) return;
        ChromeOptionsConfig options = new ChromeOptionsConfig();
        WebDriver driver = new ChromeDriver(options.setupOptionsChrome("Facebook", accountSocials.get(0).getProfile()));
        EstablishAccount.EstablishAccountFacebook(accountSocials.get(0), account);
        loginWebTTC(driver, account);

        doTasks(driver,10,URL.URL_DO_TASK_LIKE_PAGE_CHEO,"like_page");
        doTasks(driver,10,URL.URL_DO_TASK_SUB_CHEO,"sub_cheo");
        doTasks(driver,10,URL.URL_DO_TASK_LIKE_CHEO_VIP,"like_post");

        driver.close();
        accountSocials.remove(0);
        runModeAutomation(account);
    }

    private void loginWebTTC(WebDriver driver, Account account) throws InterruptedException {
        driver.get(URL.URL_WEB_TTC);
        Thread.sleep(5000);
        driver.manage().addCookie(new Cookie("PHPSESSID", account.getSession().split("=")[1].trim()));
        driver.get(driver.getCurrentUrl());
        Thread.sleep(5000);
    }


    private void doTasks(WebDriver driver, int numberOfTasks, String urlTask, String type) throws InterruptedException {
        int count = 0;
        driver.get(urlTask);
        Thread.sleep(5000);
        for (WebElement task : getWebElementsTasks(driver)) {
            WebElement doTaskButton = task.findElement(By.cssSelector(".form-group.text-center button"));
            doTaskButton.click();
            Thread.sleep(5000);
            DirectWindows.switchScreenToNextWindow(driver);
            checkAndAddCookieFacebook(driver);
            Thread.sleep(5000);

            switch (type) {
                case "sub_cheo":
                    doTaskSubCheo(driver);
                    break;
                case "like_page":
                    doTaskLikePage(driver);
                    break;
                case "like_post":
                    String currentURL = driver.getCurrentUrl();
                    if(currentURL.contains("reel")){
                        doTaskLikeReel(driver);
                    }else doTaskLikePost(driver);
                    break;
            }
            Thread.sleep(5000);
            getMoneyWhenDoneTask(driver);
            Thread.sleep(5000);
            count++;
            if (count == numberOfTasks) return;
        }
    }

    private void doTaskSubCheo(WebDriver driver) throws InterruptedException {
        WebElement subButton = SeleniumHandler.getElementFromXpaths(new String[]{
                "//div[@aria-label='Add friend']",
                "//div[@aria-label='Follow']",
                "//div[@aria-label='Thêm bạn bè']",
                "//div[@aria-label='Theo dõi']"
        }, driver);
        if (subButton != null) subButton.click();
    }

    private void doTaskLikePage(WebDriver driver) throws InterruptedException {
        WebElement likePageButton = SeleniumHandler.getElementFromXpaths(new String[]{
                "//div[@aria-label='Follow']",
                "//div[@aria-label='Like']",
                "//div[@aria-label='Theo dõi']",
                "//div[@aria-label='Thích']"
        }, driver);
        if (likePageButton != null) likePageButton.click();
    }

    private void doTaskLikeReel(WebDriver driver) throws InterruptedException {
        WebElement likeReelButton = SeleniumHandler.getElementFromXpaths(new String[]{
                "//div[@aria-label='Like' and @role='button']",
                "//div[@aria-label='Thích' and @role='button']"
        }, driver);
        if (likeReelButton != null) likeReelButton.click();
    }

    private void doTaskLikePost(WebDriver driver) throws InterruptedException {
        Thread.sleep(5000);
        WebElement dialog = SeleniumHandler.getElementFromXpaths(new String[]{
                "//div[@class='x78zum5 xdt5ytf x1iyjqo2' and @role='dialog']"
        },driver);
        if(dialog == null ) {
            doTaskListPostFirstLike(driver);
        }
        else {
            List<WebElement> likeButtons = SeleniumHandler.getElementsFromsXpathsOnElement(new String[]{
                    "//div[@aria-label='Thích' and @role='button']","//div[@aria-label='Like' and @role='button']"
            },dialog);
            if(likeButtons != null){
                for(WebElement likeButton : likeButtons){
                    try{
                        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", likeButton);
                        likeButton.click();
                        break;
                    }catch (Exception ignored){
                        //Skip button can not click
                    }
                }
            }
        }

    }

    private void doTaskListPostFirstLike(WebDriver driver){
        WebElement likeButton = null;
        try {
             likeButton = SeleniumHandler.getElementFromXpaths(new String[]{
                    "//div[@aria-label='Thích' and @role='button']","//div[@aria-label='Like' and @role='button']"
            },driver);
            if(likeButton != null) likeButton.click();
        }catch (Exception ignored){
            if(likeButton != null){
                ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", likeButton);
                likeButton.click();
            }
        }
    }



    private void checkAndAddCookieFacebook(WebDriver driver) {

        if(((Facebook)accountSocials.get(0)).getType().equals("PAGE")){
            if( driver.manage().getCookieNamed("i_user") == null
                    || driver.manage().getCookieNamed("i_user").getValue().isEmpty()
                    || !driver.manage().getCookieNamed("i_user").getValue().equals(((Facebook)accountSocials.get(0)).getFacebookID()) ){
                FacebookCookieHandler.addCookieFacebookToWeb(driver, accountSocials.get(0).getCookie());
                driver.get(driver.getCurrentUrl());
            }
        }else if (
                driver.manage().getCookieNamed("xs") == null
                        || driver.manage().getCookieNamed("c_user") == null
                        || driver.manage().getCookieNamed("xs").getValue().isEmpty()
                        || driver.manage().getCookieNamed("c_user").getValue().isEmpty()
        ) {
            FacebookCookieHandler.addCookieFacebookToWeb(driver, accountSocials.get(0).getCookie());
            driver.get(driver.getCurrentUrl());
        }

    }

    private List<WebElement> getWebElementsTasks(WebDriver driver) {
        WebElement containerTask = SeleniumHandler.getElementFromXpaths(new String[]{"//div[@id='dspost']"}, driver);
        return containerTask.findElements(By.xpath("./*"));
    }

    private void getMoneyWhenDoneTask(WebDriver driver) {
        DirectWindows.closeTabAndSwitchToTabFirst(driver);
        WebElement buttonNhanTien = SeleniumHandler.getElementFromXpaths(new String[]{"//button[text()='Nhận tiền']"}, driver);
        if (buttonNhanTien != null) buttonNhanTien.click();
    }

}
