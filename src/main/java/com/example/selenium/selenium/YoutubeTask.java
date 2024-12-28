package com.example.selenium.selenium;

import com.example.selenium.common.DirectWindows;
import com.example.selenium.common.EstablishAccount;
import com.example.selenium.common.SeleniumHandler;
import com.example.selenium.config.ChromeOptionsConfig;
import com.example.selenium.constants.URL;
import com.example.selenium.pojo.Account;
import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.service.account_youtube.AccountYoutubeService;
import com.example.selenium.service.account_youtube.IAccountYoutubeService;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class YoutubeTask {

    private static List<AccountSocial> accountSocials;

    public YoutubeTask() {
        IAccountYoutubeService accountYoutubeService = new AccountYoutubeService();
        accountSocials = accountYoutubeService.getAllAccountYoutube();
    }

    public void run(Account account, Map<String, Integer> tasks) throws InterruptedException {
        if(accountSocials == null || accountSocials.isEmpty()) return;
        if(accountSocials.get(0).getStatus().equals("INACTIVE")){
            accountSocials.remove(0);
            run(account, tasks);
        }
        ChromeOptionsConfig options = new ChromeOptionsConfig();
        WebDriver driver = new ChromeDriver(options.setupOptionsChrome("Youtube", accountSocials.get(0).getProfile()));
        EstablishAccount.EstablishAccountYoutube(accountSocials.get(0),account);
        loginWebTTC(driver,account);
        doTasks(driver, tasks.get("followYoutube"), URL.URL_FOLLOW_YOUTUBE,"followYoutube");
        doTasks(driver, tasks.get("commentYoutube"), URL.URL_COMMENT_YOUTUBE,"commentYoutube");
        driver.close();
        accountSocials.remove(0);
        run(account, tasks);

    }

    private void doTasks(WebDriver driver, int numberOfTasks, String urlTask, String type) {
       try {
           if(numberOfTasks == 0) return;
           int count = 0;
           driver.get(urlTask);
           Thread.sleep(5000);
           for(WebElement task : getWebElementsTasks(driver)){
               WebElement doTaskButton = task.findElement(By.cssSelector(".form-group.text-center button"));
               String comment = null;
               if(type.equals("commentYoutube")){
                   comment = getWebElementsComment(task).get(0).getText();
               }
               doTaskButton.click();
               Thread.sleep(5000);
               DirectWindows.switchScreenToNextWindow(driver);
               Thread.sleep(5000);
               switch (type){
                   case "commentYoutube":
                       commentYoutube(driver, comment);
                       break;
                   case "followYoutube":
                       followYoutube(driver,count);
                       break;
               }
               count++;
               Thread.sleep(5000);
               if (count == numberOfTasks) return;
           }
           if(count < numberOfTasks){
               doTasks(driver, numberOfTasks - count, urlTask, type);
           }
       }catch (Exception e){
            e.printStackTrace();
       }

    }

    private void followYoutube(WebDriver driver, int count) throws InterruptedException {
        Thread.sleep(60000);
        WebElement buttonFollow = SeleniumHandler.getElementFromXpaths(new String[]{
                "//yt-button-shape[@id='subscribe-button-shape']"
        },driver);
        if(buttonFollow != null){
            try{
                buttonFollow.click();
            }catch (Exception ignored){}
        }
        Thread.sleep(5000);
        DirectWindows.closeTabAndSwitchToTabFirst(driver);
        if(count != 0 && count % 4 == 0){
            getMoneyWhenDoneTask(driver);
        }
    }

    private void commentYoutube(WebDriver driver, String comment) throws InterruptedException {
        if(comment == null) return;
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 300)");
        Thread.sleep(5000);
        WebElement clickComment = SeleniumHandler.getElementFromXpaths(new String[]{
                "//div[@id='placeholder-area']"
        },driver);
        if(clickComment == null) return;
        clickComment.click();
        Thread.sleep(5000);
        WebElement inputComment = SeleniumHandler.getElementFromXpaths( new String[]{
                "//div[@id='contenteditable-root']"
        },driver);
        if(inputComment != null){
            inputComment.sendKeys(comment);
            Thread.sleep(5000);
            try{
                WebElement submitComment = SeleniumHandler.getElementFromXpaths(new String[]{
                        "//ytd-button-renderer[@id='submit-button']"
                },driver);
                if(submitComment != null){
                    submitComment.click();
                }
            }catch (Exception ignored){

            }
        }
    }


    private void loginWebTTC(WebDriver driver, Account account) throws InterruptedException {
        driver.get(URL.URL_WEB_TTC);
        Thread.sleep(5000);
        driver.manage().addCookie(new Cookie("PHPSESSID", account.getSession().split("=")[1].trim()));
        driver.get(Objects.requireNonNull(driver.getCurrentUrl()));
        Thread.sleep(5000);
    }

    private List<WebElement> getWebElementsTasks(WebDriver driver) {
        WebElement containerTask = SeleniumHandler.getElementFromXpaths(new String[]{"//div[@id='dspost']"}, driver);
        return containerTask.findElements(By.xpath("./*"));
    }

    private List<WebElement> getWebElementsComment(WebElement containerComment) {
        WebElement commentLists = SeleniumHandler.getElementFromXpathsOnElement(new String[]{
            "//ul[@class='list-group']"
        },containerComment);
        return commentLists.findElements(By.xpath("./*"));
    }
    private void getMoneyWhenDoneTask(WebDriver driver) throws InterruptedException {
        WebElement buttonGetMoney = SeleniumHandler.getElementFromXpaths(new String[]{"//button[@id='nhanall']"}, driver);
        if(buttonGetMoney != null) buttonGetMoney.click();
    }
    

}
