package TestCases;


import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
//import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;


public class Base {
  public static WebDriver driver;
  //WebDriverWait wait;
  
  private static StringBuffer verificationErrors = new StringBuffer();
  //public ObjectMap objMap;
  public static String logFolderPath;  
  public static String workingDir = System.getProperty("user.dir"); 
  public static Select select;
  public static JavascriptExecutor js;
  private static Properties properties;
  
  //Pirms testu inicializācija
  @BeforeSuite
  public static void setUp() throws Exception {

	  
	  //Mape kurā atrodas logi un ekrānšāviņi:
	  
	  logFolderPath = "test-output\\Log";
	  new File(logFolderPath).mkdirs();	 
	  FileUtils.cleanDirectory(new File(logFolderPath));
	  
	  PrintStream out = new PrintStream(new FileOutputStream(logFolderPath + "\\output.log"), true, "UTF-8");
	  System.setOut(out);
	  

	  
	  driver = new FirefoxDriver();//Palaiž pārlūku
	  driver.manage().window().maximize();//Maksimizē logu
	  
	  //Dabūn current working directory priekš objectmap.properties faila
	  //Norāda objectmap.properties faila vietu
	  //objMap = new ObjectMap (workingDir+"\\UserFiles\\objectmap.properties");
	  properties = new Properties();
	  InputStreamReader Master = new InputStreamReader(new FileInputStream(workingDir+"\\UserFiles\\objectmap.properties"), StandardCharsets.UTF_8);
	  properties.load(Master);
	  Master.close();

	   
	  //Timeout uzstādījumi
	  driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  
	  //JavascripExecutor instance
	  js = (JavascriptExecutor) driver;	  

  }
  @BeforeTest
  public static void testSetUp(ITestContext testContext){
	  System.out.println("===============================================");
	  System.out.println("Testa \"" + testContext.getName() + "\" sākums");
	  System.out.println("");
  }
  
  @AfterTest
  public static void testEnd(ITestContext testContext){
	  System.out.println("");
	  System.out.println("Testa \"" + testContext.getName() + "\" beigas");
	  System.out.println("===============================================");
  }

  
  //Metode kas ielogojas 
  public static void LogIn() throws Exception{
	  webElement("banka1").click();
	  webElement("banka2").sendKeys("sia_dpa_gzalezalitis_test");
	  webElement("banka3").sendKeys(readFile(workingDir + "\\UserFiles\\user.pass", StandardCharsets.UTF_8));
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  try{
		  webElement("banka4").click();	 	
	  }
	  catch (Exception e){		 
	  }	 
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  select = new Select(webElement("banka5"));
	  //select.selectByVisibleText("sia_dpa_gzalezalitis_test Gatis Zāle-Zālītis (31078410838)");
	  select.selectByIndex(1);
  }
  
  //Izveido ekrānšāviņu neveiksmīga testa gadījumā
  @AfterMethod
  public static void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
	  if (testResult.getStatus() == ITestResult.FAILURE) {
		    
	  	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);  	
	  	FileUtils.copyFile(scrFile, new File(logFolderPath+"\\"+ testResult.getTestContext().getName() +".jpg"));
	  	
	  	System.out.println(testResult.getThrowable().getMessage());
	  } 
  }
  
  
  //Izsaucas pēc testiem

  @AfterSuite(alwaysRun = true)
  public static void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      Assert.fail(verificationErrorString);
    }
  }
  
  //Metode koda saīsināšanai
  public static WebElement webElement(String name) throws Exception{	  
	  //System.out.println("Meklē elementu "+ name);
	  return driver.findElement(getLocator(name));
  }
  
  //Šī metode salīdzina aktuālo epakalpojuma samaksu ar gaidāmo
  public static void salidzinatMaksu(String valstsNodeva, String publikacija) throws Exception{
	  
	  
	  
	  try{
		  System.out.println("Gaidāmā valsts nodeva: " + valstsNodeva + " Epakalpojumā redzamā: " +  webElement("valstsNodeva_Field").getText());
		  Assert.assertEquals(webElement("valstsNodeva_Field").getText(), valstsNodeva);
		  System.out.println("Gaidāmā publikācijas maksa: " + publikacija + " Epakalpojumā redzamā: " +  webElement("publikacija_Field").getText());
		  Assert.assertEquals(webElement("publikacija_Field").getText(), publikacija);
		  System.out.println("Samaksa par pakalpojumiem sakrīt ar gaidāmo!");
	  }catch (Exception e){
		  System.out.println("Samaksa par pakalpojumiem nesakrīt ar gaidāmo!");
		  throw e;
	  }

  }
  
  //Metode, kas visu faila saturu ievieto String
  public static String readFile(String path, Charset encoding) throws IOException 
  {
	  byte[] encoded = Files.readAllBytes(Paths.get(path));	  
	  return new String(encoded, encoding);
  }
  
  private static By getLocator(String ElementName) throws Exception {
      
      String locator = properties.getProperty(ElementName);
      
      String locatorType = locator.split(":")[0];
      String locatorValue = locator.split(":")[1];
      
        if(locatorType.toLowerCase().equals("id"))
              return By.id(locatorValue);
        else if(locatorType.toLowerCase().equals("name"))
              return By.name(locatorValue);
        else if((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
              return By.className(locatorValue);
        else if((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
              return By.className(locatorValue);
        else if((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
              return By.linkText(locatorValue);
        else if(locatorType.toLowerCase().equals("partiallinktext"))
              return By.partialLinkText(locatorValue);
        else if((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
              return By.cssSelector(locatorValue);
        else if(locatorType.toLowerCase().equals("xpath"))
              return By.xpath(locatorValue);
        else
                throw new Exception("Locator type '" + locatorType + "' not defined!!");
      }
  
}
