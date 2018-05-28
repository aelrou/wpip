# wpip
Public IP address monitor and notify utility using Java Selenium WebDriver on Windows

* Builds in [Java 10](http://www.oracle.com/technetwork/java/javase/downloads/index.html)  
  `JAVA_HOME - C:\Program Files\Java\jdk-10.0.1`  
  `JRE_HOME - C:\Program Files\Java\jre-10.0.1`  
  `PATH - C:\Program Files\Java\jre-10.0.1\bin`

* Build JAR with dependencies using [Maven](https://maven.apache.org/)  
  `M2_HOME - C:\Program Files\Maven`  
  `PATH - C:\Program Files\Maven\bin`  
  `C:\wpip>mvn clean package`

* __WebDriver executables not included__  
  Find them at [SeleniumHQ](https://www.seleniumhq.org/download/)

* Configure visible or headless driver in JSON config file  
  `"RUNTIME_DRIVER":"FirefoxHeadlessDriver"`

* Configure IP check frequency using Windows Task Scheduler  
  `java -jar "C:\wpip\app.jar" "C:\wpip"`

* Requires no open inbound ports

* All traffic across HTTPS

* To notify IP changes requires a remote HTTPS input form  
  I used [WordPress](https://www.wordpress.com) for mine
