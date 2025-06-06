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


class SettingsPage extends BasePage {

    private By hoverToolTipDivLocator = By.xpath("//div[@id='tiptip_holder']"); // This is the dynamic hover div that has the tooltip texts
    private By anonymousHoverElementLocator = By.xpath("//div[@id='tiptip_content' and contains(text(), 'anonymous')]");
    private By webpageInputHoverElementLocator = By.xpath("//div[@id='tiptip_content' and contains(text(), 'http://weboldala.hu')]");
    private By accountDropdownButtonLocator = By.xpath("//li[@id='top-bar-user']");
    private By logoutButtonLocator = By.xpath("//a[normalize-space()='Kilépés']");
    private By inputUsernameLocator = By.xpath("//input[contains(@name,'username')]");
    private By settingsAccountButtonLocator = By.xpath("//div[@class='form-content']//a[normalize-space()='Fiók']");
    private By settingsProfileButtonLocator = By.xpath("//div[@class='form-content']//a[normalize-space()='Profil']");
    private By bioTextareaLocator = By.xpath("//textarea[@name='bio']");
    private By submitFormButtonLocator = By.xpath("//button[@type='submit']");
    private By notificationDivLocator = By.xpath("//div[@id='growl']");

    public SettingsPage(WebDriver driver) {
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

    public boolean isToolTipDisplayed() {
        try {
            WebElement toolTipDiv = waitAndReturnElement(hoverToolTipDivLocator);
            return toolTipDiv.isDisplayed();
        } catch (Exception e)
        {
            return false;
        }
    }

    public void addAboutText(String text) {
        WebElement settingsProfileButton = waitAndReturnElement(settingsProfileButtonLocator);
        settingsProfileButton.click();

        WebElement bioTextarea = waitAndReturnElement(bioTextareaLocator);
        bioTextarea.clear();
        bioTextarea.sendKeys(text);

        submitForm();
    }

    public String getAboutText() {
        WebElement settingsProfileButton = waitAndReturnElement(settingsProfileButtonLocator);
        settingsProfileButton.click();

        WebElement bioTextarea = waitAndReturnElement(bioTextareaLocator);
        return bioTextarea.getText();
    }

    private void submitForm() {
        WebElement submitFormButton = waitAndReturnElement(submitFormButtonLocator);
        submitFormButton.click();
    }

    public void hoverOverUsernameInput() {
        WebElement input = waitAndReturnElement(inputUsernameLocator);
        Actions actions = new Actions(this.driver);
        actions.moveToElement(input).perform();
    }

    public String getToolTipText() {
        if(!isToolTipDisplayed())
        {
            return ""; // Maybe return null? And check if its null?
        }

        WebElement toolTipDiv = waitAndReturnElement(hoverToolTipDivLocator);
        return toolTipDiv.getText();
    }
}
