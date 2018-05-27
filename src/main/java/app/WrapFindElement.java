package app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class WrapFindElement {
    WebElement with(WebDriver driver, Loc locatorType, String locator) throws CustomExceptLocatorType, CustomExceptElementCount {
        WebElement element;
        List<WebElement> elementList;

        switch (locatorType) {
            case CLASSNAME:
                elementList = driver.findElements(By.className(locator));
                if (elementList.size() == 1) {
                    element = driver.findElement(By.className(locator));
                } else {
                    throw new CustomExceptElementCount("Unable to create WebElement with "+ locatorType +" \""+ locator +"\" because there are "+ elementList.size());
                }
                break;
            case CSSSELECTOR:
                elementList = driver.findElements(By.cssSelector(locator));
                if (elementList.size() == 1) {
                    element = driver.findElement(By.cssSelector(locator));
                } else {
                    throw new CustomExceptElementCount("Unable to create WebElement with "+ locatorType +" \""+ locator +"\" because there are "+ elementList.size());
                }
                break;
            case ID:
                elementList = driver.findElements(By.id(locator));
                if (elementList.size() == 1) {
                    element = driver.findElement(By.id(locator));
                } else {
                    throw new CustomExceptElementCount("Unable to create WebElement with "+ locatorType +" \""+ locator +"\" because there are "+ elementList.size());
                }
                break;
            case LINKTEXT:
                elementList = driver.findElements(By.linkText(locator));
                if (elementList.size() == 1) {
                    element = driver.findElement(By.linkText(locator));
                } else {
                    throw new CustomExceptElementCount("Unable to create WebElement with "+ locatorType +" \""+ locator +"\" because there are "+ elementList.size());
                }
                break;
            case NAME:
                elementList = driver.findElements(By.name(locator));
                if (elementList.size() == 1) {
                    element = driver.findElement(By.name(locator));
                } else {
                    throw new CustomExceptElementCount("Unable to create WebElement with "+ locatorType +" \""+ locator +"\" because there are "+ elementList.size());
                }
                break;
            case PARTIALLINKTEXT:
                elementList = driver.findElements(By.partialLinkText(locator));
                if (elementList.size() == 1) {
                    element = driver.findElement(By.partialLinkText(locator));
                } else {
                    throw new CustomExceptElementCount("Unable to create WebElement with "+ locatorType +" \""+ locator +"\" because there are "+ elementList.size());
                }
                break;
            case TAGNAME:
                elementList = driver.findElements(By.tagName(locator));
                if (elementList.size() == 1) {
                    element = driver.findElement(By.tagName(locator));
                } else {
                    throw new CustomExceptElementCount("Unable to create WebElement with "+ locatorType +" \""+ locator +"\" because there are "+ elementList.size());
                }
                break;
            case XPATH:
                elementList = driver.findElements(By.xpath(locator));
                if (elementList.size() == 1) {
                    element = driver.findElement(By.xpath(locator));
                } else {
                    throw new CustomExceptElementCount("Unable to create WebElement with "+ locatorType +" \""+ locator +"\" because there are "+ elementList.size());
                }
                break;
            default:
                throw new CustomExceptLocatorType("Unknown locatorType "+ locatorType);
        }
        return element;
    }
}
