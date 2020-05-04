
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

public class LoginWaitTest {

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
	public void noPasswordNoLogin() throws InterruptedException {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div/fieldset[5]/button")).click();

		WebElement element = new WebDriverWait(driver,3).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"default-register\"]/div")));
		assertTrue(element.isDisplayed());
	}
	
	@Test
	public void testToBeSelected() throws InterruptedException {
		driver.navigate().to("http://how2html.pl/input-radio-html/");
		driver.findElement(By.id("fries")).click();
		Boolean element = new WebDriverWait(driver,10).
		until(ExpectedConditions.elementToBeSelected(By.id("fries")));
		assertTrue(element);
	}

	@Test
	public void onlyPassword() throws InterruptedException {
		driver.findElement(By.name("password")).sendKeys("testpassword");
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div/fieldset[5]/button")).click();

		WebElement element = new WebDriverWait(driver,3).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"default-register\"]/div")));
		assertTrue(element.isDisplayed());
	}

	@Test
	public void onlyLogin() throws InterruptedException {
		driver.findElement(By.name("username")).sendKeys("testlogin");
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div/fieldset[5]/button")).click();
		WebElement element = new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/div/div/div[2]/div/form/div/fieldset[2]/div")));
		assertTrue(element.isDisplayed());
	}

	@Test
    public void testTitle() {
    	driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div/div[4]/a")).click();
        Boolean element = new WebDriverWait(driver,3).until(ExpectedConditions.titleContains("reddit.com: Join the worldwide conversation"));
        assertTrue(element);
    }
    
    @Test
    public void testTextPresent() {
    	driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div/div[4]/a")).click();
        WebElement text = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div[1]/p[2]"));
        Boolean element = new WebDriverWait(driver,3).until(
                ExpectedConditions.textToBePresentInElement(text, "Policy"));
        assertTrue(element);
    }
    
    @Test
    public void testTextPresentValue() {
    	driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div/div[4]/a")).click();
    	driver.findElement(By.xpath("//*[@id=\'regEmail\']")).sendKeys("test");
        WebElement text = driver.findElement(By.xpath("//*[@id=\'regEmail\']"));
        Boolean element = new WebDriverWait(driver,3).until(
                ExpectedConditions.textToBePresentInElementValue(text, "test"));
        assertTrue(element);
    }



}