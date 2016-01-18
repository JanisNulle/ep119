package TestNG;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;


public class TestCases {
  private WebDriver driver;
  //WebDriverWait wait;
  
  private StringBuffer verificationErrors = new StringBuffer();
  public ObjectMap objMap;
  String folderPath;  

  //Pirms testu inicializācija
  @BeforeClass
  public void setUp() throws Exception {
	  
	  //bup

	  LocalDateTime now = LocalDateTime.now();	  
	  
	  //Mape kurā atrodas logi un ekrānšāviņi:
	  folderPath = "test-output\\Log\\"+now.getYear()+"-"+now.getMonth()+"-"+now.getDayOfMonth()
	  +" "+now.getHour()+"-"+now.getMinute()+"-"+now.getSecond();
	  new File(folderPath).mkdirs();	  
	  PrintStream out = new PrintStream(new FileOutputStream(folderPath + "\\output.log"));
	  System.setOut(out);
 
	  driver = new FirefoxDriver();//Palaiž pārlūku
	  driver.manage().window().maximize();//Maksimizē logu
	  //driver.get("https://lvptest.vraa.gov.lv");//Atver doto adresi	  
	  
	  //Dabūn current working directory priekš objectmap.properties faila
	  String workingDir=System.getProperty("user.dir");
	  //Norāda objectmap.properties faila vietu
	  objMap = new ObjectMap (workingDir+"\\ObjectMapProperties\\objectmap.properties",driver);
	   
	  //Timeout uzstādījumi
	  driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
  
  
  @Test
  public void Test1() throws Exception{   
	 try{ 
	  
	  
	 driver.get("https://lvptest.vraa.gov.lv/lv/Epakalpojumi/EP119");

	 webElement("banka1").click();
	 webElement("banka2").sendKeys("1399372");
	 webElement("banka4").click();
	 Thread.sleep(10000);
	 webElement("banka3").click();	 	

	 
	 driver.switchTo().frame(0);//aplikācija ir iekš iframe
	 driver.switchTo().frame(0);//iekš vēlviena iframe
	 
	 webElement("piekritu_CheckBox").click();
	 webElement("talak_Button").click();

	 
	 webElement("jaunsRegistracijasPieteikums_Button").click();
	 webElement("izmainuRegistracija_Button").click();
	 webElement("nosaukums_Field").sendKeys("SIA");
	 webElement("meklet_Button").click();
	 webElement("izveleties_Radio").click();
	 webElement("talak_Button").click();
	 webElement("pieteikumsIzmainamKomercregistra_Link").click();
	 webElement("ePasts_Field").sendKeys("vards@epasts.lv");
	 webElement("talak_Button").click();
	 webElement("talak_Button").click();
	 
	 //Atķeksē kastītes
	 webElement("0_Check").click();
	 webElement("7_Check").click();
	 webElement("9_Check").click();
	 webElement("10_Check").click();
	 
	 webElement("talak_Button").click();
	 
	 webElement("0_Check").click();
	 webElement("3_Check").click();
	 webElement("4_Check").click();
	 
	 webElement("talak_Button").click();
	 
	 //Sākas izmaiņas
	 webElement("1_Radio").click();
	 webElement("12_Check").click(); 
	 webElement("ieladetManusDatus_Button").click();
	 
	 webElement("011_Check").click();
	 
	 webElement("vards_Field").sendKeys("Tests");
	 webElement("uzvards_Field").sendKeys("Tests");
	 webElement("personasKods_Field").sendKeys("111111-11111");
	 webElement("parakstisanasVieta_Field").sendKeys("Tests");
	 webElement("ePastaAdrese_Field").sendKeys("vards@epasts.lv");
	 
	 webElement("talak_Button").click();
	 
	 driver.switchTo().alert().accept();//uzlecošs paziņojums
	 webElement("talak_Button").click();
	 
	 webElement("0_Radio").click();
	 webElement("talak_Button").click();
	 
	 //Jauna juridiskā adrese
	 Select select = new Select(webElement("novads_DropDown"));
	 select.selectByVisibleText("Ādažu nov.");
	 select = new Select(webElement("ciems_DropDown"));
	 select.selectByVisibleText("Ādaži");
	 select = new Select(webElement("iela_DropDown"));
	 select.selectByVisibleText("Alīdas iela");
	 Thread.sleep(5000);
	 
	 select = new Select(webElement("numurs_DropDown"));
	 select.selectByVisibleText("1");
	 
	 webElement("talak_Button").click();
	 
	 
	 //Jauns nosaukums SIA
	 webElement("siaNosaukums_Field").sendKeys("SIA Tests");
	 webElement("talak_Button").click();
	 driver.switchTo().alert().accept();//uzlecošs paziņojums
	 webElement("talak_Button").click();
	 
	 //Prokūra izmaiņas
	 webElement("0_Radio").click();
	 webElement("ieladetManusDatus_Button").click();
	 webElement("07_Radio").click();
	 webElement("010_Radio").click();
	 webElement("talak_Button").click();
	 driver.switchTo().alert().accept();//uzlecošs paziņojums
	 webElement("talak_Button").click();
	 
	 //Pāreja uz EUR
	 webElement("akcijuSkaits_Field").sendKeys("2");
	 webElement("akcijasNominalvertiba_Field").sendKeys("2");
	 webElement("pamatkapitalaLielums_Field").sendKeys("2");
	 webElement("apmaksataisPamatkapitals_Field").sendKeys("2");
	 
	 webElement("apliecinuEuro_Check").click();
	 webElement("talak_Button").click();
	 
	 }
	 catch (Exception e){
			 
		 StringWriter sw = new StringWriter();
		 PrintWriter pw = new PrintWriter(sw);
		 e.printStackTrace(pw);
		 System.out.println(sw.toString()); 
		 
		 throw e;
	 }
     //Thread.sleep(10000);   
     
  }
  
  //Izveido ekrānšāviņu neveiksmīga testa gadījumā
  @AfterMethod
  public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
	  if (testResult.getStatus() == ITestResult.FAILURE) {
	  	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	  	FileUtils.copyFile(scrFile, new File(folderPath+"\\testScreenShot.jpg"));
	  	
	  	System.out.println(testResult.getThrowable().getMessage());
	  } 
  }
  
  
  //Izsaucas pēc testiem

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      Assert.fail(verificationErrorString);
    }
  }
  
  //Metode koda saīsināšanai
  WebElement webElement(String name) throws Exception{	    
	  return objMap.webElement(name);
  }


}
