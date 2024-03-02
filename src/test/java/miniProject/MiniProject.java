package miniProject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class MiniProject {

	public static void main(String[] args) throws InterruptedException {
		//Get the browser name
		System.out.println("Enter the browser name (Chrome/Edge) :");
		WebDriver driver = initiateDriver();
	
		// Launch the URL
		driver.get("https://www.rediff.com/");
		driver.findElement(By.linkText("Create Account")).click();
		TimeUnit.SECONDS.sleep(2);
		
		//call the parent validation method to validate the title of the web page
		parentValidation(driver); 
		TimeUnit.SECONDS.sleep(2);
		
		// call the displayURL method to count and display the number of links present in the web page
		displayUrl(driver); 
		TimeUnit.SECONDS.sleep(2);
		
		driver.findElement(By.linkText("terms and conditions")).click();
		TimeUnit.SECONDS.sleep(2);
		
		//handle multiple window
	
		switchWindow(driver);	//switch to "Terms and Conditions" page and validate the title
		
		//close the Terms and Conditions window
		driver.close();
		
		
		
		//close the parent window
		driver.quit();	

	}

	public static WebDriver initiateDriver() {
		WebDriver driver=null;
		Scanner sc=new Scanner(System.in);
		String name=sc.next();
		if(name.equalsIgnoreCase("Chrome"))
			driver=new ChromeDriver();
		else if(name.equalsIgnoreCase("Edge"))
			driver=new EdgeDriver();
		else
			System.out.println("Invalid Input");
		
		//maximize the window
		driver.manage().window().maximize();
		
		//implicitly wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
		
		
	}

	public static void displayUrl(WebDriver driver) {
		
		//find the number of links and print the links
		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("Total number of links is: " + links.size());
		System.out.println("The links are:");
		for (WebElement link : links) {
			System.out.println(link.getAttribute("href"));
		}

	}

	public static void parentValidation(WebDriver driver) {
		
		//parent page validation
		String act_title = "";
		try {
			act_title = driver.findElement(By.cssSelector(".f22")).getText();
		} catch (NoSuchElementException e) {
		}
		String exp_title = "Create a Rediffmail account";
		if (act_title.equals(exp_title)) {
			System.out.println("Parent Page Title Validation Successful");
		} else {
			System.out.println("Parent Page Title Validation Failed");
		}
	}

	public static void switchWindow(WebDriver driver) {
		
		//switch windows to handle multiple windows
		Set<String> winIDS = driver.getWindowHandles();
		List<String> winIDSList = new ArrayList(winIDS);
		String pWindowID = winIDSList.get(0);
		String cWindowID = winIDSList.get(1);
		driver.switchTo().window(cWindowID);
		String act_head = "";
		try {
			act_head = driver.findElement(By.cssSelector(".floatL.bold")).getText();
		} catch (NoSuchElementException e) {
		}
		String exp_head = "Terms and Conditions";
		if (act_head.equals(exp_head)) {
			System.out.println("Child Page Title Validation Successful");
		} else {
			System.out.println("Child Page Title Validation Failed");
		}
		//switch to parent window
				driver.switchTo().window(pWindowID);

		
	}

}

