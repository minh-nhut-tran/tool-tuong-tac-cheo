package com.example.selenium.selenium;

import com.example.selenium.common.DirectWindows;
import com.example.selenium.common.SeleniumHandler;
import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.pojo.Tiktok;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class TiktokSelenium {

    public boolean loginTiktokAccountOnGoogle(WebDriver driver, AccountSocial accountSocial) throws InterruptedException {
        driver.get("https://www.tiktok.com/login");
        Thread.sleep(5000);
        if(Objects.equals(driver.getTitle(), "Log in | TikTok")){
            return loginTiktokByEmailAndPassword(driver,accountSocial);
        }
        return false;
    }

    private boolean loginTiktokByEmailAndPassword(WebDriver driver, AccountSocial accountSocial) throws InterruptedException {
        try{
            WebElement loginByNumberPhoneAndEmail = SeleniumHandler.getElementFromXpaths(new String[]{
                    "//div[text()='Use phone / email / username']/ancestor::div[@data-e2e='channel-item']/parent::*",
                    "//div[text()='Số điện thoại/email/tên người dùng']/ancestor::div[@data-e2e='channel-item']/parent::*",
            },driver);
            loginByNumberPhoneAndEmail.click();
            Thread.sleep(2000);

            WebElement loginByEmail = SeleniumHandler.getElementFromXpaths(new String[]{
                    "//a[text()='Log in with email or username']"
            },driver);
            loginByEmail.click();
            Thread.sleep(2000);

            WebElement username = SeleniumHandler.getElementFromXpaths(new String[]{
                    "//input[@name='username']"
            },driver);
            username.sendKeys(accountSocial.getUsername());
            WebElement password = SeleniumHandler.getElementFromXpaths(new String[]{
                    "//input[@autocomplete='new-password']"
            },driver);
            password.sendKeys(accountSocial.getPassword());
            Thread.sleep(5000);
            WebElement login = SeleniumHandler.getElementFromXpaths(new String[]{
                    "//button[@data-e2e='login-button' and @type='submit']",
            },driver);
            login.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
            wait.until(driver_web
                    -> driver_web.manage().getCookieNamed("sessionid") != null &&
                       !Objects.requireNonNull(driver_web.manage().getCookieNamed("sessionid")).getValue().isEmpty()
            );
            assignAccount(driver,accountSocial);
            DirectWindows.closeAllTab(driver);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    public void assignAccount(WebDriver driver, AccountSocial accountSocial) throws InterruptedException {
        Thread.sleep(5000);
        DirectWindows.openNewTab(driver);
        driver.get("https://www.tiktok.com/profile");
        Thread.sleep(5000);
        String tiktokID = SeleniumHandler.getElementFromXpaths(new String[]{
                "//h1[@data-e2e='user-title']"
        },driver).getText().trim();
        String name = SeleniumHandler.getElementFromXpaths(new String[]{
                "//h2[@data-e2e='user-subtitle']"
        },driver).getText().trim();
        String sessionid = Objects.requireNonNull(driver.manage().getCookieNamed("sessionid")).getValue();
        accountSocial.setCookie(sessionid);
        ((Tiktok)accountSocial).setTiktokID(tiktokID);
        ((Tiktok)accountSocial).setName(name);
        accountSocial.setCookie(sessionid);
    }


}
