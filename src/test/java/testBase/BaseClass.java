package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.google.common.io.Files;

public class BaseClass
{
	public static WebDriver driver;
	public Logger logger;
	protected Properties p;
	
	
	
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os, String browser) throws IOException
	{
		logger=LogManager.getLogger(this.getClass());//Log4j
		
		switch(browser.toLowerCase())
		{
			case "chrome"  : driver=new ChromeDriver(); break;
			case "edge" : driver = new EdgeDriver();break;
			case "firefox" : driver = new FirefoxDriver(); break;
			default : System.out.println("Invalid Browser"); return; 
			// return --> exit from the entire loop - if browser only invalid then no need to execute remaining steps
		}
		
		//loading congig.properties file
		FileInputStream file = new FileInputStream("./src/test/resources/config.properties");
		p=new Properties();
		p.load(file);
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("url"));
		driver.manage().window().maximize();
	}
	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown()
	{
		driver.quit();
	}
	
	public String randomeString()
	{
		String generatedString=RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	public String randomeNumber()
	{
		String generatedString=RandomStringUtils.randomNumeric(10);
		return generatedString;
	}
	
	public String randomAlphaNumeric()
	{
		String str=RandomStringUtils.randomAlphabetic(3);
		String num=RandomStringUtils.randomNumeric(3);
		
		return (str+"@"+num);
	}
	
	public static void captureScreenshot(String fileName) throws IOException 
	{
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        File dest = new File("./screenshots/" + fileName + ".png");
        Files.copy(src, dest);
    }
}
