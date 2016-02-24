package testCases;

import java.nio.charset.StandardCharsets;

import org.testng.annotations.Test;

public class Test002 extends Base{
	@Test
	public void Kodols() throws Exception{
		  driver.get("https://lvptest.vraa.gov.lv/lv/Epakalpojumi/EP219?");	  	
		  LogIn();
		  EnterFrame();			  
		  ClickPiekritu();		  
		  
		  webElement("jaunsPakalpojums_Button").click();  		  
		  webElement("ePasts_Field2").sendKeys(readFile(workingDir + "\\UserFiles\\email", StandardCharsets.UTF_8));
		  
		  SelectApplicationTypeID(1);
		  SelectSubjetTypeID("K");
		  SelectSubjectLegalTypeID("SIAP");
		  GenerateRandomName();		  
		  webElement("talak_Button").click();	
		  
		  webElement("row0_FileUpload").sendKeys(workingDir + "\\UserFiles\\document.edoc");
		  webElement("row2_FileUpload").sendKeys(workingDir + "\\UserFiles\\document.edoc");
		  webElement("row3_FileUpload").sendKeys(workingDir + "\\UserFiles\\document.edoc");
		  webElement("row4_FileUpload").sendKeys(workingDir + "\\UserFiles\\document.edoc");
		  webElement("row5_FileUpload").sendKeys(workingDir + "\\UserFiles\\document.edoc");
		  
		  webElement("row12_FileUpload").sendKeys(workingDir + "\\UserFiles\\document.edoc");
		  webElement("talak_Button").click();
		  
		  salidzinatMaksu("128,06", "27,03");
		  
		  
	}

}
