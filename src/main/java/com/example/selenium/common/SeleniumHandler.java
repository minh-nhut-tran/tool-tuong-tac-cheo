package com.example.selenium.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SeleniumHandler {

    public static WebElement getElementFromXpaths(String[] xpaths, WebDriver driver) {
        WebElement result = null;
        for (String xpath : xpaths) {
            try {
                result = driver.findElement(By.xpath(xpath));
            } catch (Exception ignored) {
            }
        }
        return result;
    }

    public static WebElement getElementFromXpathsOnElement(String[] xpaths, WebElement element){
        WebElement result = null;
        for (String xpath : xpaths) {
            try {
                result = element.findElement(By.xpath(xpath));
                break;
            } catch (Exception ignored) {
            }
        }
        return result;
    }

    public static List<WebElement> getElementsFromsXpathsOnElement(String[] xpaths, WebElement element){
        List<WebElement> result = new ArrayList<>();
        for (String xpath : xpaths) {
            try {
                result = element.findElements(By.xpath(xpath));
                break;
            } catch (Exception ignored) {
                result.add(getElementFromXpathsOnElement(xpaths,element));
            }
        }
        return result;
    }

}
