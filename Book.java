package parkrdu;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
//import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

/**
 * 
 * @author DavisT
 */
public class Book {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  // test data variables
  private String entrydate;
  private String exitdate;
  private String entrytime;
  private String exittime;
  private String ccName;
  private String ccYear;
  private String ccMonth;
  private String ccNumber;
  private String firstName;
  private String lastName;
  private String emailAddress;
  private String ccCSV;
  private String zipCode;
  private String accountName ="notused";
  private String accountPassword;
  private String browserName;
  private Integer waitTimes = 200; // default timeout to allow pages to load
  private String screenshotName;
  private String screenshotDir;
  private Integer screenshotNumber = 0;

  
  /**
   * 
   * @param testdata
   * constructor for booking test without account
   * 
   */
  public Book(Properties testdata, String browser){
	  entrydate = testdata.getProperty("entrydate");
      exitdate = testdata.getProperty("exitdate");
      entrytime = testdata.getProperty("entrytime");
      exittime = testdata.getProperty("exittime");
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
  /**
   * 
   * @param testdata
   * @param accountName
   * @param accountPassword
   * constructor for booking test using an account
   * 
   */
  public Book(Properties testdata, String accName, String accPassword, String browser){
      entrydate = testdata.getProperty("entrydate");
      exitdate = testdata.getProperty("exitdate");
      ccName = testdata.getProperty("ccName");
      ccNumber = testdata.getProperty("ccNumber");
      ccCSV = testdata.getProperty("ccCSV");
      firstName = testdata.getProperty("firstname");
      lastName = testdata.getProperty("lastname");
      emailAddress = testdata.getProperty("email");
      ccMonth = testdata.getProperty("ccmonth");
      zipCode = testdata.getProperty("zipcode");
      accountName = accName;
      accountPassword = accPassword;
      baseUrl = "https://rdu-booking.preprod.inventiveit.net/";
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS );
  }

  //@Test
  public void testBook() throws Exception {
    driver.get("https://rdu-booking.preprod.inventiveit.net/en/");
    File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    Thread.sleep(waitTimes);
    /*
     * This block is only for testing account related activity
     */
    if (accountName != "notused"){
	    driver.findElement(By.cssSelector("div.booking-form-login > a.btn.btn-primary")).click();
	    driver.findElement(By.id("login-password")).clear();
	    driver.findElement(By.id("login-password")).sendKeys("parkrdu");
	    driver.findElement(By.id("login-username")).clear();
	    driver.findElement(By.id("login-username")).sendKeys(emailAddress);
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	 }

    driver.findElement(By.id("entry-date")).click();
    driver.findElement(By.id("entry-date")).clear();
    driver.findElement(By.id("entry-date")).sendKeys(entrydate);
    driver.findElement(By.id("entry-time")).click();
    driver.findElement(By.id("entry-time")).sendKeys(entrytime);
    driver.findElement(By.id("exit-date")).click();
    driver.findElement(By.id("exit-date")).clear();
    driver.findElement(By.id("exit-date")).sendKeys(exitdate);
    driver.findElement(By.id("exit-time")).click();
    driver.findElement(By.id("exit-time")).sendKeys(exittime);
    Thread.sleep(waitTimes);
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    Thread.sleep(waitTimes);
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.xpath(".//*[@id='search-result-3']/div[1]/div[6]/form/button")).click();
    Thread.sleep(waitTimes);
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.linkText("Proceed to Checkout")).click();
    Thread.sleep(waitTimes);
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.id("checkbox-terms_and_condition")).click();
    Thread.sleep(waitTimes);
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.xpath("//main[@id='panel']/section[2]/div/div/form/div[10]/div[11]/div/div/div")).click();
    Thread.sleep(waitTimes);
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    Thread.sleep(waitTimes);
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.id("input-first_name")).sendKeys(firstName);
    driver.findElement(By.id("input-last_name")).sendKeys(lastName);
    driver.findElement(By.id("input-email")).sendKeys(emailAddress);
    driver.findElement(By.id("input-confirm_email")).sendKeys(emailAddress);    
    driver.findElement(By.id("input-postcode")).clear();
    driver.findElement(By.id("input-postcode")).sendKeys(zipCode);
    driver.findElement(By.xpath("//main[@id='panel']/section[2]/div/div/form/div[10]/div[11]/div/div/div")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    Thread.sleep(waitTimes);
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
    driver.findElement(By.id("card_holder_name")).clear();
    driver.findElement(By.id("card_holder_name")).sendKeys(ccName);
    driver.findElement(By.id("credit_card_no")).clear();
    driver.findElement(By.id("credit_card_no")).sendKeys(ccNumber);
    new Select(driver.findElement(By.id("card_type"))).selectByVisibleText("Visa");
    new Select(driver.findElement(By.id("year"))).selectByVisibleText("2026");
    driver.findElement(By.id("cvv")).clear();
    driver.findElement(By.id("cvv")).sendKeys(ccCSV);
    driver.findElement(By.id("payment_button")).click();
    Thread.sleep(waitTimes);
    screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    screenshotNumber += 1;
    FileUtils.copyFile(screenshot, new File(screenshotDir+screenshotName+screenshotNumber.toString()+".png"));
//    assertEquals("Thank you for your order test me", driver.findElement(By.xpath("//main[@id='panel']/article/div/div/div/p[2]")).getText());
  }

 /*
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
*/
  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

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
