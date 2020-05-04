
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TimeoutTest {

	private static WebDriver driver;


	@BeforeAll
	public static void setUpDriver() {
		WebDriverManager.firefoxdriver().setup();
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		driver = new FirefoxDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@BeforeEach
	public void setUp() throws Exception {
		driver.get("https://www.reddit.com/login/?dest=https%3A%2F%2Fwww.reddit.com%2F");
	}

	@AfterEach
	public void ref() {
		driver.get("https://www.reddit.com/login/?dest=https%3A%2F%2Fwww.reddit.com%2F");
	}

	@AfterAll
	public static void tearDown() throws Exception {
		driver.quit();
	}

	@Test
    public void testpageLoad() {
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        
        driver.findElement(By.name("password")).sendKeys("testpassword");
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div/fieldset[5]/button")).click();
        
		String text = driver.findElement(By.xpath("//*[@id=\"default-register\"]/div")).getText();
		assertEquals("Username must be between 3 and 20 characters", text);
    }
    
    @Test
    public void testsetScript() {
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        
        driver.findElement(By.name("password")).sendKeys("testpassword");
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div/fieldset[5]/button")).click();
        
		String text = driver.findElement(By.xpath("//*[@id=\"default-register\"]/div")).getText();
		assertEquals("Username must be between 3 and 20 characters", text);
    }

}