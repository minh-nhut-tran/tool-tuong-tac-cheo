package com.example.selenium.selenium;

import com.example.selenium.common.DirectWindows;
import com.example.selenium.common.SeleniumHandler;
import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.pojo.Facebook;
import org.openqa.selenium.*;

public class FacebookSelenium {

    public boolean loginAutomationFacebookOnGoogle(WebDriver driver, AccountSocial account) throws InterruptedException {
        driver.get("https://www.facebook.com/login.php/");
        Thread.sleep(5000); //5s
        String title = driver.getTitle();
        if (title.equals("Log in to Facebook")) {
            return loginFacebookAutomationByUserPassword(driver, account);
        }
        return false;
    }


    private boolean loginFacebookAutomationByUserPassword(WebDriver driver, AccountSocial account) throws InterruptedException {
        WebElement username = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        username.sendKeys(account.getUsername());
        Thread.sleep(2000);
        WebElement password = driver.findElement(By.xpath("//*[@id=\"pass\"]"));
        password.sendKeys(account.getPassword());
        Thread.sleep(2000);
        WebElement login = driver.findElement(By.xpath("//*[@id=\"loginbutton\"]"));
        login.click();
        Thread.sleep(6000);
        if (driver.getCurrentUrl().equals("https://www.facebook.com/")) {
            assignAccount(driver, account);
            return true;
        }
        return false;
    }


    private void assignAccount(WebDriver driver, AccountSocial accountSocial) throws InterruptedException {
        DirectWindows.openNewTab(driver);
        String userId = getUserIdFromViewSource(driver);
        driver.get("https://www.facebook.com/" + userId);
        Thread.sleep(8000);
        if (((Facebook) accountSocial).getType().equals("PAGE")) {
            if (!choosePage(driver, accountSocial)) return;
            userId = getUserIdFromViewSource(driver);
            driver.get("https://www.facebook.com/" + userId);
            Thread.sleep(8000);
        }
        String name = driver.
                findElement(By.cssSelector(".x78zum5.xdt5ytf.x1wsgfga.x9otpla")).
                findElement(By.tagName("h1")).getText();
        String cookie = driver.manage().getCookies().toString();
        ((Facebook) accountSocial).setFacebookID(userId);
        ((Facebook) accountSocial).setName(name.trim());
        accountSocial.setCookie(cookie);
    }

    /*
        thực hiện lấy idUser từ view source
     */
    private String getUserIdFromViewSource(WebDriver driver) {

        driver.get("view-source:https://www.facebook.com/");
        String pageSource = driver.getPageSource();
        int index = pageSource.toLowerCase().indexOf("userid");
        StringBuilder userId = new StringBuilder();
        while (pageSource.charAt(index + 8) != ',') {
            userId.append(pageSource.charAt(index + 8));
            index++;
        }
        return userId.toString().trim();
    }


    /*
        thực hiện hành động đăng nhập vào page
     */
    private boolean choosePage(WebDriver driver, AccountSocial accountSocial) throws InterruptedException {
        String pageName = ((Facebook) accountSocial).getName();
        if (pageName == null || pageName.isEmpty()) return false;
        WebElement profile = SeleniumHandler.getElementFromXpaths(new String[]{
                "//div[@aria-label='Your profile' and @role='button']",
                "//div[@aria-label='Trang cá nhân của bạn' and @role='button']"
        }, driver);
        profile.click();
        Thread.sleep(3000);
        WebElement pageProfile;
        if (
                (pageProfile = SeleniumHandler.getElementFromXpaths(new String[]{
                        "//div[@aria-label='Chuyển sang " + pageName.trim() + "' and @role='button']",
                        "//div[@aria-label='Switch to " + pageName.trim() + "' and @role='button']"
                }, driver)) == null
        ) pageProfile = handleExceptionChoosePage(driver, accountSocial);

        if (pageProfile != null) pageProfile.click();
        Thread.sleep(5000);
        return true;
    }


    /*
        dùng để xử lý lỗi khi chose page mà page đó nằm ở
        mục xem tất cả của facebook
     */
    private WebElement handleExceptionChoosePage(WebDriver driver, AccountSocial accountSocial) throws InterruptedException {
        String namePage = ((Facebook) accountSocial).getName();
        if (namePage == null || namePage.isEmpty()) return null;

        WebElement seeAll = SeleniumHandler.getElementFromXpaths(new String[]{
                "//div[@aria-label='Xem tất cả trang cá nhân' and @role='button']",
                "//div[@aria-label='See all profiles' and @role='button']"
        }, driver);
        seeAll.click();

        Thread.sleep(5000);
        return SeleniumHandler.getElementFromXpaths(new String[]{
                "//div[@role='listitem']//span[text()='" + namePage.trim() + "']/ancestor::div[@role='listitem']"
        }, driver);
    }


    /*
       tìm kiếm 1 element theo nhiều xpath
    */


}
