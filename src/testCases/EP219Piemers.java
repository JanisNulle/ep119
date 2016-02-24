package testCases;

import java.nio.charset.StandardCharsets;

import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

public class EP219Piemers extends Base{
	
	@Test
	public void Kodols() throws Exception{
		  driver.get("https://lvptest.vraa.gov.lv/lv/Epakalpojumi/EP219?");	  	
		  LogIn();
		  EnterFrame();			  
		  ClickPiekritu();		  
		  
		  webElement("jaunsPakalpojums_Button").click();  		  
		  webElement("ePasts_Field2").sendKeys(readFile(workingDir + "\\UserFiles\\email", StandardCharsets.UTF_8));
		  
		  SelectApplicationTypeID(2);
		  
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
		  
		  IesniegtPieteikumuUR();
	
	}

}
