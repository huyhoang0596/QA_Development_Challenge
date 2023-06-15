package javatesting;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class QA_Development_Challenge {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	Random random = new Random();
	int int_random = Math.abs(random.nextInt());
	String productName = "Test" + String.valueOf(int_random);
	String product, quantity, scheduledDate, responsible;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://codechallenge.odoo.com/web/login");
	}

	@Test
	public void TC_01_Login() {

		// Step 1
		driver.findElement(By.cssSelector("input#login")).sendKeys("user@codechallenge.app");
		driver.findElement(By.cssSelector("input#password")).sendKeys("123456");
		driver.findElement(By.cssSelector("button[class='btn btn-primary btn-block']")).click();
	}

	@Test
	public void TC_02_CreateProduct() {

		// Step 2
		driver.findElement(By.cssSelector("a#result_app_1")).click();

		// Step 3
		driver.findElement(By.cssSelector("button[title='Products']")).click();
		driver.findElement(By.cssSelector("a[data-section='124']")).click();
		driver.findElement(By.cssSelector("button[title='Create record']")).click();

		// Step 4
		driver.findElement(By.cssSelector("input#o_field_input_12")).sendKeys(productName);
		driver.findElement(By.cssSelector("button[title='Save record']")).click();
		sleepInSecond(2);

		// Step 5
		driver.findElement(By.cssSelector("button[name='action_update_quantity_on_hand']")).click();
		sleepInSecond(2);
		driver.findElement(By.cssSelector("button[class='btn btn-primary o_list_button_add']")).click();
		driver.findElement(By.cssSelector("input[name='inventory_quantity']")).clear();
		driver.findElement(By.cssSelector("input[name='inventory_quantity']")).sendKeys("10");
		driver.findElement(By.cssSelector("button[title='Save record']")).click();
	}

	@Test
	public void TC_03_Manufacturing() {

		// Step 6
		driver.findElement(By.cssSelector("a[class='fa o_menu_toggle fa-th']")).click();

		// Step 7
		driver.findElement(By.cssSelector("a#result_app_2")).click();
		driver.findElement(By.cssSelector("button[class='btn btn-primary o_list_button_add']")).click();
		driver.findElement(By.cssSelector("input[class='o_input ui-autocomplete-input']"))
				.sendKeys(productName);
		driver.findElement(By.xpath("//html")).click();

		// Step 8
		driver.findElement(By.cssSelector("button[name='action_confirm']")).click();
		sleepInSecond(1);
		driver.findElement(By.xpath("//footer[@class= 'modal-footer']/button[1]")).click();
		driver.findElement(By.cssSelector("button[name='action_confirm']")).click();
		sleepInSecond(3);
		driver.findElement(By.xpath("//div[@class= 'o_statusbar_buttons']/button[4]")).click();
		driver.findElement(By.xpath("//footer[@class= 'modal-footer']/button[1]")).click();
		driver.findElement(By.cssSelector("button[name='process']")).click();
		driver.findElement(By.cssSelector("button[title='Save record']")).click();
		
		//Step 9
		//Verify Product
		product = driver.findElement(By.xpath("//div[@class='o_group']/table[1]/tbody/tr[5]/td[2]/a/span")).getText();
		Assert.assertEquals(product, productName);
	}

	private void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
