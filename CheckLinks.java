package parkrdu;

import java.io.File;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.Select;

public class CheckLinks {
  private FirefoxDriver driver;
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
  
  public CheckLinks(Properties testdata){
      entrydate = testdata.getProperty("entrydate");
      exitdate = testdata.getProperty("exitdate");
      ccName = testdata.getProperty("ccName");
      ccNumber = testdata.getProperty("ccNumber");
      ccCSV = testdata.getProperty("ccCSV");
      firstName = testdata.getProperty("firstname");
      lastName = testdata.getProperty("lastname");
      emailAddress = testdata.getProperty("email");
      ccMonth = testdata.getProperty("ccmonth");
  }
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://rdu-booking.preprod.inventiveit.net/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCheckLinks() throws Exception {
    driver.get(baseUrl + "/en/");
    driver.findElement(By.xpath("//a[@class='header-logo']/img")).click();
    driver.findElement(By.xpath("//nav[@class='header-bottom']//a[.='Frequently Asked Questions']")).click();
    driver.getScreenshotAs(OutputType.FILE);
    driver.findElement(By.xpath("//nav[@class='header-bottom']//a[.='Parking Information']")).click();
    driver.getScreenshotAs(OutputType.FILE);
    driver.findElement(By.xpath("//nav[@class='header-bottom']//a[.='Manage My Booking']")).click();
    driver.getScreenshotAs(OutputType.FILE);
    driver.findElement(By.xpath("//nav[@class='header-bottom']//a[.='Login']")).click();
    driver.getScreenshotAs(OutputType.FILE);
    driver.findElement(By.xpath("//nav[@class='header-bottom']//a[.='Create Account']")).click();
    driver.getScreenshotAs(OutputType.FILE);
    driver.findElement(By.linkText("TERMS & CONDITIONS")).click();
    driver.getScreenshotAs(OutputType.FILE);
    driver.findElement(By.linkText("PRIVACY POLICY")).click();
    driver.getScreenshotAs(OutputType.FILE);
    driver.findElement(By.linkText("CONTACT US")).click();
    driver.getScreenshotAs(OutputType.FILE);
    driver.findElement(By.xpath("(//a[contains(text(),'Frequently Asked Questions')])[2]")).click();
    driver.getScreenshotAs(OutputType.FILE);
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
  }

  @After
  public void tearDown() throws Exception {
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
