package TestNG;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
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
import java.util.List;
import java.util.Properties;
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
  String workingDir = System.getProperty("user.dir"); 
  Select select;
  JavascriptExecutor js;
  Properties priceProperties;

  //Pirms testu inicializācija
  @BeforeClass
  public void setUp() throws Exception {

	  
	  //Mape kurā atrodas logi un ekrānšāviņi:
	  
	  folderPath = "test-output\\Log";
	  new File(folderPath).mkdirs();	 
	  FileUtils.cleanDirectory(new File(folderPath));
	  
	  PrintStream out = new PrintStream(new FileOutputStream(folderPath + "\\output.log"));
	  System.setOut(out);
	  

	  
	  driver = new FirefoxDriver();//Palaiž pārlūku
	  driver.manage().window().maximize();//Maksimizē logu
	  
	  //Dabūn current working directory priekš objectmap.properties faila
	  //Norāda objectmap.properties faila vietu
	  objMap = new ObjectMap (workingDir+"\\UserFiles\\objectmap.properties");
	  
	  //Ielādē failu ar gaidāmajām nodevu summām
	  InputStreamReader master = new InputStreamReader(new FileInputStream(workingDir+"\\UserFiles\\expectedPrice.properties"), StandardCharsets.UTF_8);
	  priceProperties = new Properties();
	  priceProperties.load(master);
	  master.close();
	   
	  //Timeout uzstādījumi
	  driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  
	  //javascript executor
	  js = (JavascriptExecutor) driver;
	  

  }
  
  @Test
  public void EP219Piemers() throws Exception{
	  driver.get("https://lvptest.vraa.gov.lv/lv/Epakalpojumi/EP219?");	  	
	  LogIn();
	  //Thread.sleep(10000);
	  
	  driver.switchTo().frame(0);//ep 119 web aplikācija ir iekš iframe
	  driver.switchTo().frame(0);//iekš vēlviena iframe
		

	  //E-pakalpojumā 219 visi checkboxi .css failā atzīmēti ar izmēru 100x100.
	  //Šī iemesla dēļ selenium nevar uz tiem uzklikšķināt
	  //Jāizmanto javascritp executor klase, kas aktivizē checkboksus "pa taisno"
	  js.executeScript("arguments[0].click();", webElement("ep219Piekritu_Check"));
	  
	  //webElement("ep219Piekritu_Check");
	  
	  webElement("talak_Button").click();
	  webElement("jaunsPakalpojums_Button").click();
	  webElement("ePasts_Field2").sendKeys(readFile(workingDir + "\\UserFiles\\email", StandardCharsets.UTF_8));
	  select = new Select(webElement("pieteikumaVeids_Combo"));
	  select.selectByVisibleText("Izmaiņu reģistrācija");
	  webElement("registracijasNumurs_Field").sendKeys("40103291885");
	  webElement("subjectCheck_Button").click();
	  
	  js.executeScript("arguments[0].click();", webElement("row0_Check"));
	  js.executeScript("arguments[0].click();", webElement("row7_Check"));
	  js.executeScript("arguments[0].click();", webElement("row9_Check"));
	  js.executeScript("arguments[0].click();", webElement("row12_Check"));
	  js.executeScript("arguments[0].click();", webElement("row13_Check"));
	  
	  webElement("close_Button").click();
	  webElement("jaunaisNosaukums_Field").sendKeys("Tests");
	  select = new Select(webElement("prokuras_Combo"));
	  select.selectByIndex(1);
	  
	  js.executeScript("arguments[0].click();", webElement("row17_Check"));
	  
	  webElement("talak_Button").click();
	  
	  //Failu augšuplāde - tā vietā, lai atvērtu augšupielādes logu, iespējams vienkārši padot faila 
	  //atrašanās vietu pa taisno input elementam string formātā
	  webElement("row0_FileUpload").sendKeys(workingDir + "\\UserFiles\\document.edoc");
	  webElement("row1_FileUpload").sendKeys(workingDir + "\\UserFiles\\document.edoc");
	  webElement("row2_FileUpload").sendKeys(workingDir + "\\UserFiles\\document.edoc");
	  webElement("row3_FileUpload").sendKeys(workingDir + "\\UserFiles\\document.edoc");
	  webElement("row4_FileUpload").sendKeys(workingDir + "\\UserFiles\\document.edoc");
	  webElement("row6_FileUpload").sendKeys(workingDir + "\\UserFiles\\document.edoc");
	  webElement("row10_FileUpload").sendKeys(workingDir + "\\UserFiles\\document.edoc");
		 
	  webElement("talak_Button").click();
	  
	  
	  //Veic salīdzināšanu vai summa sakrīt ar gaidāmo
	  salidzinatMaksu("ep219piemers_nodeva","ep219piemers_publikacija");
	  
	  
	  //Tiek pabeigts iesniegums UR
	  webElement("pievienotMaksajumaDatus_Button0").click();
	  webElement("vardsUzvards_Field").sendKeys("Tests Tests");
	  webElement("personasKodsVaiDzimsanasDatums_Field").sendKeys("111111-11111");
	  webElement("maksajumaDatums_Field").sendKeys("01.01.2016");
	  webElement("dokumentaNr_Field").sendKeys("1");
	  select = new Select(webElement("maksajumuPakalpojumuSniedzejs_Combo"));
	  select.selectByIndex(1);
	  webElement("saglabat_Button").click();
	  
	  webElement("pievienotMaksajumaDatus_Button1").click();
	  webElement("vardsUzvards_Field").sendKeys("Tests Tests");
	  webElement("personasKodsVaiDzimsanasDatums_Field").sendKeys("111111-11111");
	  webElement("maksajumaDatums_Field").sendKeys("01.01.2016");
	  webElement("dokumentaNr_Field").sendKeys("1");
	  select = new Select(webElement("maksajumuPakalpojumuSniedzejs_Combo"));
	  select.selectByIndex(1);
	  webElement("saglabat_Button").click();
	  
	  webElement("iesniegtUR_Button").click();
	  webElement("labi_Button").click();	  
  
  }
  
  //Šī metode atspējota kā testpiemērs
  //@Test
  public void EP119Piemers() throws Exception{   

	 driver.get("https://lvptest.vraa.gov.lv/lv/Epakalpojumi/EP119?");	  	
	 LogIn();
  		  
	 //Sagaida lietošanas nosacījumus 
	 Thread.sleep(10000);
	 
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
	 
	 
	 //Failu augšuplāde - tā vietā, lai atvērtu augšupielādes logu, iespējams vienkārši padot faila 
	 //atrašanās vietu pa taisno input elementam string formātā
	 webElement("augsupladet_Button1").sendKeys(workingDir + "\\UserFiles\\document.edoc");
	 webElement("augsupladet_Button2").sendKeys(workingDir + "\\UserFiles\\document.edoc");
	 webElement("augsupladet_Button3").sendKeys(workingDir + "\\UserFiles\\document.edoc");
	 Thread.sleep(10000);
	 webElement("talak_Button").click();
	 
	 webElement("vards_Field2").sendKeys("Tests");
	 webElement("uzvards_Field2").sendKeys("Tests");
	 webElement("personasKods_Field2").sendKeys("111111-11111");
	 webElement("parakstisanasVieta_Field2").sendKeys("Tests");
	 webElement("ePastaAdrese_Field2").sendKeys("vards@epasts.lv");
	 webElement("talak_Button").click();
	 webElement("talak_Button").click();
	 
	 //Šādi tiek pārbaudīts, vai lapā ir teksts "Jums nav tiesību parakstīt pieteikumu!"
	 List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Jums nav tiesību parakstīt pieteikumu!')]"));
	 Assert.assertTrue(list.size() > 0);
	 Thread.sleep(10000);
	 
	 
  }
  
  //Metode kas ielogojas 
  private void LogIn() throws Exception{
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
	  select.selectByVisibleText("sia_dpa_gzalezalitis_test Gatis Zāle-Zālītis (31078410838)");
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
  
  //Šī metode salīdzina aktuālo epakalpojuma samaksu ar gaidāmo
  private void salidzinatMaksu(String valstsNodeva, String publikacija) throws Exception{
	  try{
		  System.out.println("Gaidāmā valsts nodeva: " + priceProperties.getProperty(valstsNodeva) + " Epakalpojumā redzamā: " +  webElement("valstsNodeva_Field").getText());
		  Assert.assertEquals(webElement("valstsNodeva_Field").getText(), priceProperties.getProperty(valstsNodeva));
		  System.out.println("Gaidāmā publikācijas maksa: " + priceProperties.getProperty(publikacija) + " Epakalpojumā redzamā: " +  webElement("publikacija_Field").getText());
		  Assert.assertEquals(webElement("publikacija_Field").getText(), priceProperties.getProperty(publikacija));
		  System.out.println("Samaksa par pakalpojumiem sakrīt ar gaidāmo!");
	  }catch (Exception e){
		  System.out.println("Samaksa par pakalpojumiem nesakrīt ar gaidāmo!");
		  throw e;
	  }

  }
  
  //Metode, kas visu faila saturu ievieto String
  static String readFile(String path, Charset encoding) throws IOException 
  {
	  byte[] encoded = Files.readAllBytes(Paths.get(path));	  
	  return new String(encoded, encoding);
  }
  
  
}
