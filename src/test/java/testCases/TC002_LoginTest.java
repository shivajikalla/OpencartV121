package testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass
{
	@Test(groups={"Regression", "Master"})
	public void verify_Login() throws IOException
	{
		
		logger.info("**********Starting TC002_LoginTest ************ ");
		try
		{
		//HomePage
		HomePage hp= new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(p.getProperty("username"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		logger.info("**********successfully login ************ ");
		
		//MyAccountPage
		MyAccountPage macc = new MyAccountPage(driver);
		boolean status=macc.isMyAccountPageExists();
		Assert.assertTrue(status); //(or) Assert.assertEquals(status, true, "isMyAccountPageExists is failed");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		
		
		// Take screenshot after test success
		captureScreenshot("MyAccountPage");
		Reporter.log("Screenshot taken successfully",true);
		
		logger.info("**********Finshed TC002_LoginTest ************ ");
	}

}
