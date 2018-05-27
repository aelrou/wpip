package app;

public class LocalRunConfig {
    public String RUNTIME_DRIVER;
    public String RUNTIME_STAGE;
    public String AVALIBLE_DRIVERS;
    public String AVALIBLE_STAGES;

    public LocalRunConfig (boolean useDefault) {
        if(useDefault) {
            this.RUNTIME_DRIVER = "FirefoxDriver";
            this.RUNTIME_STAGE = "Development";
            this.AVALIBLE_DRIVERS = "ChromeDriver ChromeHeadlessDriver EdgeDriver FirefoxDriver FirefoxHeadlessDriver HtmlUnitDriver InternetExplorerDriver JBrowserDriver OperaDriver PhantomJSDriver SafariDriver";
            this.AVALIBLE_STAGES = "Development Test PRODUCTION";
        }
    }
}
