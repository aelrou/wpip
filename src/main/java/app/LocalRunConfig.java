package app;

public class LocalRunConfig {
    public String DEVICE_NAME;
    public int SCREEN_WIDTH;
    public int SCREEN_HEIGHT;
    public int BROWSER_WIDTH;
    public int BROWSER_HEIGHT;
    public String RUNTIME_DRIVER;
    public String RUNTIME_STAGE;
    public String AVALIBLE_DRIVERS;
    public String AVALIBLE_STAGES;
    public String LOGS_PATH;
    public String LOG_NAME;

    public LocalRunConfig (boolean useDefault) {
        if(useDefault) {
            this.DEVICE_NAME = "My Unique Device Name";
            this.SCREEN_WIDTH = 1920;
            this.SCREEN_HEIGHT = 1080;
            this.BROWSER_WIDTH = 1280;
            this.BROWSER_HEIGHT = 1080;
            this.RUNTIME_DRIVER = "FirefoxDriver";
            this.RUNTIME_STAGE = "Development";
            this.AVALIBLE_DRIVERS = "ChromeDriver ChromeHeadlessDriver EdgeDriver FirefoxDriver FirefoxHeadlessDriver HtmlUnitDriver InternetExplorerDriver JBrowserDriver OperaDriver PhantomJSDriver SafariDriver";
            this.AVALIBLE_STAGES = "Development Test PRODUCTION";
            this.LOGS_PATH = "C:\\Users\\Public\\wpip";
            this.LOG_NAME = "wpip.log";
        }
    }
}
