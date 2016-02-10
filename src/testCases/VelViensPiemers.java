package testCases;

import org.testng.annotations.Test;

public class VelViensPiemers extends Base{

	@Test
	public void Kodols() throws Exception{
		Base.driver.get("https://lvptest.vraa.gov.lv/lv/Epakalpojumi/EP119?");
		Thread.sleep(5000);
		throw new Exception("Neveiksme");
	}
}
