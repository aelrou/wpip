package app;

public class LocalStageConfig {
    public String IPCHECK_URL;
    public String IPCHECK_XPATH_LOCATOR;
    public String IPNOTIFY_URL;
    public String IPNOTIFY_FORM_XPATH_LOCATOR;
    public String IPNOTIFY_SUBMIT_XPATH_LOCATOR;
    public String IPNOTIFY_CONFIRM_XPATH_LOCATOR;

    public LocalStageConfig (boolean useDefault) {
        if(useDefault) {
            this.IPCHECK_URL = "https://www.google.com/search?q=what+is+my+ip";
            this.IPCHECK_XPATH_LOCATOR = "//w-answer-desktop/div[contains(.,'Your public IP address')]/../div[1]";
            this.IPNOTIFY_URL = "https://NONEXISTANT.wordpress.com/NONEXISTANT/";
            this.IPNOTIFY_FORM_XPATH_LOCATOR = "//div[@class='entry-content']/div/form/div/label[contains(.,'Name')]/../input";
            this.IPNOTIFY_SUBMIT_XPATH_LOCATOR = "//div[@class='entry-content']/div/form/div/label[contains(.,'Name')]/../../p/input[@type='submit']";
            this.IPNOTIFY_CONFIRM_XPATH_LOCATOR = "//div[@class='entry-content']/div/*[contains(.,'Message Sent')]";
        }
    }
}
