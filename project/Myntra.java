package week4.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Myntra {

	@Test
	public void main() throws InterruptedException, IOException {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.myntra.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// To mouse over on men and click
		Actions builder = new Actions(driver);
		WebElement webMen = driver.findElement(By.className("desktop-navLink"));
		builder.moveToElement(webMen).perform();

		// to click on jackets
		driver.findElement(By.xpath("//a[text()='Jackets']")).click();
		// driver.findElement(By.linkText("Jackets")).click();

		// to get the title count
		WebElement Tcount = driver.findElement(By.className("title-count"));
		String count = Tcount.getText();
		String Ncount = count.replaceAll("[\\D]", "");
		System.out.println("The Total number of counts :" + Ncount);
		int totalCount = Integer.parseInt(Ncount);

		// to get count of jacket
		WebElement Jcount = driver.findElement(By.xpath("//span[@class='categories-num']"));
		String count2 = Jcount.getText();
		String Ncount2 = count2.replaceAll("[\\D]", "");
		System.out.println("The number of Jackets count :" + Ncount2);
		int jacketCount = Integer.parseInt(Ncount2);

		// to get rain jacket count
		String RJcount = driver.findElement(By.xpath("(//span[@class='categories-num'])[2]")).getText();
		String Ncount3 = RJcount.replaceAll("[\\D]", "");
		System.out.println("The number of Rain Jackets count : " + Ncount3);
		int rainJacketCount = Integer.parseInt(Ncount3);
		// calculation
		int temp = jacketCount + rainJacketCount;
		System.out.println("Adding jackets and rain jackets count :" + temp);
		// check whether categories matches with the total count
		if (temp == totalCount) {
			System.out.println("Categories matches with the total count");

		} else {
			System.out.println("Categories matches not with the total count");

		}
		driver.findElement(By.className("common-checkboxIndicator")).click();
		driver.findElement(By.xpath("//div[@class='brand-more']")).click();
		driver.findElement(By.xpath("//input[@class='FilterDirectory-searchInput']")).sendKeys("Duke");
		driver.findElement(By.xpath("//span[@class='FilterDirectory-count']/following-sibling::div")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[@class='myntraweb-sprite FilterDirectory-close sprites-remove']")).click();

		//To verify all the products are duke
		List<WebElement> findElements = driver.findElements(By.xpath("//h3[text()='Duke']"));
		Set<String> duke = new LinkedHashSet<String>();
		boolean flag = false;
		
		for (WebElement forDuke : findElements) {
			String sDuke = forDuke.getText();
			duke.add(sDuke);
			boolean contains = !duke.contains("Duke");
			if (contains == true) {
				System.out.println(sDuke + "this is not duke brand, conditions :" + contains);
				flag = true;
			}

		}
		if (flag == false) {
			System.out.println("All the brands are Duke");
		}

		
		WebElement sort = driver.findElement(By.className("sort-sortBy"));
		builder.moveToElement(sort).perform();
		driver.findElement(By.xpath("//input[@value='discount']/parent::label")).click();
	
		WebElement firstPrdt = driver.findElement(By.xpath("//span[@class='product-discountedPrice']"));
		String text = firstPrdt.getText();
		System.out.println("The price of the first displayed product :"+text  );
		firstPrdt.click();
		

		//To move to the next window
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> win = new ArrayList<String>(windowHandles);	
		
		driver.switchTo().window(win.get(1));
		Thread.sleep(5000);
		driver.findElement(By.xpath("//span[text()='WISHLIST']")).click();
		//To take Screeonshot
		File src1 = driver.getScreenshotAs(OutputType.FILE);
		File Dst = new File("./snaps/duke.png");
		FileUtils.copyFile(src1,Dst);


	}

}
