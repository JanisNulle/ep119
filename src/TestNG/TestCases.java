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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
//import java.time.LocalDateTime;
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

	  //LocalDateTime now = LocalDateTime.now();	  
	  
	  //Mape kurā atrodas logi un ekrānšāviņi:
	  //folderPath = "test-output\\Log\\"+now.getYear()+"-"+now.getMonth()+"-"+now.getDayOfMonth()
	  //+" "+now.getHour()+"-"+now.getMinute()+"-"+now.getSecond();

	  
	  folderPath = "test-output\\Log";
	  new File(folderPath).mkdirs();	 
	  FileUtils.cleanDirectory(new File(folderPath));
	  
	  PrintStream out = new PrintStream(new FileOutputStream(folderPath + "\\output.log"));
	  System.setOut(out);
	  

	  
	  driver = new FirefoxDriver();//Palaiž pārlūku
	  driver.manage().window().maximize();//Maksimizē logu
	  
	  //Dabūn current working directory priekš objectmap.properties faila
	  String workingDir=System.getProperty("user.dir");
	  //Norāda objectmap.properties faila vietu
	  objMap = new ObjectMap (workingDir+"\\ObjectMapProperties\\objectmap.properties");
	   
	  //Timeout uzstādījumi
	  driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
  
  
  @Test
  public void Test1() throws Exception{   
	 String workingDir=System.getProperty("user.dir"); 
	  
	 driver.get("https://lvptest.vraa.gov.lv/lv/Epakalpojumi/EP119");

	 webElement("banka1").click();
	 webElement("banka2").sendKeys("sia_dpa_gzalezalitis_test");
	 webElement("banka3").sendKeys(readFile(workingDir + "\\user.pass", StandardCharsets.UTF_8));
	 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	 try{
		 webElement("banka4").click();	 	
	 }
	 catch (Exception e){		 
	 }	 
	 driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	 Select select = new Select(webElement("banka5"));
	 select.selectByIndex(1);
	 
	 
	 driver.switchTo().frame(0);//ep 119 web aplikācija ir iekš iframe
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
	 //webElement("9_Check").click();
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
	 select = new Select(webElement("novads_DropDown"));
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
	 /*webElement("akcijuSkaits_Field").sendKeys("2");
	 webElement("akcijasNominalvertiba_Field").sendKeys("2");
	 webElement("pamatkapitalaLielums_Field").sendKeys("2");
	 webElement("apmaksataisPamatkapitals_Field").sendKeys("2");
	 
	 webElement("apliecinuEuro_Check").click();
	 webElement("talak_Button").click();*/
	 
	 //Datuma ievade
	 webElement("datums_Field").sendKeys("31.12.2025.");
	 webElement("talak_Button").click();
	 driver.switchTo().alert().accept();//uzlecošs paziņojums
	 webElement("talak_Button").click();
	 
	 webElement("apliecinu_Check").click();
	 webElement("talak_Button").click();
	 
	 //Failu augšuplāde
	 webElement("augsupladet_Button1").sendKeys(workingDir + "\\document.edoc");
	 webElement("augsupladet_Button2").sendKeys(workingDir + "\\document.edoc");
	 webElement("augsupladet_Button3").sendKeys(workingDir + "\\document.edoc");
	 webElement("talak_Button").click();
	 /*webElement("augsupladet_Button1").click();
	 webElement("augsupladet_Dialogue").sendKeys(workingDir + "\\document.edoc");
	 webElement("augsupladet_Button2").click();
	 webElement("augsupladet_Dialogue").sendKeys(workingDir + "\\document.edoc");
	 webElement("augsupladet_Button3").click();
	 webElement("augsupladet_Dialogue").sendKeys(workingDir + "\\document.edoc");*/
	     
  }
  
  //Izveido ekrānšāviņu neveiksmīga testa gadījumā
  @AfterMethod
  public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
	  if (testResult.getStatus() == ITestResult.FAILURE) {
	  	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	  	FileUtils.copyFile(scrFile, new File(folderPath+"\\"+ testResult.getName() +".jpg"));
	  	
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
	  //System.out.println("Meklē elementu "+ name);
	  return driver.findElement(objMap.getLocator(name));
  }
  
  //Metode, kas visu faila saturu ievieto String
  static String readFile(String path, Charset encoding) throws IOException 
  {
	  byte[] encoded = Files.readAllBytes(Paths.get(path));	  
	  return new String(encoded, encoding);
  }

}
