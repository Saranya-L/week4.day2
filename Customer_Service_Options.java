package week4.day2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.sukgu.Shadow;

public class Customer_Service_Options {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
			1. Launch Salesforce application https://login.salesforce.com/
			2. Login with Provided Credentials
			3. Click on Learn More link in Mobile Publisher
			4. Clilck on Products and Mousehover on Service 
			5. Click on Customer Services
			6. Get the names Of Services Available 
			7. Verify the title
		 */
		WebDriverManager.edgedriver().setup();
		EdgeDriver driver = new EdgeDriver();
		//Load the url
		driver.get("https://login.salesforce.com/");
		//Maximize the browser
		driver.manage().window().maximize();
		//implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		//Enter the username and password,click on login button
		driver.findElement(By.id("username")).sendKeys("ramkumar.ramaiah@testleaf.com");
		driver.findElement(By.id("password")).sendKeys("Password$123");
		driver.findElement(By.id("Login")).click();
		
		//click on the learn more option in the Mobile publisher
		driver.findElement(By.xpath("//span[text()='Learn More']")).click();
		
		//Switch to the next window using Windowhandle
		Set<String> windows=driver.getWindowHandles();
		List<String> list=new ArrayList<String>(windows);
		driver.switchTo().window(list.get(1));
		
		//click on the confirm button in the redirecting page
		driver.findElement(By.xpath("//button[text()='Confirm']")).click();
		
		//Clilck on Products and Mousehover on Service
		Shadow dom=new Shadow(driver);
		WebElement products=dom.findElementByXPath("//span[text()='Products']");
		products.click();
		WebElement service=dom.findElementByXPath("//span[text()='Service']");
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(service));
		Actions builder=new Actions(driver);
		
		builder.moveToElement(service).perform();
		
		//Get the names Of Services Available
		List<WebElement> services=dom.findElementsByXPath("//ul[@class='c360-panel-linkedlist__listitems']/li/h4/a[contains(text(),'Service')]");
		System.out.println("Services Available : ");
		for(int i=0;i<services.size();i++)
		{
			String names=services.get(i).getText();
			System.out.println(names);
		}
		
		//Verify the title
		String title=driver.getTitle();
		System.out.println("Title is "+title);
	}

}
