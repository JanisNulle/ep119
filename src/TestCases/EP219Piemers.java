package TestCases;

import java.nio.charset.StandardCharsets;

import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

public class EP219Piemers extends Base{
	
	@Test
	public void Kodols() throws Exception{
		  driver.get("https://lvptest.vraa.gov.lv/lv/Epakalpojumi/EP219?");	  	
		  LogIn();
		  //Thread.sleep(10000);
		  
		  driver.switchTo().frame(0);//EP219 web aplikācija ir iekš iframe
		  driver.switchTo().frame(0);//iekš vēlviena iframe
			

		  //E-pakalpojumā 219 visi checkboxi .css failā atzīmēti ar izmēru 100x100,
		  //taču pārlūkā attēlojas parastā izmērā.
		  //Šī iemesla dēļ Selenium nevar uz tiem uzklikšķināt.
		  //Jāizmanto JavascriptExecutor klase, kas aktivizē checkboksus "pa taisno".
		  js.executeScript("arguments[0].click();", webElement("ep219Piekritu_Check"));
		  
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
		  salidzinatMaksu("32,01","9,25");
		  
		  
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

}
