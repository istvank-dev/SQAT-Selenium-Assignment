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


class LoggedOutPage extends BasePage {
    
    private By startPageButtonLocator = By.xpath("//a[normalize-space()='Ugrás a főoldalra']");

    public LoggedOutPage(WebDriver driver) {
        super(driver);
    }

    public MainPage goToMainPage() {
        WebElement startPageButton = waitAndReturnElement(startPageButtonLocator);
        startPageButton.click();

        return new MainPage(this.driver);
    }

}
