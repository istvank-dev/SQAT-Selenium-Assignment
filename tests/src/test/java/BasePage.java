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
import java.time.Duration;


class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl = "https://kepkuldes.com/";
    protected By loginButtonLocator = By.xpath("//li[@id='top-bar-signin']");
    protected By headerLocator = By.tagName("header");
    protected By bodyLocator = By.tagName("body");
    
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
    }
    
    protected WebElement waitAndReturnElement(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.presenceOfElementLocated(locator));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public String getPageTitle() {
        return this.driver.getTitle();
    }

    public String getPageUrl() {
        return this.driver.getCurrentUrl();
    }

    public String getBodyText() {
        return waitAndReturnElement(bodyLocator).getText();
    }

    public boolean isWebElementDisplayed(By locator) {
        try {
            WebElement element = waitAndReturnElement(locator);
            return element.isDisplayed();
        } catch (Exception e)
        {
            return false;
        }
    }
   
    public boolean isLoginButtionDisplayed() {
        return isWebElementDisplayed(loginButtonLocator);
    }
}
