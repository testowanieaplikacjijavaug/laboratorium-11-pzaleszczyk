

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ElementPresentDuckTest {

	private static WebDriver driver;

	@BeforeAll
	public static void setUpDriver(){
		WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@BeforeEach
	public void setUp() throws Exception {
		driver.get("https://duckduckgo.com/");
	}

	@AfterAll
	public static void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void findFirst() {
		driver.findElement(By.id("search_form_input_homepage")).sendKeys("mfi.ug.edu.pl");
		driver.findElement(By.id("search_button_homepage")).click();
		driver.findElement(By.id("r1-0")).click();
		assertEquals("https://mfi.ug.edu.pl/", driver.getCurrentUrl());
	}

	@Test
	public void findThird() {
		driver.findElement(By.id("search_form_input_homepage")).sendKeys("mfi.ug.edu.pl");
		driver.findElement(By.id("search_button_homepage")).click();
		driver.findElement(By.id("r1-2")).click();
		assertEquals("https://en.mfi.ug.edu.pl/students/erasmus_0", driver.getCurrentUrl());
	}


	@Test
	public void noClick() {
		driver.findElement(By.id("search_form_input_homepage")).sendKeys("mfi.ug.edu.pl");
		driver.findElement(By.id("search_button_homepage")).sendKeys(Keys.RETURN);
		String name = driver.findElement(By.id("r1-0")).getText().substring(0,41);
		assertEquals("Wydział Matematyki, Fizyki i Informatyki ", name);
	}

	@Test
	public void noResult() {
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.id("search_form_input_homepage")).sendKeys("//!?@!?#!?");
			driver.findElement(By.id("search_button_homepage")).click();
			driver.findElement(By.id("r1-0")).click();
			assertEquals("https://mfi.ug.edu.pl/", driver.getCurrentUrl());
		});
	}
	

	@Test
	public void byClassName() {
		driver.findElement(By.id("search_form_input_homepage")).sendKeys("afsddfs");
		driver.findElement(By.id("search_button_homepage")).click();
		String name = driver.findElement(By.className("result__url__full")).getText();
		
		assertEquals("/questions/504006/algorithm-in-ieee-journal", name);
	}
	
	@Test
	public void elementPresentTest() {
		boolean resulttrue = isElementPresent(By.id("search_form_input_homepage"));
		boolean resultfalse = isElementPresent(By.id("search_form_input_homepage_false"));
		assertAll(
			() -> assertTrue(resulttrue),
			() ->assertTrue(!resultfalse)
		);
	}
	
	private boolean isElementPresent(By by) {
        try{
            driver.findElement(by);
            return true;
        }catch(Exception e) {
        	return false;
        }
    }
	
}
