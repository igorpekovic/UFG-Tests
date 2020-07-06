package com.applitools.quickstarts;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


/**
 * Unit test for simple App.
 */
public class AppTest {

	public static void main(String[] args) {
		// Create a new chrome web driver
		WebDriver webDriver = new ChromeDriver();

		// Create a runner with concurrency of 1
		VisualGridRunner runner = new VisualGridRunner(10);

		// Create Eyes object with the runner, meaning it'll be a Visual Grid eyes.
		Eyes eyes = new Eyes(runner);

		setUp(eyes);

		try {

			 task1(webDriver, eyes);
			 task2(webDriver, eyes);
			 task3(webDriver, eyes);

		} finally {
			tearDown(webDriver, runner);
		}

	}

	public static void setUp(Eyes eyes) {

		// Initialize eyes Configuration
		Configuration config = new Configuration();

		// You can get your api key from the Applitools dashboard
		config.setApiKey("rZAhKCC4pPglsd2e0ei4QDvjI6cVT2EEwDsv0rIiUv0110");

		// create a new batch info instance and set it to the configuration
		config.setBatch(new BatchInfo("UFG Hackathon"));

		// Add browsers with different viewports
		config.addBrowser(1200, 700, BrowserType.CHROME);
		config.addBrowser(1200, 700, BrowserType.FIREFOX);
		config.addBrowser(1200, 700, BrowserType.EDGE_CHROMIUM);
		config.addBrowser(768, 700, BrowserType.CHROME);
		config.addBrowser(768, 700, BrowserType.FIREFOX);
		config.addBrowser(768, 700, BrowserType.EDGE_CHROMIUM);

		// Add mobile emulation devices in Portrait mode
		config.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);

		// Set the configuration object to eyes
		eyes.setConfiguration(config);

	}

	public static void task1(WebDriver webDriver, Eyes eyes) {

		try {

			// Navigate to the url we want to test
			webDriver.get("https://demo.applitools.com/gridHackathonV1.html");

			// Call Open on eyes to initialize a test session
			eyes.open(webDriver, "Cross-Device Elements Test", "Task 1", new RectangleSize(800, 600));

			eyes.checkWindow("Cross-Device Elements Test");

			// Call Close on eyes to let the server know it should display the results
			eyes.closeAsync();


		} finally  {
			eyes.abortAsync();
		}

	}

	public static void task2(WebDriver webDriver, Eyes eyes) {

		try {

			// Call Open on eyes to initialize a test session
			eyes.open(webDriver, "Filter results", "Task 2", new RectangleSize(800, 600));
			new WebDriverWait(webDriver, 20).until(ExpectedConditions.elementToBeClickable(By.id("ti-filter"))).click();
			new WebDriverWait(webDriver, 20).until(ExpectedConditions.elementToBeClickable(By.id("SPAN__checkmark__107"))).click();
			new WebDriverWait(webDriver, 20).until(ExpectedConditions.elementToBeClickable(By.id("filterBtn"))).click();

			eyes.check("Filter results", Target.region(By.id("product_grid")));

			// Call Close on eyes to let the server know it should display the results
			eyes.closeAsync();

		} finally  {
			eyes.abortAsync();
		}

	}

	public static void task3(WebDriver webDriver, Eyes eyes) {

		try {

			// Call Open on eyes to initialize a test session
			eyes.open(webDriver, "Product Details test", "Task 3", new RectangleSize(800, 600));

			new WebDriverWait(webDriver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id=\"product_grid\"]/div/div/figure"))).click();
			eyes.checkWindow("Product Details test");

			// Call Close on eyes to let the server know it should display the results
			eyes.closeAsync();

		} finally  {
			eyes.abortAsync();
		}

	}

	private static void tearDown(WebDriver webDriver, VisualGridRunner runner) {
		// Close the browser
		webDriver.quit();

		// we pass false to this method to suppress the exception that is thrown if we
		// find visual differences
		TestResultsSummary allTestResults = runner.getAllTestResults(false);
		System.out.println(allTestResults);
	}

}