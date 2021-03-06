package parkrdu;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
//import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.*;

/**
 * 
 * @author DavisT
 *
 */
public class CheckLinks {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  // test data variables
  private String entrydate;
  private String exitdate;
  private String ccName;
  private String ccYear;
  private String ccMonth;
  private String ccNumber;
  private String firstName;
  private String lastName;
  private String emailAddress;
  private String ccCSV;
  private String screenshotName = "screenshot";
  private String screenshotDir;
  private Integer screenshotNumber = 0;
  private String browserName;
  private Integer runCount;
  
  private Integer waitTimes = 200; // default timeout to allow pages to load
 
  /**
   * browser is sent from TestParameters
   * browserName is used by CheckLinks
   * 
   * @param testdata
   * @param browser
   */
  public CheckLinks(Properties testdata,  String browser){
      
	  entrydate = testdata.getProperty("entrydate");
      exitdate = testdata.getProperty("exitdate");
      ccName = testdata.getProperty("ccName");
      ccNumber = testdata.getProperty("ccNumber");
      ccCSV = testdata.getProperty("ccCSV");
      firstName = testdata.getProperty("firstname");
      lastName = testdata.getProperty("lastname");
      emailAddress = testdata.getProperty("email");
      ccMonth = testdata.getProperty("ccmonth");
      screenshotName = testdata.getProperty("screenshotname");
      screenshotDir = testdata.getProperty("screenshotdir")+"\\";
      
      if (browser == "Firefox"){
          driver = new FirefoxDriver();
          browserName = browser;
          waitTimes = 20000; //Firefox needs longer to load to avoid blank screenshots
      } else if (browser == "Internet Explorer 11"){
    	  DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer(); 
    	  ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
    	  ieCapabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "https://rdu-booking.preprod.inventiveit.net/en/");
          driver = new InternetExplorerDriver(ieCapabilities);
          browserName = browser;
          waitTimes = 400; //allow a little longer for IE to load
          try {
			Thread.sleep(waitTimes);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      } else if (browser == "Chrome"){
    	  URL url = this.getClass().getClassLoader().getResource("chromedriver.exe");
    	  System.setProperty("webdriver.chrome.driver", url.getPath());
          driver = new ChromeDriver();
          browserName = browser;
      } else if (browser == "Edge"){
    	  URL url = this.getClass().getClassLoader().getResource("MicrosoftWebDriver.exe");
          System.setProperty("webdriver.edge.driver", url.getPath());
          driver = new EdgeDriver();
          browserName = browser;
      } else if (browser == "Opera"){
    	  /*
    	   * Not sure if this is needed
    	   * 
    	  URL url = this.getClass().getClassLoader().getResource("OperaDriver.exe");
    	  System.setProperty("webdriver.opera.driver", url.getPath());
          */
          driver = new OperaDriver();
          browserName = browser;
      }
    baseUrl = "https://rdu-booking.preprod.inventiveit.net/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS );
  }

  public void testCheckLinks() throws Exception {

    driver.get(baseUrl + "/en/");
    Thread.sleep(2000);

    //special code to click the rdu logo
    WebElement rduLogo = driver.findElement(By.className("header-logo"));
    rduLogo.findElement(By.tagName("img")).click();
    // 
    //driver.findElement(By.cssSelector("a[href*=/en/")).click();
    Thread.sleep(waitTimes);
    //String parentWindow = driver.getWindowHandle();
    File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.xpath("//nav[@class='header-bottom']//a[.='Parking Information']")).click();
    Thread.sleep(waitTimes);
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.xpath("//nav[@class='header-bottom']//a[.='Manage My Booking']")).click();
    Thread.sleep(waitTimes);
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.xpath("//nav[@class='header-bottom']//a[.='Login']")).click();
    Thread.sleep(waitTimes);
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.xpath("//nav[@class='header-bottom']//a[.='Create Account']")).click();
    Thread.sleep(waitTimes);
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.linkText("TERMS & CONDITIONS")).click();
    Thread.sleep(waitTimes);
    String parentWindow = driver.getWindowHandle();
    Set<String> handles = driver.getWindowHandles();
    	for (String windowHandle : handles){
    		if(!windowHandle.equals(parentWindow)){
    			driver.switchTo().window(windowHandle);
    			screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    		    screenshotNumber += 1;
    		    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    		    driver.findElement(By.linkText("PRIVACY POLICY")).click();
    		    Thread.sleep(waitTimes);
    		    //driver.switchTo().window("Privacy Policy | RDU International Airport");
    		    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    		    screenshotNumber += 1;
    		    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    		    driver.findElement(By.linkText("CONTACT US")).click();
    		    Thread.sleep(waitTimes);
    		    //driver.switchTo().window("Contact us | RDU International Airport");
    		    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    		    screenshotNumber += 1;
    		    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    		    driver.findElement(By.xpath("(//a[contains(text(),'Frequently Asked Questions')])[2]")).click();
    		    Thread.sleep(waitTimes);
    		    //driver.switchTo().window("Frequently Asked Questions | RDU International Airport");
    		    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    		    screenshotNumber += 1;
    		    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    		}
    	}
    	driver.close();
  }


/*  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }
*/
  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
