# wpip
Public IP address monitor and notify service for Windows using Java Selenium WebDriver

* Build JAR with dependancies using Maven  
  `C:\wpim>maven clean package`

* __WebDriver executables not included__  
  Find them at [SeleniumHQ](https://www.seleniumhq.org/download/)

* Configure visible or headless driver in JSON config file  
  `"RUNTIME_DRIVER":"ChromeHeadlessDriver"`

* Configure IP check frequency using Windows Task Scheduler  
  `java -jar "C:\wpip\app.jar" "C:\wpip"`

* Requires no open inbound ports

* All traffic across HTTPS

* To notify IP changes requires a remote HTTPS input form  
  I used [WordPress](https://www.wordpress.com) for mine
