package app;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;
import com.machinepublishers.jbrowserdriver.UserAgent;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

public class Go {

    public static final String indent ="    ";

    public static void main(String[] args) throws WorkDirException, WrapJsonException {

        String workDir = new WorkDir(args,"app.jar").dir();

        File localBinConfigFile = new WrapJson(workDir).findLocalBinConfig("LocalBinConfig.json");
        LocalBinConfig localBinConfig = new WrapJson(workDir).readLocalBinConfig(localBinConfigFile);

        File localRunConfigFile = new WrapJson(workDir).findLocalRunConfig("LocalRunConfig.json");
        LocalRunConfig localRunConfig = new WrapJson(workDir).readLocalRunConfig(localRunConfigFile);

        String logFile = Log.find(localRunConfig.LOGS_PATH, localRunConfig.LOG_NAME);

        File localStageConfigFile = new WrapJson(workDir).findLocalStageConfig("LocalStageConfig.json");
        LocalStageConfig localStageConfig = new WrapJson(workDir).readLocalStageConfig(localStageConfigFile);

        File localStatusCacheFile = new WrapJson(workDir).findLocalStatusCache("LocalStatusCache.json");
        LocalStatusCache localStatusCache = new WrapJson(workDir).readLocalStatusCache(localStatusCacheFile);

        String executable;
        ArrayList<Integer> exceptPidList = new ArrayList();
        WebDriver driver = null;

        switch (localRunConfig.RUNTIME_DRIVER) {
            case "ChromeDriver":
                exceptPidList = WinTask.pidList(localBinConfig.TASKLIST,"chrome.exe");
                executable = workDir +"\\"+ localBinConfig.CHROME_DRIVER;
                System.setProperty("webdriver.chrome.driver", executable);
                ChromeOptions chromeVisibleOptions = new ChromeOptions();
                chromeVisibleOptions.addArguments("--user-data-dir="+ localBinConfig.CHROME_PROFILE);
                driver = new ChromeDriver(chromeVisibleOptions);
                break;
            case "ChromeHeadlessDriver":
                exceptPidList = WinTask.pidList(localBinConfig.TASKLIST,"chrome.exe");
                executable = workDir +"\\"+ localBinConfig.CHROME_DRIVER;
                System.setProperty("webdriver.chrome.driver", executable);
                ChromeOptions chromeHeadlessOptions = new ChromeOptions();
                chromeHeadlessOptions.addArguments("--user-data-dir="+ localBinConfig.CHROME_PROFILE);
                chromeHeadlessOptions.addArguments("--headless");
                chromeHeadlessOptions.addArguments("--disable-gpu");
                driver = new ChromeDriver(chromeHeadlessOptions);
                break;
            case "EdgeDriver":
                exceptPidList = WinTask.pidList(localBinConfig.TASKLIST,"microsoftedg*.exe");
                executable = workDir +"\\"+ localBinConfig.EDGE_DRIVER;
                System.setProperty("webdriver.edge.driver", executable);
                driver = new EdgeDriver();
                break;
            case "FirefoxDriver":
                exceptPidList = WinTask.pidList(localBinConfig.TASKLIST,"firefox.exe");
                executable = workDir +"\\"+ localBinConfig.FIREFOX_DRIVER;
                System.setProperty("webdriver.gecko.driver", executable);
                FirefoxOptions firefoxVisibleOptions = new FirefoxOptions();
                firefoxVisibleOptions.setProfile(new FirefoxProfile(new File(localBinConfig.FIREFOX_PROFILE)));
                driver = new FirefoxDriver(firefoxVisibleOptions);
                break;
            case "FirefoxHeadlessDriver":
                exceptPidList = WinTask.pidList(localBinConfig.TASKLIST,"firefox.exe");
                executable = workDir +"\\"+ localBinConfig.FIREFOX_DRIVER;
                System.setProperty("webdriver.gecko.driver", executable);
                FirefoxOptions firefoxHeadlessOptions = new FirefoxOptions();
                firefoxHeadlessOptions.setProfile(new FirefoxProfile(new File(localBinConfig.FIREFOX_PROFILE)));
                firefoxHeadlessOptions.addArguments("--headless");
                driver = new FirefoxDriver(firefoxHeadlessOptions);
                break;
            case "HtmlUnitDriver":
                DesiredCapabilities htmlUnitCapabilities = new DesiredCapabilities();
                htmlUnitCapabilities.setBrowserName("htmlunit");
                htmlUnitCapabilities.setJavascriptEnabled(true);
                htmlUnitCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                driver = new HtmlUnitDriver(htmlUnitCapabilities);
                break;
            case "InternetExplorerDriver":
                // Ensure that "Enable Protected Mode" in Internet Options is either checked or unchecked the same for all 4 security zones.
                // Ensure that "Enable Enhanced Protected Mode" in Advanced Internet Options is unchecked.
                // Ensure that "Enable 64-bit processes for Enhanced Protected Mode" is checked if using 64-bit IEDriverServer.exe, or unchecked if using 32-bit IEDriverServer.exe
                // Ensure that the FEATURE_BFCACHE and MaxUserPort registry keys have been installed.
                // Ensure that the path to IEDriverServer.exe is in the PATH variable.
                // Ensure that there is an exception in the Windows Firewall for IEDriverServer.exe
                exceptPidList = WinTask.pidList(localBinConfig.TASKLIST,"iexplore.exe");
                executable = workDir +"\\"+ localBinConfig.IE_DRIVER;
                System.setProperty("webdriver.ie.driver", executable);
                driver = new InternetExplorerDriver();
                break;
            case "JBrowserDriver":
                driver = new JBrowserDriver(Settings.builder()
                        .timezone(Timezone.AMERICA_DENVER)
                        .userAgent(UserAgent.CHROME)
                        .ssl("trustanything")
                        .hostnameVerification(false)
                        .build()
                );
                break;
            case "PhantomJSDriver":
                exceptPidList = WinTask.pidList(localBinConfig.TASKLIST,"phantomjs.exe");
                executable = workDir +"\\"+ localBinConfig.PHANTOMJS_DRIVER;
                DesiredCapabilities phantomJsCapabilities = new DesiredCapabilities();
                phantomJsCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, executable);
                phantomJsCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--ignore-ssl-errors=true", "--ssl-protocol=any", "--web-security=false",});
                driver = new PhantomJSDriver(phantomJsCapabilities);
                break;
            case "OperaDriver":
                exceptPidList = WinTask.pidList(localBinConfig.TASKLIST,"opera.exe");
                executable = workDir +"\\"+ localBinConfig.OPERA_DRIVER;
                OperaOptions operaOptions = new OperaOptions();
                operaOptions.setBinary(executable);
                System.setProperty("webdriver.opera.driver", executable);
                driver = new OperaDriver(operaOptions);
                break;
            case "SafariDriver":
                executable = workDir +"\\"+ localBinConfig.SAFARI_DRIVER;
                System.setProperty("webdriver.safari.driver", executable);
                driver = new SafariDriver();
                break;
            default:
                System.out.println("Unknown RUNTIME_DRIVER : "+ localRunConfig.RUNTIME_DRIVER);
                driver.quit();
                terminateWebDriver(localRunConfig, localBinConfig.TASKLIST, localBinConfig.TASKKILL, exceptPidList);
                System.exit(1);
                break;
        }

