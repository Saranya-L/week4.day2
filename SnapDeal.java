package week4.day2;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SnapDeal {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		/*	
	  		1. Launch https://www.snapdeal.com/
			2. Go to Mens Fashion
			3. Go to Sports Shoes
			4. Get the count of the sports shoes
			5. Click Training shoes
			6. Sort by Low to High
			7. Check if the items displayed are sorted correctly
			8. Select the price range (900-1200)
			9. verify the all applied filters 
			10. Mouse Hover on first resulting Training shoes
			11. click QuickView button
			12. Print the cost and the discount percentage
			13. Take the snapshot of the shoes.
			14. Close the current window
			15. Close the main window
		 */


		// We have to call WDM for the browser driver !!
		WebDriverManager.chromedriver().setup(); // verify the version, download, set up !

		// Handle Browser notifications
		ChromeOptions options = new ChromeOptions();

		// Notfications
		options.addArguments("--disable-notifications");

		// Headless -- invisible !
		//options.setHeadless(true);

		// Launch the browser (chrome)
		ChromeDriver driver = new ChromeDriver(options);
		//Load the url
		driver.get("https://www.snapdeal.com/");
		//Maximize the browser
		driver.manage().window().maximize();
		//implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		//Go to Mens Fashion
		WebElement mens=driver.findElement(By.xpath("(//a[@class='menuLinks leftCategoriesProduct ']/span[2])[1]"));
		Actions mensfashion=new Actions(driver);
		mensfashion.moveToElement(mens).perform();
		//Go to Sports Shoes
		driver.findElement(By.xpath("//span[text()='Sports Shoes']")).click();

		//Get the count of the sports shoes
		WebElement sports=driver.findElement(By.xpath("//span[@class='category-name category-count']"));
		String shoes=sports.getText().replace("(", "").replace(" ", "").replace(")", "").replace("Items", "");
		int count=Integer.parseInt(shoes);
		System.out.println("Total sports shoes available are "+count);

		//Click Training shoes
		driver.findElement(By.xpath("//div[text()='Training Shoes']")).click();
		//Sort by Low to High
		driver.findElement(By.xpath("//span[text()='Sort by:']")).click();
		driver.findElement(By.xpath("(//li[@data-index='1'])[2]")).click();
		Thread.sleep(3000);
		//Check if the items displayed are sorted correctly
		List<WebElement> items=driver.findElements(By.xpath("//span[@class='lfloat product-price']"));
		List<Integer> price=new ArrayList<Integer>();
		//for placing the price in list<Integer>
		String lowtohigh="";
		
		for(int i=0;i<items.size();i++) 
		{
			lowtohigh=items.get(i).getText().replace("Rs.", "").replace(" ", "").replace(",", "");
			int cost=Integer.parseInt(lowtohigh);
			price.add(cost);
			
		}
		//checking the sorted order
		int size=0;
		for(int i=0;i<(items.size())-1;i++) 
		{
			if(price.get(i)<=price.get(i+1))
			{
				size=size+1;
			}else
			{
				System.out.println("List is not in sorted order");
				System.out.println("present number "+price.get(i));
				System.out.println("Next number "+price.get(i+1));
			}

		}
		//System.out.println(size);
		//System.out.println((items.size())-1);
		//Select the price range (900-1200)
		WebElement from=driver.findElement(By.xpath("//input[@name='fromVal']"));
		from.clear();
		from.sendKeys(String.valueOf(900),Keys.ENTER);
		WebElement to=driver.findElement(By.xpath("//input[@name='toVal']"));
		to.clear();
		to.sendKeys(String.valueOf(1200),Keys.ENTER);
		Thread.sleep(3000);
		WebElement go=driver.findElement(By.xpath("//div[@class='price-go-arrow btn btn-line btn-theme-secondary']"));
		go.click();
		
		//verify the all applied filter
		List<WebElement> item=driver.findElements(By.xpath("//div[@class='filters']/div[@class='navFiltersPill']"));
		List<WebElement> name=driver.findElements(By.xpath("//div[@class='filters']/div[@class='navFiltersPill']/a"));
		for(int i=0;i<item.size();i++)
		{
			System.out.println(item.get(i).getText());
			System.out.println(name.get(i).getText());
		}
		//Mouse Hover on first resulting Training shoes
		WebElement firstelement=driver.findElement(By.xpath("(//p[@class='product-title'])[1]"));
		Actions forview=new Actions(driver);
		forview.moveToElement(firstelement).perform();
		
		Thread.sleep(3000);
		WebElement trainingshoes=driver.findElement(By.xpath("(//div[@class='center quick-view-bar  btn btn-theme-secondary  '])[1]"));
		trainingshoes.click();
		//Print the cost and the discount percentage
		WebElement cost=driver.findElement(By.xpath("//span[@class='strikee ']"));
		String actual=cost.getText().replace("Rs.","").replace(" ","").replace(",", "");
		System.out.println("Actual cost : "+actual);
		WebElement discount=driver.findElement(By.xpath("//span[@class='payBlkBig']"));
		String discountcost=discount.getText().replace(" ","");
		System.out.println("Discount cost : "+discountcost);
		
		 // Take a snapshot 
		 File source = driver.getScreenshotAs(OutputType.FILE);
		
		 //You need to save it to your local dir
		 File dest = new File("./Snapshots/snapdeal.png");
		 FileUtils.copyFile(source, dest);
		 Thread.sleep(3000);
		 //Close the current window
		 driver.findElement(By.xpath("(//i[@class='sd-icon sd-icon-delete-sign'])[3]")).click();
		 driver.quit();
				
	}

}
