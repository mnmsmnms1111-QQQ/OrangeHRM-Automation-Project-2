



import DataProviders.DataProviders;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Map;

public class EmployeeCreation {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test(dataProvider = "EmployeeData", dataProviderClass = DataProviders.class)
    public void testEmployeeCreation(Map<String, String> employee) {
        String firstName = employee.get("firstName");
        String middleName = employee.get("middleName");
        String lastName = employee.get("lastName");
        String userNameValue = employee.get("username");
        String passwordValue = employee.get("password");
        String photoPath = employee.get("photoPath");

        // ---- LOGIN AS ADMIN ----
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("Admin");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys("admin123");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();

        // Verify login
        By userProfilePic = By.cssSelector(".oxd-userdropdown-tab");
        wait.until(ExpectedConditions.visibilityOfElementLocated(userProfilePic));
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "❌ Login failed - Dashboard not loaded.");
        System.out.println("✅ Logged in as Admin");

        // ---- CREATE EMPLOYEE ----
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='PIM']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Add Employee')]"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstName"))).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("middleName"))).sendKeys(middleName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName"))).sendKeys(lastName);

        // Set Employee ID dynamically
        WebElement empId = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Employee Id']/../following-sibling::div/input")));
        empId.clear();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String empIdValue = timestamp.substring(timestamp.length() - 6);
        empId.sendKeys(empIdValue);

        // Upload employee photo
        WebElement uploadInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
        uploadInput.sendKeys(photoPath);

        // Enable "Create Login Details"
        By toggle = By.xpath("//span[contains(@class,'oxd-switch-input')]");
        WebElement toggleBtn = wait.until(ExpectedConditions.elementToBeClickable(toggle));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", toggleBtn);
        toggleBtn.click();

        // Fill login details
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Username']/../following-sibling::div/input"))).sendKeys(userNameValue);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Password']/../following-sibling::div/input"))).sendKeys(passwordValue);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Confirm Password']/../following-sibling::div/input"))).sendKeys(passwordValue);

        // Save employee
        By saveBtn = By.xpath("//button[contains(.,'Save')]");
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", saveButton);
        saveButton.click();

        // Verify employee creation
        Assert.assertTrue(wait.until(ExpectedConditions.urlContains("viewPersonalDetails")),
                "❌ Employee creation failed.");
        System.out.println("✅ Employee created → Username: " + userNameValue + " | EmpID: " + empIdValue);

        // ---- LOGOUT ADMIN ----
        wait.until(ExpectedConditions.elementToBeClickable(userProfilePic)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Logout']"))).click();
        System.out.println("🔹 Logged out as Admin");

        // ---- LOGIN AS NEW EMPLOYEE ----
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys(userNameValue);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys(passwordValue);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();

        // Verify login as employee
        wait.until(ExpectedConditions.visibilityOfElementLocated(userProfilePic));
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"),
                "❌ Employee login failed.");
        System.out.println("✅ Successfully logged in as new Employee: " + userNameValue);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
