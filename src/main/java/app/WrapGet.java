package app;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WrapGet {

    public static WebElement waitPage(WebDriver driver, String url, Loc locatorType, String locator, Integer seconds) throws CustomExceptLocatorType, CustomExceptPageTimeout {
        if (seconds == null) {
            seconds = 10;
        }

        driver.get("about:blank");

        JavascriptExecutor jsdriver = (JavascriptExecutor) driver;
        jsdriver.executeScript("window.location.href='"+ url +"'");
        System.out.println("Browse URL \""+ url +"\"");

        WebElement element = null;
        try {
            element = WrapLocator.waitFind(driver, locatorType, locator, seconds);
        } catch (CustomExceptElementWait ceew) {
//            ceew.printStackTrace();
        }

        if (element == null) {
            throw new CustomExceptPageTimeout("Unable to find "+ locatorType +" \""+ locator +"\" after "+ seconds +" sec at url \""+ url +"\"");
        }

        return element;
    }
}
