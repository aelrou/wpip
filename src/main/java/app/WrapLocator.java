package app;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WrapLocator {

    public static WebElement waitFind(WebDriver driver, Loc locatorType, String locator, Integer seconds) throws CustomExceptElementWait, CustomExceptLocatorType {
        if (seconds == null) {
            seconds = 10;
        }

        System.out.println(Go.indent +"Look for "+ locatorType +" \""+ locator +"\" for "+ seconds +" sec");

        int loopTime = 200;
        int maxLoops = (1000/loopTime) * seconds;
        int loop = 1;

        WebElement element = null;
        WrapFindElement find = new WrapFindElement();

        while (true) {
            try {
                element = find.with(driver, locatorType, locator);
                if (element == null) {
                    if (loop > maxLoops) {
                        throw new CustomExceptElementWait("Unable to create WebElement with "+ locatorType +" \""+ locator +"\" after "+ seconds +" seconds");
                    }
                    loop +=1;
                    Thread.sleep(loopTime);
                }
                break;
            } catch (CustomExceptElementCount ceec){
                if (loop > maxLoops) {
                    ceec.printStackTrace();
                    break;
                }
                loop +=1;
                try {
                    Thread.sleep(loopTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // This is here because driver.findElements is throwing an exception when no elements are found when it should just return an empty list
            } catch (NoSuchElementException nsee){
                if (loop > maxLoops) {
                    nsee.printStackTrace();
                    break;
                }
                loop +=1;
                try {
                    Thread.sleep(loopTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        return element;
    }

    public static int waitFindCount(WebDriver driver, Loc locatorType, String locator, Integer seconds) throws CustomExceptElementWait, CustomExceptLocatorType {
        if (seconds == null) {
            seconds = 10;
        }

        System.out.println(Go.indent +"Count all "+ locatorType +" \""+ locator +"\" for "+ seconds +" sec");

        int loopTime = 200;
        int maxLoops = (1000/loopTime) * seconds;
        int loop = 1;

        List<WebElement> elements = null;
        WrapFindElements find = new WrapFindElements();

        while (true) {
            try {
                elements = find.with(driver, locatorType, locator);
                if (elements == null) {
                    if (loop > maxLoops) {
                        throw new CustomExceptElementWait("Unable to find WebElements with "+ locatorType +" \""+ locator +"\" after "+ seconds +" seconds");
                    }
                    loop +=1;
                    Thread.sleep(loopTime);
                }
                break;
            } catch (CustomExceptElementCount ceec){
                if (loop > maxLoops) {
                    ceec.printStackTrace();
                    break;
                }
                loop +=1;
                try {
                    Thread.sleep(loopTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // This is here because driver.findElements is throwing an exception when no elements are found when it should just return an empty list
            } catch (NoSuchElementException nsee){
                if (loop > maxLoops) {
                    nsee.printStackTrace();
                    break;
                }
                loop +=1;
                try {
                    Thread.sleep(loopTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        return elements.size();
    }

    public static WebElement waitDisplay(WebDriver driver, Loc locatorType, String locator, Integer seconds) throws CustomExceptElementWait, CustomExceptLocatorType {
        if (seconds == null) {
            seconds = 10;
        }

        System.out.println(Go.indent +"Look for visible "+ locatorType +" \""+ locator +"\" for "+ seconds +" sec");

        int loopTime = 200;
        int maxLoops = (1000/loopTime) * seconds;
        int loop = 1;

        WebElement element = null;
        WrapFindElement find = new WrapFindElement();

        while (true) {
            try {
                element = find.with(driver, locatorType, locator);
                if (element == null) {
                    if (loop > maxLoops) {
                        throw new CustomExceptElementWait("Unable to create WebElement with "+ locatorType +" \""+ locator +"\" after "+ seconds +" seconds");
                    }
                    loop +=1;
                    Thread.sleep(loopTime);
                }
                if (!element.isDisplayed()) {
                    if (loop > maxLoops) {
                        throw new CustomExceptElementWait("WebElement of "+ locatorType +" \""+ locator +"\" it is not visible after "+ seconds +" seconds");
                    }
                    loop +=1;
                    Thread.sleep(loopTime);
                }
                break;
            } catch (CustomExceptElementCount ceec){
                if (loop > maxLoops) {
                    ceec.printStackTrace();
                    break;
                }
                loop +=1;
                try {
                    Thread.sleep(loopTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // This is here because driver.findElements is throwing an exception when no elements are found when it should just return an empty list
            } catch (NoSuchElementException nsee){
                if (loop > maxLoops) {
                    nsee.printStackTrace();
                    break;
                }
                loop +=1;
                try {
                    Thread.sleep(loopTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        return element;
    }

    public static WebElement waitEnable(WebDriver driver, Loc locatorType, String locator, Integer seconds) throws CustomExceptElementWait, CustomExceptLocatorType {
        if (seconds == null) {
            seconds = 10;
        }

        System.out.println(Go.indent +"Look for enabled "+ locatorType +" \""+ locator +"\" for "+ seconds +" sec");

        int loopTime = 200;
        int maxLoops = (1000/loopTime) * seconds;
        int loop = 1;

        WebElement element = null;
        WrapFindElement find = new WrapFindElement();

        while (true) {
            try {
                element = find.with(driver, locatorType, locator);
                if (element == null) {
                    if (loop > maxLoops) {
                        throw new CustomExceptElementWait("Unable to create WebElement with "+ locatorType +" \""+ locator +"\" after "+ seconds +" seconds");
                    }
                    loop +=1;
                    Thread.sleep(loopTime);
                }
                if (!element.isDisplayed()) {
                    if (loop > maxLoops) {
                        throw new CustomExceptElementWait("WebElement of "+ locatorType +" \""+ locator +"\" it is not visible after "+ seconds +" seconds");
                    }
                    loop +=1;
                    Thread.sleep(loopTime);
                }
                if (!element.isEnabled()) {
                    if (loop > maxLoops) {
                        throw new CustomExceptElementWait("WebElement of "+ locatorType +" \""+ locator +"\" it is not enabled after "+ seconds +" seconds");
                    }
                    loop +=1;
                    Thread.sleep(loopTime);
                }
                break;
            } catch (CustomExceptElementCount ceec){
                if (loop > maxLoops) {
                    ceec.printStackTrace();
                    break;
                }
                loop +=1;
                try {
                    Thread.sleep(loopTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // This is here because driver.findElements is throwing an exception when no elements are found when it should just return an empty list
            } catch (NoSuchElementException nsee){
                if (loop > maxLoops) {
                    nsee.printStackTrace();
                    break;
                }
                loop +=1;
                try {
                    Thread.sleep(loopTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        return element;
    }

    public static WebElement waitClickable(WebDriver driver, Loc locatorType, String locator, Integer seconds) throws CustomExceptElementWait, CustomExceptLocatorType {
        if (seconds == null) {
            seconds = 10;
        }

        System.out.println(Go.indent +"Look for clickable "+ locatorType +" \""+ locator +"\" for "+ seconds +" sec");

        int loopTime = 200;
        int maxLoops = (1000 / loopTime) * seconds;
        int loop = 1;

        WebElement element = null;
        WrapFindElement find = new WrapFindElement();

        while (true) {
            try {
                element = find.with(driver, locatorType, locator);
                if (element == null) {
                    if (loop > maxLoops) {
                        throw new CustomExceptElementWait("Unable to create WebElement with "+ locatorType +" \""+ locator +"\" after "+ seconds +" seconds");
                    }
                    loop += 1;
                    Thread.sleep(loopTime);
                }
                break;
            } catch (CustomExceptElementCount ceec){
                if (loop > maxLoops) {
                    ceec.printStackTrace();
                    break;
                }
                loop +=1;
                try {
                    Thread.sleep(loopTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // This is here because driver.findElements is throwing an exception when no elements are found when it should just return an empty list
            } catch (NoSuchElementException nsee){
                if (loop > maxLoops) {
                    nsee.printStackTrace();
                    break;
                }
                loop +=1;
                try {
                    Thread.sleep(loopTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        WebElement elementClickable;
        WebDriverWait wait = new WebDriverWait(driver, seconds);

        try {
            elementClickable = wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException te) {
            throw new CustomExceptElementWait("WebElement of "+ locatorType +" \""+ locator +"\" it is not clickable after "+ seconds +" seconds");
        }

        return elementClickable;
    }
}
