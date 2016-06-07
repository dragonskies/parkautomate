package parkrdu;

import java.io.File;
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
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.*;

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
  private Integer screenshotNumber = 0;
  
  public CheckLinks(Properties testdata, String browser){
      entrydate = testdata.getProperty("entrydate");
      exitdate = testdata.getProperty("exitdate");
      ccName = testdata.getProperty("ccName");
      ccNumber = testdata.getProperty("ccNumber");
      ccCSV = testdata.getProperty("ccCSV");
      firstName = testdata.getProperty("firstname");
      lastName = testdata.getProperty("lastname");
      emailAddress = testdata.getProperty("email");
      ccMonth = testdata.getProperty("ccmonth");
      if (browser == "Firefox"){
          driver = new FirefoxDriver();
      } else if (browser == "Internet Explorer 11"){
          //System.setProperty("", "baseUrl");
          driver = new InternetExplorerDriver();
      } else if (browser == "Chrome"){
          System.setProperty("webdriver.chrome.driver", "C:\\Users\\davist\\Downloads\\chromedriver_win32(2)\\chromedriver.exe");
          driver = new ChromeDriver();
      } else if (browser == "Safari"){
          driver = new SafariDriver();
      } else if (browser == "Edge"){
          System.setProperty("webdriver.edge.driver", "C:\\Users\\davist\\Downloads\\MicrosoftWebDriver.exe");
          driver = new EdgeDriver();
      } else if (browser == "Opera"){
          driver = new OperaDriver();
      }
    baseUrl = "https://rdu-booking.preprod.inventiveit.net/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS );
  }

  public void testCheckLinks() throws Exception {

    driver.get(baseUrl + "/en/");
    String parentWindow = driver.getWindowHandle();
    driver.findElement(By.cssSelector("header-logo")).click();
    driver.findElement(By.xpath("html/body/main/nav/div/div[1]/div/ul/li[1]/a")).click();
    File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.xpath("//nav[@class='header-bottom']//a[.='Parking Information']")).click();
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.xpath("//nav[@class='header-bottom']//a[.='Manage My Booking']")).click();
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.xpath("//nav[@class='header-bottom']//a[.='Login']")).click();
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.xpath("//nav[@class='header-bottom']//a[.='Create Account']")).click();
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.linkText("TERMS & CONDITIONS")).click();
    Set<String> handles = driver.getWindowHandles();
    	for (String windowHandle : handles){
    		if(!windowHandle.equals(parentWindow)){
    			driver.switchTo().window(windowHandle);
    			screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    		    screenshotNumber += 1;
    		    FileUtils.copyFile(screenshot, new File(screenshotName+screenshotNumber.toString()+".png"));
    		    driver.findElement(By.linkText("PRIVACY POLICY")).click();
    		    //driver.switchTo().window("Privacy Policy | RDU International Airport");
    		    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    		    screenshotNumber += 1;
    		    FileUtils.copyFile(screenshot, new File(screenshotName+screenshotNumber.toString()+".png"));
    		    driver.findElement(By.linkText("CONTACT US")).click();
    		    //driver.switchTo().window("Contact us | RDU International Airport");
    		    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    		    screenshotNumber += 1;
    		    FileUtils.copyFile(screenshot, new File(screenshotName+screenshotNumber.toString()+".png"));
    		    driver.findElement(By.xpath("(//a[contains(text(),'Frequently Asked Questions')])[2]")).click();
    		    //driver.switchTo().window("Frequently Asked Questions | RDU International Airport");
    		    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    		    screenshotNumber += 1;
    		    FileUtils.copyFile(screenshot, new File(screenshotName+screenshotNumber.toString()+".png"));
    		}
    	}
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
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
