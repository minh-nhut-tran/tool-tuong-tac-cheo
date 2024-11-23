package com.example.selenium.common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class DirectWindows {
    public static void openNewTab(WebDriver driver) {
        int numberCurrentTabs = driver.getWindowHandles().size();
        ((JavascriptExecutor) driver).executeScript("window.open()");
        driver.switchTo().window(driver.getWindowHandles().toArray(new String[numberCurrentTabs + 1])[numberCurrentTabs]);
    }

    public static void closeAllTab(WebDriver driver){
        driver.getWindowHandles().forEach(s -> driver.switchTo().window(s).close());
    }

    public static void closeTabAndSwitchToTabFirst(WebDriver driver){
        Set<String> handles = driver.getWindowHandles();
        String[] windowHandles = handles.toArray(new String[handles.size()]);
        driver.close();
        driver.switchTo().window(windowHandles[0]);
    }

    public static void switchScreenToNextWindow(WebDriver driver){
        Set<String> handles = driver.getWindowHandles();
        String[] windowHandles = handles.toArray(new String[handles.size()]);
        driver.switchTo().window(windowHandles[1]);
    }
}
