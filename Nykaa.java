package week4.day2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Nykaa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
			1) Go to https://www.nykaa.com/
			2) Mouseover on Brands and Search L'Oreal Paris
			3) Click L'Oreal Paris
			4) Check the title contains L'Oreal Paris(Hint-GetTitle)
			5) Click sort By and select customer top rated
			6) Click Category and click Hair->Click haircare->Shampoo
			7) Click->Concern->Color Protection
			8) check whether the Filter is applied with Shampoo
			9) Click on L'Oreal Paris Colour Protect Shampoo
			10) GO to the new window and select size as 175ml
			11) Print the MRP of the product
			12) Click on ADD to BAG
			13) Go to Shopping Bag 
			14) Print the Grand Total amount
			15) Click Proceed
			16) Click on Continue as Guest
			17) Check if this grand total is the same in step 14
			18) Close all windows
		 */
		WebDriverManager.edgedriver().setup();
		EdgeDriver driver = new EdgeDriver();
		
		//Load the url
		driver.get("https://www.nykaa.com/");
		//Maximize the browser
		driver.manage().window().maximize();
		//implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		//Mouseover on Brands and Search L'Oreal Paris
		WebElement brands=driver.findElement(By.xpath("//a[text()='brands']"));
		Actions builder=new Actions(driver);
		builder.moveToElement(brands).perform();
		//Search L'Oreal Paris
		driver.findElement(By.id("brandSearchBox")).sendKeys("L'Oreal Paris");
		//Click L'Oreal Paris
		driver.findElement(By.linkText("L'Oreal Paris")).click();

		//Check the title contains L'Oreal Paris(Hint-GetTitle)
		String title=driver.getTitle();

		if(title.contains("L'Oreal Paris"))
		{
			System.out.println("Title contains L'Oreal Paris and title is "+title);
		}else
		{
			System.out.println("Title does not contain L'Oreal Paris and title is "+title);
		}
		//Click sort By and select customer top rated
		driver.findElement(By.xpath("//span[@class='sort-name']")).click();
		driver.findElement(By.xpath("//span[text()='customer top rated']")).click();

		//Click Category and click Hair->Click haircare->Shampoo
		driver.findElement(By.xpath("//span[text()='Category']")).click();
		WebElement hair=driver.findElement(By.xpath("//span[text()='Hair']"));
		try
		{
			hair.click();
		}catch(StaleElementReferenceException e)
		{
			System.out.println("Failed due to stale element exception");
			driver.findElement(By.xpath("//span[text()='Hair']")).click();
		}
		driver.findElement(By.xpath("//span[text()='Hair Care']")).click();
		driver.findElement(By.xpath("//span[text()='Shampoo']")).click();

		//Click->Concern->Color Protection
		driver.findElement(By.xpath("//span[text()='Concern']")).click();
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		WebElement protect=driver.findElement(By.xpath("//span[text()='Color Protection']"));
		wait.until(ExpectedConditions.visibilityOf(protect));
		protect.click();
		//check whether the Filter is applied with Shampoo
		List<WebElement> filter=driver.findElements(By.xpath("//span[@class='filter-value']"));
		for (WebElement each : filter) {
			String filtervalue=each.getText();
			if(filtervalue.contains("Shampoo"))
			{
				System.out.println("Filter is applied with Shampoo");
			}
		}
		//Click on L'Oreal Paris Colour Protect Shampoo
		driver.findElement(By.xpath("//div[contains(text(),'Paris Colour Protect Shampoo')]")).click();
		
		//GO to the new window and select size as 175ml
		Set<String> window=driver.getWindowHandles();
		List<String> windows=new ArrayList<String>(window);
		driver.switchTo().window(windows.get(1));
		
		driver.findElement(By.xpath("//select[@title='SIZE']")).sendKeys("175");
		//Print the MRP of the product 
		String mrp=driver.findElement(By.xpath("//span[@class='css-1jczs19']")).getText();
		
		System.out.println("MPR of the product is "+mrp);
		//Click on ADD to BAG
		driver.findElement(By.xpath("//span[text()='Add to Bag']")).click();
		//Go to Shopping Bag 
		WebElement bag=driver.findElement(By.xpath("//button[@class='css-g4vs13']"));
		wait.until(ExpectedConditions.visibilityOf(bag));
		bag.click();
		//Print the Grand Total amount
		driver.switchTo().frame(0);
		WebElement amount=driver.findElement(By.xpath("(//span[text()='Grand Total']/following::div)[1]"));
		String total=amount.getText().replaceAll("₹", "");
		int grandTotal=Integer.parseInt(total);
		System.out.println("Grand total : "+grandTotal);
		
		//Click Proceed
		driver.findElement(By.xpath("//span[text()='Proceed']")).click();
		
		//Click on Continue as Guest
		driver.findElement(By.xpath("//button[@class='btn full big']")).click();
		
		//Check if this grand total is the same in step 14
		String checkoutsum=driver.findElement(By.xpath("(//div[@class='value']/span)[2]")).getText();
		int grandSum=Integer.parseInt(checkoutsum);
		if(grandTotal==grandSum)
		{
			System.out.println("Grand Total and Grand Sum are equal");
		}else
		{
			System.out.println("Grand Total is "+grandTotal+" and Grand Sum is "+grandSum);
		}
		
		driver.quit();
		

	}

}
