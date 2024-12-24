package com.example.selenium.selenium;

import com.example.selenium.common.DirectWindows;
import com.example.selenium.common.SeleniumHandler;
import com.example.selenium.common.StringHandler;
import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.pojo.Youtube;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class YoutubeSelenium {
    public boolean loginYoutubeAccount(WebDriver driver, AccountSocial account) throws InterruptedException {
        driver.get("https://www.youtube.com/account");
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        wait.until(driver_web
                -> driver_web.findElement(By.id("introduction-text")));
        assignAccount(driver, account);
        return true;
    }

    public void assignAccount(WebDriver driver, AccountSocial account) throws InterruptedException {
        driver.get("https://www.youtube.com/account");
        Thread.sleep(5000);
        String name = SeleniumHandler.getElementFromXpaths(new String[]{
                "//div[@id='name' and @class='style-scope ytd-channel-options-renderer'] "
        },driver).getText().trim();
        driver.get("view-source:https://www.youtube.com/account_advanced");
        Thread.sleep(5000);
        String channelID = getChannelIDYoutube(driver).startsWith("UC") ?
                getChannelIDYoutube(driver) :
                "UC" + getChannelIDYoutube(driver);
        ((Youtube)account).setName(name);
        ((Youtube)account).setChanelID(channelID);
        DirectWindows.closeAllTab(driver);
    }

    public String getChannelIDYoutube(WebDriver driver) throws InterruptedException {
        driver.get("view-source:https://www.youtube.com/account_advanced");
        Thread.sleep(5000);
        String source = driver.getPageSource();
        return StringHandler.getValueFromKeyInString(Objects.requireNonNull(source), "shortUrl").replaceAll("\"","").replaceAll(":","");
    }
}
