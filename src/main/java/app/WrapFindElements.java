package app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class WrapFindElements {
    public static List<WebElement> with(WebDriver driver, Loc locatorType, String locator) throws CustomExceptLocatorType, CustomExceptElementCount {
        List<WebElement> elementList;

        switch (locatorType) {
            case CLASSNAME:
                elementList = driver.findElements(By.className(locator));

                break;
            case CSSSELECTOR:
                elementList = driver.findElements(By.cssSelector(locator));

                break;
            case ID:
                elementList = driver.findElements(By.id(locator));

                break;
            case LINKTEXT:
                elementList = driver.findElements(By.linkText(locator));

                break;
            case NAME:
                elementList = driver.findElements(By.name(locator));

                break;
            case PARTIALLINKTEXT:
                elementList = driver.findElements(By.partialLinkText(locator));

                break;
            case TAGNAME:
                elementList = driver.findElements(By.tagName(locator));

                break;
            case XPATH:
                elementList = driver.findElements(By.xpath(locator));

                break;
            default:
                throw new CustomExceptLocatorType("Unknown locatorType "+ locatorType);
        }
        return elementList;
    }

    public static int count(WebDriver driver, Loc locatorType, String locator) throws CustomExceptLocatorType, CustomExceptElementCount {
        return with(driver, locatorType, locator).size();
    }
}