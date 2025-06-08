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
import org.openqa.selenium.interactions.Actions;
import java.io.File;


class SettingsBasePage extends BasePage {

    protected By hoverToolTipDivLocator = By.xpath("//div[@id='tiptip_holder']"); // This is the dynamic hover div that has the tooltip texts
    protected By accountDropdownButtonLocator = By.xpath("//li[@id='top-bar-user']");
    protected By logoutButtonLocator = By.xpath("//a[normalize-space()='Kilépés']");
    protected By settingsAccountButtonLocator = By.xpath("//div[@class='form-content']//a[normalize-space()='Fiók']");
    protected By settingsProfileButtonLocator = By.xpath("//div[@class='form-content']//a[normalize-space()='Profil']");
    protected By submitFormButtonLocator = By.xpath("//button[@type='submit']");
    protected By notificationDivLocator = By.xpath("//div[@id='growl']");

    public SettingsBasePage(WebDriver driver) {
        super(driver);
    }
    
    public LoggedOutPage logout() {
        WebElement accountDropdownButton = waitAndReturnElement(accountDropdownButtonLocator);
        accountDropdownButton.click();

        WebElement logoutButton = waitAndReturnElement(logoutButtonLocator);
        logoutButton.click();

        return new LoggedOutPage(this.driver);
    }

    public LoggedInPage goToLoggedInPage() {
        // No buttons that navigate back to the logged in page
        this.driver.get(super.baseUrl);
        return new LoggedInPage(this.driver);
    }

    protected void submitForm() {
        WebElement submitFormButton = waitAndReturnElement(submitFormButtonLocator);
        submitFormButton.click();
    }

    public SettingsProfileSubPage goToProfileSubPage() {
        WebElement settingsProfileButton = waitAndReturnElement(settingsProfileButtonLocator);
        settingsProfileButton.click();
        return new SettingsProfileSubPage(this.driver);
    }

    public SettingsAccountSubPage goToAccountSubPage() {
        WebElement settingsAccountButton = waitAndReturnElement(settingsAccountButtonLocator);
        settingsAccountButton.click();
        return new SettingsAccountSubPage(this.driver);
    }

    // Tooltip is universal to the subpages
    public boolean isToolTipDisplayed() {
        return isWebElementDisplayed(hoverToolTipDivLocator);
    }

    // Tooltip is universal to the subpages
    public String getToolTipText() {
        if(!isToolTipDisplayed())
        {
            return ""; // Maybe return null? And check if its null?
        }

        WebElement toolTipDiv = waitAndReturnElement(hoverToolTipDivLocator);
        return toolTipDiv.getText();
    }
}
