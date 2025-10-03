import DataProviders.DataProviders;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmployeeCreationNegtiveTest {

    private WebDriver driver;
    private WebDriverWait wait;

   @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[contains(.,'Login')]")).click();

        // Wait for dashboard to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    private void takeScreenshot(String name) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File dest = new File(System.getProperty("user.dir") + "/screenshots/" + name + "_" + timestamp + ".png");
        dest.getParentFile().mkdirs();
        try { Files.copy(src.toPath(), dest.toPath()); } catch (IOException e) { e.printStackTrace(); }
    }

    private void goToAddEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='PIM']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.orangehrm-container")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,' Add ')]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Add Employee']")));
    }

    // ===== Existing Negative Tests =====

    @Test(priority = 1 , dataProvider = "InvalidLogin",dataProviderClass = DataProviders.class)
    public void specialCharsTest(String fnamw, String mname ,String lname, String emid) {
        goToAddEmployee();
        driver.findElement(By.name("firstName")).sendKeys(fnamw);
        driver.findElement(By.name("middleName")).sendKeys(mname);
        driver.findElement(By.name("lastName")).sendKeys(lname);

        WebElement empId = driver.findElement(By.xpath("//label[text()='Employee Id']/following::input[1]"));
        empId.clear();
        empId.sendKeys(emid); // Assuming ID "1" already exists

        driver.findElement(By.xpath("//button[text()=' Save ']")).click();
        takeScreenshot("specialCharsError");
    }

//    @Test(priority = 2)
//    public void duplicateEmployeeIDTest() {
//        goToAddEmployee();
//        driver.findElement(By.name("firstName")).sendKeys("Test");
//        driver.findElement(By.name("middleName")).sendKeys("Duplicate");
//        driver.findElement(By.name("lastName")).sendKeys("Duplicate");
//
//        WebElement empId = driver.findElement(By.xpath("//label[text()='Employee Id']/following::input[1]"));
//        empId.clear();
//        empId.sendKeys("1"); // Assuming ID "1" already exists
//
//        driver.findElement(By.xpath("//button[text()=' Save ']")).click();
//        takeScreenshot("duplicateIDError");
//    }

//    @Test(priority = 3)
//    public void blankLoginDetailsTest() {
//        goToAddEmployee();
//        driver.findElement(By.name("firstName")).sendKeys("LoginTest");
//        driver.findElement(By.name("middleName")).sendKeys("Blank");
//        driver.findElement(By.name("lastName")).sendKeys("User");
//
//        driver.findElement(By.xpath("//p[text()='Create Login Details']/following::label/span")).click();
//        driver.findElement(By.xpath("//button[text()=' Save ']")).click();
//        takeScreenshot("blankLoginError");
//    }

    // ===== New Negative Tests =====

//    @Test(priority = 4)
//    public void longNamesTest() {
//        goToAddEmployee();
//        String longName = "A".repeat(256);
//        driver.findElement(By.name("firstName")).sendKeys(longName);
//        driver.findElement(By.name("middleName")).sendKeys(longName);
//        driver.findElement(By.name("lastName")).sendKeys(longName);
//        driver.findElement(By.xpath("//button[text()=' Save ']")).click();
//        takeScreenshot("longNamesError");
//    }

//    @Test(priority = 5)
//    public void lettersInEmployeeIDTest() {
//        goToAddEmployee();
//        driver.findElement(By.name("firstName")).sendKeys("IDTest");
//        driver.findElement(By.name("middleName")).sendKeys("Letter");
//        driver.findElement(By.name("lastName")).sendKeys("Test");
//        WebElement empId = driver.findElement(By.xpath("//label[text()='Employee Id']/following::input[1]"));
//        empId.clear();
//        empId.sendKeys("ABC123"); // Letters + numbers
//        driver.findElement(By.xpath("//button[text()=' Save ']")).click();
//        takeScreenshot("lettersInEmpIDError");
//    }

//    @Test(priority = 6)
//    public void spacesInEmployeeIDTest() {
//        goToAddEmployee();
//        driver.findElement(By.name("firstName")).sendKeys("SpaceID");
//        driver.findElement(By.name("middleName")).sendKeys("Test");
//        driver.findElement(By.name("lastName")).sendKeys("User");
//        WebElement empId = driver.findElement(By.xpath("//label[text()='Employee Id']/following::input[1]"));
//        empId.clear();
//        empId.sendKeys("123 456"); // Space in ID
//        driver.findElement(By.xpath("//button[text()=' Save ']")).click();
//        takeScreenshot("spacesInEmpIDError");
//    }

//    @Test(priority = 7)
//    public void numericNamesTest() {
//        goToAddEmployee();
//        driver.findElement(By.name("firstName")).sendKeys("123");
//        driver.findElement(By.name("middleName")).sendKeys("456");
//        driver.findElement(By.name("lastName")).sendKeys("789");
//        driver.findElement(By.xpath("//button[text()=' Save ']")).click();
//        takeScreenshot("numericNamesError");
//    }

//    @Test(priority = 8)
//    public void veryShortNamesTest() {
//        goToAddEmployee();
//        driver.findElement(By.name("firstName")).sendKeys("A");
//        driver.findElement(By.name("middleName")).sendKeys("B");
//        driver.findElement(By.name("lastName")).sendKeys("C");
//        driver.findElement(By.xpath("//button[text()=' Save ']")).click();
//        takeScreenshot("shortNamesError");
//    }

//    @Test(priority = 9)
//    public void specialCharsInEmployeeIDTest() {
//        goToAddEmployee();
//        driver.findElement(By.name("firstName")).sendKeys("SpecialID");
//        driver.findElement(By.name("middleName")).sendKeys("Test");
//        driver.findElement(By.name("lastName")).sendKeys("User");
//        WebElement empId = driver.findElement(By.xpath("//label[text()='Employee Id']/following::input[1]"));
//        empId.clear();
//        empId.sendKeys("@#!$"); // Only special characters
//        driver.findElement(By.xpath("//button[text()=' Save ']")).click();
//        takeScreenshot("specialCharsInEmpIDError");
//    }

//    // ===== Replacement Negative Test =====
//    @Test(priority = 10)
//    public void middleNameSpecialCharsTest() {
//        goToAddEmployee();
//        driver.findElement(By.name("firstName")).sendKeys("MiddleTest");
//        driver.findElement(By.name("middleName")).sendKeys("@@@"); // Invalid middle name
//        driver.findElement(By.name("lastName")).sendKeys("User");
//        driver.findElement(By.xpath("//button[text()=' Save ']")).click();
//        takeScreenshot("middleNameSpecialCharsError");
//    }


}
