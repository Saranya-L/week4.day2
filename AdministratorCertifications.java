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

public class AdministratorCertifications {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
			1. Launch Salesforce application https://login.salesforce.com/
			2. Login with username as "ramkumar.ramaiah@testleaf.com " and password as "Password$123"
			3. Click on Learn More link in Mobile Publisher
			4. Click confirm on Confirm redirect
			5. Click Resources and mouse hover on Learning On Trailhead
			6. Click on Salesforce Certifications
			6. Click on Ceritification Administrator
			7. Navigate to Certification - Administrator Overview window
			8. Verify the page displays Administrator
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
		
		//Click Resources and mouse hover on Learning On Trailhead
		Shadow dom=new Shadow(driver);
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		WebElement learning=dom.findElementByXPath("//span[text()='Learning']");
		wait.until(ExpectedConditions.visibilityOf(learning));
		learning.click();
		WebElement learn=dom.findElementByXPath("//span[text()='Learning on Trailhead']");
		wait.until(ExpectedConditions.visibilityOf(learn));
		Actions builder=new Actions(driver);
		
		builder.moveToElement(learn).perform();
		//Click on Salesforce Certifications
		WebElement certificate=dom.findElementByXPath("//a[text()='Salesforce Certification']");
		Actions builder1=new Actions(driver);
		builder1.scrollToElement(certificate).perform();
		certificate.click();
		//Click on Ceritification Administrator
		driver.findElement(By.linkText("Administrator")).click();
		
		//Navigate to Certification - Administrator Overview window
		String title=driver.getTitle();
		System.out.println("We are in "+title+" window.");
		
		//Verify the page displays Administrator
		String head=driver.findElement(By.xpath("//div[@class='certification-banner_title slds-container--medium slds-container--center slds-text-align--center']")).getText();
		System.out.println("Page heading is "+head);


	}

}
