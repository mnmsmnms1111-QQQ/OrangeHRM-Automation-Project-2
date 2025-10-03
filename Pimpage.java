

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Pimpage {
    private WebDriver driver;

    private By pimMenu = By.id("menu_pim_viewPimModule");
    private By addEmployeeBtn = By.id("menu_pim_addEmployee");
    private By firstNameField = By.id("firstName");
    private By lastNameField = By.id("lastName");
    private By empIdField = By.id("employeeId");
    private By createLoginChk = By.id("chkLogin");
    private By newUsername = By.id("user_name");
    private By newPassword = By.id("user_password");
    private By confirmPassword = By.id("re_password");
    private By saveBtn = By.id("btnSave");

    public Pimpage(WebDriver driver) {
        this.driver = driver;
    }

    public void addEmployee(String fName, String lName, String empId, String uName, String pwd) {
        driver.findElement(pimMenu).click();
        driver.findElement(addEmployeeBtn).click();
        driver.findElement(firstNameField).sendKeys(fName);
        driver.findElement(lastNameField).sendKeys(lName);
        driver.findElement(empIdField).clear();
        driver.findElement(empIdField).sendKeys(empId);
        driver.findElement(createLoginChk).click();
        driver.findElement(newUsername).sendKeys(uName);
        driver.findElement(newPassword).sendKeys(pwd);
        driver.findElement(confirmPassword).sendKeys(pwd);
        driver.findElement(saveBtn).click();
    }
}
