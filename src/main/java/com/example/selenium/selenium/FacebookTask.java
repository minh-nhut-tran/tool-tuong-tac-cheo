package com.example.selenium.selenium;

import com.example.selenium.common.*;
import com.example.selenium.config.ChromeOptionsConfig;
import com.example.selenium.constants.CurrentDirectory;
import com.example.selenium.constants.URL;
import com.example.selenium.pojo.Account;
import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.pojo.Facebook;
import com.example.selenium.service.account_facebook.AccountFacebookService;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FacebookTask {

    private static List<AccountSocial> accountSocials;

    public FacebookTask() {
        AccountFacebookService accountFacebookService = new AccountFacebookService();
        accountSocials = accountFacebookService.getAllAccountFacebook();
    }
    public void run(Account account, Map<String,Integer> tasks) throws InterruptedException {
        if (accountSocials == null || accountSocials.isEmpty()) return;
        if(accountSocials.get(0).getStatus().equals("INACTIVE")){
            accountSocials.remove(0);
            run(account,tasks);
        }
        ChromeOptionsConfig options = new ChromeOptionsConfig();
        WebDriver driver = new ChromeDriver(options.setupOptionsChrome("Facebook", accountSocials.get(0).getProfile()));
        EstablishAccount.EstablishAccountFacebook(accountSocials.get(0), account);
        loginWebTTC(driver, account);
        doTasks(driver,tasks.get("likePageFacebook"),URL.URL_DO_TASK_EMOTION_POST,"emotion_post");
        doTasks(driver,tasks.get("emotionFacebook"),URL.URL_DO_TASK_LIKE_PAGE_CHEO,"like_page");
        doTasks(driver,tasks.get("followFacebook"),URL.URL_DO_TASK_SUB_CHEO,"sub_cheo");
        doTasks(driver,tasks.get("likePostFacebook"),URL.URL_DO_TASK_LIKE_CHEO_VIP,"like_post");
        driver.close();
        accountSocials.remove(0);
        run(account,tasks);
    }

    private void loginWebTTC(WebDriver driver, Account account) throws InterruptedException {
        driver.get(URL.URL_WEB_TTC);
        Thread.sleep(5000);
        driver.manage().addCookie(new Cookie("PHPSESSID", account.getSession().split("=")[1].trim()));
        driver.get(driver.getCurrentUrl());
        Thread.sleep(5000);
    }
    private void doTasks(WebDriver driver, int numberOfTasks, String urlTask, String type) throws InterruptedException {
        if(numberOfTasks == 0) return;
        int count = 0;
        driver.get(urlTask);
        Thread.sleep(5000);
        for (WebElement task : getWebElementsTasks(driver)) {
            WebElement doTaskButton = task.findElement(By.cssSelector(".form-group.text-center button"));
            String taskName = doTaskButton.getText().trim();
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
                case "emotion_post":
                    doTaskEmotionPost(driver,taskName);
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

    private void doTaskEmotionPost(WebDriver driver, String emotion) throws InterruptedException {
        Thread.sleep(5000);
        WebElement dialog = SeleniumHandler.getElementFromXpaths(new String[]{
                "//div[@class='x78zum5 xdt5ytf x1iyjqo2' and @role='dialog']"
        },driver);
        if(dialog == null ) {
            doTaskEmotionFirstPost(driver,emotion);
        }else{
            List<WebElement> emotionButtons = SeleniumHandler.getElementsFromsXpathsOnElement(new String[]{
                    "//div[@aria-label='Bày tỏ cảm xúc' and @role='button']","//div[@aria-label='Change Like reaction' and @role='button']"
            },dialog);
            if(emotionButtons != null){
                for(WebElement emotionButton : emotionButtons){
                    try{
                        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", emotionButton);
                        Actions actions = new Actions(driver);
                        actions.moveToElement(emotionButton).perform();
                        Thread.sleep(2000);
                        Objects.requireNonNull(findEmotionsButton(emotion, driver)).click();
                        break;
                    }catch (Exception ignored){

                    }
                }
            }

        }
    }

    private void doTaskEmotionFirstPost(WebDriver driver,String emotion) throws InterruptedException {
        WebElement emotionButton = null;
        Actions actions = new Actions(driver);
        try {
            emotionButton = SeleniumHandler.getElementFromXpaths(new String[]{
                    "//div[@aria-label='Bày tỏ cảm xúc' and @role='button']","//div[@aria-label='Change Like reaction' and @role='button']"
            },driver);
            if(emotionButton != null){
                actions.moveToElement(emotionButton).perform();
                Thread.sleep(5000);
                WebElement emotion_bt = findEmotionsButton(emotion, driver);
                if(emotion_bt != null) emotion_bt.click();
            }
        }catch (Exception ignored){
            if(emotionButton != null){
                ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", emotionButton);
                actions.moveToElement(emotionButton).perform();
                Thread.sleep(5000);
                WebElement emotion_bt = findEmotionsButton(emotion, driver);
                if(emotion_bt != null) emotion_bt.click();
            }
        }
    }

    private WebElement findEmotionsButton(String emotion, WebDriver driver){
        switch (emotion){
            case "HAHA":
                return SeleniumHandler.getElementFromXpaths(
                        new String[]{"//div[@aria-label='Haha' and @role='button']"}, driver);
            case "LOVE":
                return SeleniumHandler.getElementFromXpaths(
                        new String[]{
                                "//div[@aria-label='Love' and @role='button']",
                                "//div[@aria-label='Yêu thích' and @role='button']"},
                        driver);
            case "THƯƠNG THƯƠNG":
                return SeleniumHandler.getElementFromXpaths(
                        new String[]{
                                "//div[@aria-label='Thương thương' and @role='button']",
                                "//div[@aria-label='Care' and @role='button']"
                        }, driver);
            default: return null;
        }
    }

    private void checkAndAddCookieFacebook(WebDriver driver) throws InterruptedException {

        if(((Facebook)accountSocials.get(0)).getType().equals("PAGE")){
            if( driver.manage().getCookieNamed("i_user") == null
                    || driver.manage().getCookieNamed("i_user").getValue().isEmpty()
                    || !driver.manage().getCookieNamed("i_user").getValue().equals(((Facebook)accountSocials.get(0)).getFacebookID()) ){
                FacebookCookieHandler.addCookieFacebookToWeb(driver, accountSocials.get(0).getCookie());
                driver.get(driver.getCurrentUrl());
                checkCookieExpiredAndCreateNew(driver);
            }
        }else if (
                driver.manage().getCookieNamed("xs") == null
                        || driver.manage().getCookieNamed("c_user") == null
                        || driver.manage().getCookieNamed("xs").getValue().isEmpty()
                        || driver.manage().getCookieNamed("c_user").getValue().isEmpty()
        ) {
            FacebookCookieHandler.addCookieFacebookToWeb(driver, accountSocials.get(0).getCookie());
            driver.get(driver.getCurrentUrl());
            checkCookieExpiredAndCreateNew(driver);

        }

    }

    private void checkCookieExpiredAndCreateNew(WebDriver driver ) throws InterruptedException {
        if(driver.manage().getCookieNamed("xs") != null &&  !driver.manage().getCookieNamed("xs").getValue().isEmpty()) return;

        WebElement login = SeleniumHandler.getElementFromXpaths(new String[]{
                "//div[@aria-label='Accessible login button' and @role='button']"
        },driver);
        login.click();
        Thread.sleep(5000);
        WebElement email = SeleniumHandler.getElementFromXpaths(new String[]{
                "//*[@id='email']"
        },driver);
        email.clear();
        email.sendKeys(accountSocials.get(0).getUsername().trim());
        Thread.sleep(5000);
        WebElement password = SeleniumHandler.getElementFromXpaths(new String[]{
                "//*[@id='pass']"
        },driver);
        password.clear();
        password.sendKeys(accountSocials.get(0).getPassword().trim());
        Thread.sleep(5000);
        WebElement loginButton = SeleniumHandler.getElementFromXpaths( new String[]{
                "//*[@id='loginbutton']"
        },driver);
        loginButton.click();
        Thread.sleep(5000);
        accountSocials.get(0).setCookie(driver.manage().getCookies().toString());
        String encryptAccount = Mapper.mapAccountFacebookToString(accountSocials.get(0));
        EncryptRSA.encryption(encryptAccount,
                new File(CurrentDirectory.currentDirectoryFacebook + ((Facebook)accountSocials.get(0)).getFacebookID() + ".dat")
        );
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
