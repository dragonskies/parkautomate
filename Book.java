package parkrdu;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
//import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
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
  private String ccName;
  private String ccYear;
  private String ccMonth;
  private String ccNumber;
  private String firstName;
  private String lastName;
  private String emailAddress;
  private String ccCSV;
  private String zipCode;
  private String accountName;
  private String accountPassword;
  /**
   * 
   * @param testdata
   * constructor for booking test without account
   * 
   */
  public Book(Properties testdata, String browser){
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
      accountName = "";
      if (browser == "Firefox"){
          driver = new FirefoxDriver();
      } else if (browser == "Internet Explorer 11"){
          driver = new InternetExplorerDriver();
      } else if (browser == "Chrome"){
          driver = new ChromeDriver();
      } else if (browser == "Edge"){
          driver = new EdgeDriver();
      } else if (browser == "Opera"){
          driver = new OperaDriver();
      }
  }
  /**
   * 
   * @param testdata
   * @param accountName
   * @param accountPassword
   * constructor for booking test using an account
   * 
   */
  public Book(Properties testdata, String accountName, String accountPassword, String browser){
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
      
  }
//  @Before
  public void setUp() throws Exception {
    //driver = new FirefoxDriver();
    baseUrl = "https://rdu-booking.preprod.inventiveit.net/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    //System.console().writer().print("setting up test");
  }

  //@Test
  public void testBook() throws Exception {
      //System.console().writer().print("starting test");
    driver.get("https://rdu-booking.preprod.inventiveit.net/en/");
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
    // ERROR: Caught exception [ERROR: Unsupported command [getEval | var i = new Date(); (i.getMonth() +1) + "/" +( i.getDate()+2) + "/" + i.getFullYear(); | ]]
    driver.findElement(By.id("entry-date")).click();
    driver.findElement(By.id("entry-date")).clear();
      String date = "8/25/2016";
    driver.findElement(By.id("entry-date")).sendKeys(entrydate);
    driver.findElement(By.id("exit-date")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [getEval | var i = new Date(); (i.getMonth() +1) + "/" + (i.getDate()+3) + "/" + i.getFullYear(); | ]]
      date = "9/25/2016";
    driver.findElement(By.id("exit-date")).clear();
    driver.findElement(By.id("exit-date")).sendKeys(exitdate);
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath(".//*[@id='search-result-3']/div[1]/div[6]/form/button")).click();
    driver.findElement(By.linkText("Proceed to Checkout")).click();
    //driver.findElement(By.cssSelector("div.booking-form-login > a.btn.btn-primary")).click();
    //driver.findElement(By.id("login-password")).clear();
    //driver.findElement(By.id("login-password")).sendKeys("parkrdu");
    //driver.findElement(By.id("login-username")).clear();
    //driver.findElement(By.id("login-username")).sendKeys(emailAddress);
    //driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.id("checkbox-terms_and_condition")).click();
    driver.findElement(By.xpath("//main[@id='panel']/section[2]/div/div/form/div[10]/div[11]/div/div/div")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.id("input-postcode")).clear();
    driver.findElement(By.id("input-postcode")).sendKeys(zipCode);
    driver.findElement(By.xpath("//main[@id='panel']/section[2]/div/div/form/div[10]/div[11]/div/div/div")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.id("card_holder_name")).clear();
    driver.findElement(By.id("card_holder_name")).sendKeys(ccName);
    driver.findElement(By.id("credit_card_no")).clear();
    driver.findElement(By.id("credit_card_no")).sendKeys(ccNumber);
    new Select(driver.findElement(By.id("card_type"))).selectByVisibleText("Visa");
    new Select(driver.findElement(By.id("year"))).selectByVisibleText("2026");
    driver.findElement(By.id("cvv")).clear();
    driver.findElement(By.id("cvv")).sendKeys(ccCSV);
    driver.findElement(By.id("payment_button")).click();
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