        Dimension window = new Dimension(localRunConfig.BROWSER_WIDTH, localRunConfig.BROWSER_HEIGHT);
        // This cannot be relied upon in headless mode. Specify the screen size in a config instead.
        // java.awt.Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        driver.manage().window().setSize(window);
        Point position = new Point(localRunConfig.SCREEN_WIDTH - window.width, -40);
        driver.manage().window().setPosition(position);

        String ipCheckUrl = null;
        String ipCheckXpath = null;
        String ipNotifyUrl = null;
        String ipNotifyFormXpath = null;
        String ipNotifySubmitXpath = null;
        String ipNotifyConfirmXpath = null;

        switch (localRunConfig.RUNTIME_STAGE) {
            case "Development":
                ipCheckUrl = localStageConfig.IPCHECK_URL;
                ipCheckXpath = localStageConfig.IPCHECK_XPATH_LOCATOR;
                ipNotifyUrl = localStageConfig.IPNOTIFY_URL;
                ipNotifyFormXpath = localStageConfig.IPNOTIFY_FORM_XPATH_LOCATOR;
                ipNotifySubmitXpath = localStageConfig.IPNOTIFY_SUBMIT_XPATH_LOCATOR;
                ipNotifyConfirmXpath = localStageConfig.IPNOTIFY_CONFIRM_XPATH_LOCATOR;
                break;
            case "Test":
                ipCheckUrl = "";
                ipCheckXpath = "";
                ipNotifyUrl = "";
                ipNotifyFormXpath = "";
                ipNotifySubmitXpath = "";
                ipNotifyConfirmXpath = "";
                break;
            case "PRODUCTION":
                ipCheckUrl = "";
                ipCheckXpath = "";
                ipNotifyUrl = "";
                ipNotifyFormXpath = "";
                ipNotifySubmitXpath = "";
                ipNotifyConfirmXpath = "";
                break;
            default:
                System.out.println("Unknown RUNTIME_STAGE : "+ localRunConfig.RUNTIME_STAGE);
                driver.quit();
                terminateWebDriver(localRunConfig, localBinConfig.TASKLIST, localBinConfig.TASKKILL, exceptPidList);
                System.exit(1);
                break;
        }

        LocalDateTime startClock = LocalDateTime.now();
        String currentIp = "";

