
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import io.github.bonigarcia.seljup.SeleniumExtension;
@ExtendWith(SeleniumExtension.class)
public class NonStandardTest {

	private static WebDriver driver;


	public NonStandardTest(FirefoxDriver driver){
		this.driver = driver;
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
	public void test1() {
		driver.findElement(By.name("username")).sendKeys("testloing");
		driver.findElement(By.name("password")).sendKeys("testpasword");
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div/fieldset[5]/button")).click();
		try{		 
			String message = new WebDriverWait(driver, 5).until(new ExpectedCondition<String>(){
				@Override
				public String apply(WebDriver d){
					return d.findElement(By.className("AnimatedForm__errorMessage")).getText();
				}
			});
			assertEquals("Incorrect username or password", message);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testNoLogin() {
		try{		 
			driver.findElement(By.name("password")).sendKeys("testpassword");
			driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div/fieldset[5]/button")).click();
			WebElement message = new WebDriverWait(driver, 5).until(new ExpectedCondition<WebElement>(){
				@Override
				public WebElement apply(WebDriver d){
					return d.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div/fieldset[1]/div"));
				}
			});
			assertEquals("Username must be between 3 and 20 characters", message.getText());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testNoLoginDisplayed() {
		try{
			driver.findElement(By.name("password")).sendKeys("testpassword");
			driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div/fieldset[5]/button")).click();
			Boolean message = new WebDriverWait(driver, 5).until(new ExpectedCondition<Boolean>(){
				@Override
				public Boolean apply(WebDriver d){
					return d.findElement(By.xpath("/html/body/div/div/div[2]/div/form/div/fieldset[1]/div")).isDisplayed();
				}
			});
			assertTrue(message);
		} catch (Exception e) {
			fail();
		}
	}

}