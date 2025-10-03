

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class EmployeeReports {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        // ✅ Setup to allow PDF export via print dialog
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("printing.print_preview_sticky_settings.appState",
                "{\"recentDestinations\":[{\"id\":\"Save as PDF\",\"origin\":\"local\",\"account\":\"\"}]," +
                        "\"selectedDestinationId\":\"Save as PDF\",\"version\":2}");
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--kiosk-printing");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Increased wait time for stability
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test
    public void testReports() {
        // 🔹 Login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Username']")))
                .sendKeys("Admin");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Password']")))
                .sendKeys("admin123");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();

        // 🔹 Navigate to PIM → Reports
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='PIM']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Reports']"))).click();

        // 🔹 Search for a report
        WebElement inputBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Type for hints...']")));
        inputBox.sendKeys("Employee");

        // Wait for ANY suggestion to appear
        By firstSuggestion = By.xpath("//div[@role='listbox']//span");
        WebElement suggestionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(firstSuggestion));

        // Click the first available suggestion
        String reportName = suggestionElement.getText();
        suggestionElement.click();
        System.out.println("✅ Report selected: " + reportName);

        // 🔹 Click Search button
        By searchButtonLocator = By.xpath("//button[text()=' Search ']");
        wait.until(ExpectedConditions.elementToBeClickable(searchButtonLocator)).click();

        // 🔹 Open the report (view icon)
        By reportIconLocator = By.xpath("//i[@class='oxd-icon bi-file-text-fill']");
        wait.until(ExpectedConditions.elementToBeClickable(reportIconLocator)).click();

        // 🔹 Full screen
        By fullScreenIconLocator = By.xpath("//i[@class='oxd-icon bi-arrows-fullscreen oxd-icon-button__icon --toggable-icon']");
        wait.until(ExpectedConditions.elementToBeClickable(fullScreenIconLocator)).click();

        // 🔹 Export/Print as PDF
        ((JavascriptExecutor) driver).executeScript("window.print();");
        System.out.println("✅ Report exported as PDF successfully.");
    }
}