        try {

            WrapGet.waitPage(driver, ipCheckUrl, Loc.XPATH, ipCheckXpath, 10);

            WebElement ipReport;
            ipReport = WrapLocator.waitDisplay(driver, Loc.XPATH, ipCheckXpath, 10);
            currentIp = ipReport.getText();

            WrapGet.waitPage(driver, ipNotifyUrl, Loc.XPATH, ipNotifyFormXpath, 10);

        } catch (CustomExceptLocatorType | CustomExceptPageTimeout | CustomExceptElementWait e) {
            Log.save(logFile, "Failed to check public IP");
            e.printStackTrace();
            driver.quit();
            terminateWebDriver(localRunConfig, localBinConfig.TASKLIST, localBinConfig.TASKKILL, exceptPidList);
            System.exit(1);
        }

        if (currentIp.equals(localStatusCache.IP_CACHE)){

            Log.save(logFile, "No change in reported IP "+ currentIp);

        } else {

            Log.save(logFile, "The reported IP "+ currentIp +" is different from the cached IP "+ localStatusCache.IP_CACHE);

            try {

                WebElement notifyForm;
                notifyForm = WrapLocator.waitClickable(driver, Loc.XPATH, ipNotifyFormXpath, 10);
                Actions actionForm = new Actions(driver);
                actionForm.moveToElement(notifyForm).build().perform();
                notifyForm.clear();
                notifyForm.sendKeys(localRunConfig.DEVICE_NAME);

                WebElement submitButton;
                submitButton = WrapLocator.waitClickable(driver, Loc.XPATH, ipNotifySubmitXpath, 10);
                Actions actionSubmit = new Actions(driver);
                actionSubmit.moveToElement(submitButton).build().perform();
                submitButton.click();

                WebElement confirmMessage;
                confirmMessage = WrapLocator.waitDisplay(driver, Loc.XPATH, ipNotifyConfirmXpath, 10);
                confirmMessage.getText();

                new WrapJson(workDir).updateLocalStatusCache(localStatusCacheFile, currentIp);

            } catch (CustomExceptLocatorType | CustomExceptElementWait e) {
                Log.save(logFile, "Failed to send update notification");
                e.printStackTrace();
                driver.quit();
                terminateWebDriver(localRunConfig, localBinConfig.TASKLIST, localBinConfig.TASKKILL, exceptPidList);
                System.exit(1);
            }
        }

        driver.quit();

        terminateWebDriver(localRunConfig, localBinConfig.TASKLIST, localBinConfig.TASKKILL, exceptPidList);

        LocalDateTime stopClock = LocalDateTime.now();
        Duration duration = Duration.between(startClock, stopClock);
        Log.save(logFile, "Process time "+ duration.getSeconds() +" sec");

    }

    static void terminateWebDriver(LocalRunConfig localRunConfig, String taskListExe, String taskKillExe, ArrayList<Integer> exceptPidList){

        switch (localRunConfig.RUNTIME_DRIVER) {
            case "ChromeDriver":
                WinTask.kill(taskListExe, taskKillExe,"chromedriver.exe", exceptPidList);
                WinTask.kill(taskListExe, taskKillExe,"chrome.exe", exceptPidList);
                break;
            case "ChromeHeadlessDriver":
                WinTask.kill(taskListExe, taskKillExe,"chromedriver.exe", exceptPidList);
                WinTask.kill(taskListExe, taskKillExe,"chrome.exe", exceptPidList);
                break;
            case "EdgeDriver":
                WinTask.kill(taskListExe, taskKillExe,"microsoftwebdriver.exe", exceptPidList);
                WinTask.kill(taskListExe, taskKillExe,"microsoftedg*.exe", exceptPidList);
                break;
            case "FirefoxDriver":
                WinTask.kill(taskListExe, taskKillExe,"geckodriver.exe", exceptPidList);
                WinTask.kill(taskListExe, taskKillExe,"firefox.exe", exceptPidList);
                break;
            case "FirefoxHeadlessDriver":
                WinTask.kill(taskListExe, taskKillExe,"geckodriver.exe", exceptPidList);
                WinTask.kill(taskListExe, taskKillExe,"firefox.exe", exceptPidList);
                break;
            case "HtmlUnitDriver":
                break;
            case "InternetExplorerDriver":
                WinTask.kill(taskListExe, taskKillExe,"iedriverserver.exe", exceptPidList);
                WinTask.kill(taskListExe, taskKillExe,"iexplore.exe", exceptPidList);
                break;
            case "JBrowserDriver":
                break;
            case "PhantomJSDriver":
                WinTask.kill(taskListExe, taskKillExe,"phantomjs.exe", exceptPidList);
                break;
            case "OperaDriver":
                WinTask.kill(taskListExe, taskKillExe,"operadriver.exe", exceptPidList);
                WinTask.kill(taskListExe, taskKillExe,"opera.exe", exceptPidList);
                break;
            case "SafariDriver":
                break;
            default:
                System.out.println("Unknown RUNTIME_DRIVER : "+ localRunConfig.RUNTIME_DRIVER);
                break;
        }
    }
}
