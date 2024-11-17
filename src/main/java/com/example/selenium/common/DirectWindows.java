package com.example.selenium.common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class DirectWindows {
    public static void openNewTab(WebDriver driver) {
        int numberCurrentTabs = driver.getWindowHandles().size();
        ((JavascriptExecutor) driver).executeScript("window.open()");
        driver.switchTo().window(driver.getWindowHandles().toArray(new String[numberCurrentTabs + 1])[numberCurrentTabs]);
    }

    public static void closeAllTab(WebDriver driver){
        driver.getWindowHandles().forEach(s -> driver.switchTo().window(s).close());
    }
}
