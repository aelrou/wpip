package app;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Please specify the working directory where the JAR file is located.");
            System.out.println("Example: \"java.exe\" -jar \"app.jar\" \"C:\\Working\\Directory\"");
            System.exit(1);
        }

        if (args.length != 1) {
            System.out.println("Only 1 parameter allowed. Found "+ args.length);
            System.exit(1);
        }

        String workDir = args[0];

        findJarFile(workDir, "app.jar");

        File localBinConfigFile = findLocalBinConfig(workDir,"LocalBinConfig.json");
        LocalBinConfig localBinConfig = readLocalBinConfig(localBinConfigFile);

        String taskListExe = localBinConfig.TASKLIST;
        String taskKillExe = localBinConfig.TASKKILL;

        File localRunConfigFile = findLocalRunConfig(workDir,"LocalRunConfig.json");
        LocalRunConfig localRunConfig = readLocalRunConfig(localRunConfigFile);

        String logFile = Log.find(localRunConfig.LOGS_PATH, localRunConfig.LOG_NAME);

        File localStageConfigFile = findLocalStageConfig(workDir,"LocalStageConfig.json");
        LocalStageConfig localStageConfig = readLocalStageConfig(localStageConfigFile);

        File localStatusCacheFile = findLocalStatusCache(workDir, "LocalStatusCache.json");
        LocalStatusCache localStatusCache = readLocalStatusCache(localStatusCacheFile);

        String executable;
        WebDriver driver = null;

        switch (localRunConfig.RUNTIME_DRIVER) {
            case "ChromeDriver":
                executable = workDir +"\\"+ localBinConfig.CHROME_DRIVER;
                System.setProperty("webdriver.chrome.driver", executable);
                ChromeOptions chromeVisibleOptions = new ChromeOptions();
                chromeVisibleOptions.addArguments("--user-data-dir="+ localBinConfig.CHROME_PROFILE);
                driver = new ChromeDriver(chromeVisibleOptions);
                break;
            case "ChromeHeadlessDriver":
                executable = workDir +"\\"+ localBinConfig.CHROME_DRIVER;
                System.setProperty("webdriver.chrome.driver", executable);
                ChromeOptions chromeHeadlessOptions = new ChromeOptions();
                chromeHeadlessOptions.addArguments("--user-data-dir="+ localBinConfig.CHROME_PROFILE);
                chromeHeadlessOptions.addArguments("--headless");
                chromeHeadlessOptions.addArguments("--disable-gpu");
                driver = new ChromeDriver(chromeHeadlessOptions);
                break;
            case "EdgeDriver":
                executable = workDir +"\\"+ localBinConfig.EDGE_DRIVER;
                System.setProperty("webdriver.edge.driver", executable);
                driver = new EdgeDriver();
                break;
            case "FirefoxDriver":
                executable = workDir +"\\"+ localBinConfig.FIREFOX_DRIVER;
                System.setProperty("webdriver.gecko.driver", executable);
                FirefoxOptions firefoxVisibleOptions = new FirefoxOptions();
                firefoxVisibleOptions.setProfile(new FirefoxProfile(new File(localBinConfig.FIREFOX_PROFILE)));
                driver = new FirefoxDriver(firefoxVisibleOptions);
                break;
            case "FirefoxHeadlessDriver":
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
                executable = workDir +"\\"+ localBinConfig.PHANTOMJS_DRIVER;
                DesiredCapabilities phantomJsCapabilities = new DesiredCapabilities();
                phantomJsCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, executable);
                phantomJsCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--ignore-ssl-errors=true", "--ssl-protocol=any", "--web-security=false",});
                driver = new PhantomJSDriver(phantomJsCapabilities);
                break;
            case "OperaDriver":
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
                terminateWebDriver(localRunConfig, taskListExe, taskKillExe);
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
                terminateWebDriver(localRunConfig, taskListExe, taskKillExe);
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
            terminateWebDriver(localRunConfig, taskListExe, taskKillExe);
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

                updateLocalStatusCache(localStatusCacheFile, currentIp);

            } catch (CustomExceptLocatorType | CustomExceptElementWait e) {
                Log.save(logFile, "Failed to send update notification");
                e.printStackTrace();
                driver.quit();
                terminateWebDriver(localRunConfig, taskListExe, taskKillExe);
                System.exit(1);
            }
        }

        driver.quit();

        terminateWebDriver(localRunConfig, taskListExe, taskKillExe);

        LocalDateTime stopClock = LocalDateTime.now();
        Duration duration = Duration.between(startClock, stopClock);
        Log.save(logFile, "Process time "+ duration.getSeconds() +" sec");

    }

    static File findJarFile(String workDir, String fileName) {
        File filePath = null;
        try {
            filePath = new File(workDir +"\\"+ fileName).getCanonicalFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!filePath.exists()) {
            System.out.println("Cannot find \""+ filePath.toString() +"\"");
            System.out.println("Please ensure that the working directory is correct");
            System.exit(1);
        }
        System.out.println("Found JAR \""+ filePath.toString() +"\"");
        return filePath;
    }

    static File findLocalBinConfig(String workDir, String fileName) {
        File filePath = null;
        try {
            filePath = new File(workDir +"\\"+ fileName).getCanonicalFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!filePath.exists()) {
            System.out.println("Cannot find \""+ filePath.toString() +"\"");
            createLocalBinConfig(filePath);
            System.exit(1);
        }
        System.out.println("Found JSON \""+ filePath.toString() +"\"");
        return filePath;
    }

    static LocalBinConfig readLocalBinConfig(File file) {
        LocalBinConfig localBinConfig = null;
        try {
            FileInputStream input = new FileInputStream(file);
            Reader reader = new InputStreamReader(input, "US-ASCII");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            localBinConfig = gson.fromJson(reader, LocalBinConfig.class);
            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localBinConfig;
    }

    static void createLocalBinConfig(File file) {
        try {
//            System.out.println("Creating JSON template \""+ file.toString() +"\"");
            System.out.println("Creating...");
            FileWriter writer = new FileWriter(file, false);
            BufferedWriter buffer = new BufferedWriter(writer);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(new LocalBinConfig(true), buffer);
            buffer.flush();
            buffer.close();
            System.out.println("Please examine \""+ file +"\" and update the relative paths as needed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static File findLocalRunConfig(String workDir, String fileName) {
        File filePath = null;
        try {
            filePath = new File(workDir +"\\"+ fileName).getCanonicalFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!filePath.exists()) {
            System.out.println("Cannot find \""+ filePath.toString() +"\"");
            createLocalRunConfig(filePath);
            System.exit(1);
        }
        System.out.println("Found JSON \""+ filePath.toString() +"\"");
        return filePath;
    }

    static LocalRunConfig readLocalRunConfig(File file) {
        LocalRunConfig localRunConfig = null;
        try {
            FileInputStream input = new FileInputStream(file);
            Reader reader = new InputStreamReader(input, "US-ASCII");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            localRunConfig = gson.fromJson(reader, LocalRunConfig.class);
            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localRunConfig;
    }

    static void createLocalRunConfig(File file) {
        try {
//            System.out.println("Creating JSON template \""+ file.toString() +"\"");
            System.out.println("Creating...");
            FileWriter writer = new FileWriter(file, false);
            BufferedWriter buffer = new BufferedWriter(writer);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(new LocalRunConfig(true), buffer);
            buffer.flush();
            buffer.close();
            System.out.println("Please examine \""+ file +"\" and update the selected driver as needed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static File findLocalStageConfig(String workDir, String fileName) {
        File filePath = null;
        try {
            filePath = new File(workDir +"\\"+ fileName).getCanonicalFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!filePath.exists()) {
            System.out.println("Cannot find \""+ filePath.toString() +"\"");
            createLocalStageConfig(filePath);
            System.exit(1);
        }
        System.out.println("Found JSON \""+ filePath.toString() +"\"");
        return filePath;
    }

    static LocalStageConfig readLocalStageConfig(File file) {
        LocalStageConfig localStageConfig = null;
        try {
            FileInputStream input = new FileInputStream(file);
            Reader reader = new InputStreamReader(input, "US-ASCII");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            localStageConfig = gson.fromJson(reader, LocalStageConfig.class);
            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localStageConfig;
    }

    static void createLocalStageConfig(File file) {
        try {
//            System.out.println("Creating JSON template \""+ file.toString() +"\"");
            System.out.println("Creating...");
            FileWriter writer = new FileWriter(file, false);
            BufferedWriter buffer = new BufferedWriter(writer);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(new LocalStageConfig(true), buffer);
            buffer.flush();
            buffer.close();
            System.out.println("Please examine \""+ file +"\" and update the stage parameters as needed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static File findLocalStatusCache(String workDir, String fileName) {
        File filePath = null;
        try {
            filePath = new File(workDir +"\\"+ fileName).getCanonicalFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!filePath.exists()) {
            System.out.println("Cannot find \""+ filePath.toString() +"\"");
            createLocalStatusCache(filePath);
            System.exit(1);
        }
        System.out.println("Found JSON \""+ filePath.toString() +"\"");
        return filePath;
    }

    static LocalStatusCache readLocalStatusCache(File file) {
        LocalStatusCache localStatusCache = null;
        try {
            FileInputStream input = new FileInputStream(file);
            Reader reader = new InputStreamReader(input, "US-ASCII");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            localStatusCache = gson.fromJson(reader, LocalStatusCache.class);
            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localStatusCache;
    }

    static void createLocalStatusCache(File file) {
        try {
//            System.out.println("Creating JSON template \""+ file.toString() +"\"");
            System.out.println("Creating...");
            FileWriter writer = new FileWriter(file, false);
            BufferedWriter buffer = new BufferedWriter(writer);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(new LocalStatusCache(true), buffer);
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void updateLocalStatusCache(File file, String result) {
        try {
            FileWriter writer = new FileWriter(file, false);
            BufferedWriter buffer = new BufferedWriter(writer);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            LocalStatusCache updateStatusCache = new LocalStatusCache(false);
            updateStatusCache.IP_CACHE = result;
            updateStatusCache.LAST_UPDATE = LocalDateTime.now();

            gson.toJson((updateStatusCache), buffer);
            buffer.flush();
            buffer.close();
            System.out.println("Updated JSON status cache \""+ file.toString() +"\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void terminateWebDriver(LocalRunConfig localRunConfig, String taskListExe, String taskKillExe){

        switch (localRunConfig.RUNTIME_DRIVER) {
            case "ChromeDriver":
                if (WinTask.find(taskListExe,"chromedriver.exe")){
                    WinTask.kill(taskKillExe,"chromedriver.exe", true);
                }
                if (WinTask.find(taskListExe,"chrome.exe")){
                    WinTask.kill(taskKillExe,"chrome.exe", true);
                }
                break;
            case "ChromeHeadlessDriver":
                if (WinTask.find(taskListExe,"chromedriver.exe")){
                    WinTask.kill(taskKillExe,"chromedriver.exe", true);
                }
                if (WinTask.find(taskListExe,"chrome.exe")){
                    WinTask.kill(taskKillExe,"chrome.exe", true);
                }
                break;
            case "EdgeDriver":
                if (WinTask.find(taskListExe,"microsoftwebdriver.exe")){
                    WinTask.kill(taskKillExe,"microsoftwebdriver.exe", true);
                }
                if (WinTask.find(taskListExe,"microsoftedg*.exe")){
                    WinTask.kill(taskKillExe,"microsoftedg*.exe", true);
                }
                break;
            case "FirefoxDriver":
                if (WinTask.find(taskListExe,"geckodriver.exe")){
                    WinTask.kill(taskKillExe,"geckodriver.exe", true);
                }
                if (WinTask.find(taskListExe,"firefox.exe")){
                    WinTask.kill(taskKillExe,"firefox.exe", true);
                }
                break;
            case "FirefoxHeadlessDriver":
                if (WinTask.find(taskListExe,"geckodriver.exe")){
                    WinTask.kill(taskKillExe,"geckodriver.exe", true);
                }
                if (WinTask.find(taskListExe,"firefox.exe")){
                    WinTask.kill(taskKillExe,"firefox.exe", true);
                }
                break;
            case "HtmlUnitDriver":
                break;
            case "InternetExplorerDriver":
                if (WinTask.find(taskListExe,"iedriverserver.exe")){
                    WinTask.kill(taskKillExe,"iedriverserver.exe", true);
                }
                if (WinTask.find(taskListExe,"iexplore.exe")){
                    WinTask.kill(taskKillExe,"iexplore.exe", true);
                }
                break;
            case "JBrowserDriver":
                break;
            case "PhantomJSDriver":
                if (WinTask.find(taskListExe,"phantomjs.exe")){
                    WinTask.kill(taskKillExe,"phantomjs.exe", true);
                }
                break;
            case "OperaDriver":
                if (WinTask.find(taskListExe,"operadriver.exe")){
                    WinTask.kill(taskKillExe,"operadriver.exe", true);
                }
                if (WinTask.find(taskListExe,"opera.exe")){
                    WinTask.kill(taskKillExe,"opera.exe", true);
                }
                break;
            case "SafariDriver":
                break;
            default:
                System.out.println("Unknown RUNTIME_DRIVER : "+ localRunConfig.RUNTIME_DRIVER);
                break;
        }
    }
}
