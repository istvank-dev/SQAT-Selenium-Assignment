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


class LoggedInPage extends BasePage {
    
    private By accountDropdownButtonLocator = By.xpath("//li[@id='top-bar-user']");
    private By logoutButtonLocator = By.xpath("//a[normalize-space()='Kilépés']");
    private By profilePageButtonLocator = By.xpath("//a[normalize-space()='Profilom']");
    private By settingsPageButtonLocator = By.xpath("//a[normalize-space()='Beállítások']");

    public LoggedInPage(WebDriver driver) {
        super(driver);
    }

    public LoggedOutPage logout() {
        WebElement accountDropdownButton = waitAndReturnElement(accountDropdownButtonLocator);
        accountDropdownButton.click();

        WebElement logoutButton = waitAndReturnElement(logoutButtonLocator);
        logoutButton.click();

        return new LoggedOutPage(this.driver);
    }

    public ProfilePage goToProfilePage() {
        WebElement accountDropdownButton = waitAndReturnElement(accountDropdownButtonLocator);
        accountDropdownButton.click();

        WebElement profilePageButton = waitAndReturnElement(profilePageButtonLocator);
        profilePageButton.click();

        return new ProfilePage(this.driver);
    }

    public SettingsAccountSubPage goToSettingsPage() {
        WebElement accountDropdownButton = waitAndReturnElement(accountDropdownButtonLocator);
        accountDropdownButton.click();

        WebElement settingsPageButton = waitAndReturnElement(settingsPageButtonLocator);
        settingsPageButton.click();

        return new SettingsAccountSubPage(this.driver);
    }

}
