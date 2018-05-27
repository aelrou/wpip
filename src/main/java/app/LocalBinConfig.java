package app;

public class LocalBinConfig {
    public String FIREFOX_DRIVER;
    public String FIREFOX_PROFILE;
    public String CHROME_DRIVER;
    public String CHROME_PROFILE;
    public String IE_DRIVER;
    public String EDGE_DRIVER;
    public String SAFARI_DRIVER;
    public String OPERA_DRIVER;
    public String PHANTOMJS_DRIVER;

    public LocalBinConfig (boolean useDefault) {
        if(useDefault) {
            this.FIREFOX_DRIVER = "webdriver_binary\\geckodriver-v0.20.1-win64\\geckodriver.exe";
            this.FIREFOX_PROFILE = "C:\\Users\\NONEXISTANT\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\NONEXISTANT";
            this.CHROME_DRIVER = "webdriver_binary\\chromedriver_2.38_win32\\chromedriver.exe";
            this.CHROME_PROFILE = "C:\\Users\\NONEXISTANT\\AppData\\Local\\Google\\Chrome\\NONEXISTANT";
            this.IE_DRIVER = "webdriver_binary\\IEDriverServer_Win32_3.12.0\\IEDriverServer.exe";
            this.EDGE_DRIVER = "webdriver_binary\\MicrosoftWebDriver17134\\MicrosoftWebDriver.exe";
            this.SAFARI_DRIVER = "webdriver_binary\\SafariDriver_2.48.0\\SafariDriver.safariextz";
            this.OPERA_DRIVER = "webdriver_binary\\operadriver_2.36_win64\\operadriver.exe";
            this.PHANTOMJS_DRIVER = "webdriver_binary\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";
        }
    }
}
