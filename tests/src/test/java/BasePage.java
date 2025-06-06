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


class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl = "https://kepkuldes.com/";
    protected By loginButtonLocator = By.xpath("//li[@id='top-bar-signin']");
    
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
    }
    
    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    public String getPageTitle() {
        return this.driver.getTitle();
    }

    public String getPageUrl() {
        return this.driver.getCurrentUrl();
    }

    public boolean isLoginButtionDisplayed() {
        try {
            WebElement loginButton = waitAndReturnElement(loginButtonLocator);
            return loginButton.isDisplayed();
        } catch (Exception e)
        {
            return false;
        }
    }
   
}
