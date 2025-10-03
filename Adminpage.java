

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class Adminpage {
    private WebDriver driver;

    private By adminMenu = By.id("menu_admin_viewAdminModule");
    private By addUserBtn = By.id("btnAdd");
    private By userRoleDropdown = By.id("systemUser_userType");
    private By empNameField = By.id("systemUser_employeeName_empName");
    private By usernameField = By.id("systemUser_userName");
    private By passwordField = By.id("systemUser_password");
    private By confirmPasswordField = By.id("systemUser_confirmPassword");
    private By saveBtn = By.id("btnSave");

    public Adminpage(WebDriver driver) {
        this.driver = driver;
    }

    public void assignRole(String empName, String userName, String password, String role) {
        driver.findElement(adminMenu).click();
        driver.findElement(addUserBtn).click();

        Select roleDropdown = new Select(driver.findElement(userRoleDropdown));
        roleDropdown.selectByVisibleText(role);

        driver.findElement(empNameField).sendKeys(empName);
        driver.findElement(usernameField).sendKeys(userName);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(confirmPasswordField).sendKeys(password);

        driver.findElement(saveBtn).click();
    }
}

