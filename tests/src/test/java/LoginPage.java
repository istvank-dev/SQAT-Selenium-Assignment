import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;


class LoginPage extends BasePage {
    
    private By usernameLocator = By.xpath("//input[@name='login-subject']");
    private By passwordLocator = By.xpath("//input[@name='password']");
    private By loginSubmitButtonLocator = By.xpath("//div[@class='input-with-button']/button[@type='submit']");


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoggedInPage login(String username, String password) {
        WebElement usernameInputField = waitAndReturnElement(usernameLocator);
        usernameInputField.clear();
        usernameInputField.sendKeys(username);

        WebElement passwordInputField = waitAndReturnElement(passwordLocator);
        passwordInputField.clear();
        passwordInputField.sendKeys(password);

        WebElement loginSubmitButton = waitAndReturnElement(loginSubmitButtonLocator);
        loginSubmitButton.click();

        return new LoggedInPage(this.driver);
    }
}
