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


class SettingsPage extends BasePage {

    private By hoverToolTipDivLocator = By.xpath("//div[@id='tiptip_holder']"); // This is the dynamic hover div that has the tooltip texts
    private By anonymousHoverElementLocator = By.xpath("//div[@id='tiptip_content' and contains(text(), 'anonymous')]");
    private By webpageInputHoverElementLocator = By.xpath("//div[@id='tiptip_content' and contains(text(), 'http://weboldala.hu')]");
    private By accountDropdownButtonLocator = By.xpath("//li[@id='top-bar-user']");
    private By logoutButtonLocator = By.xpath("//a[normalize-space()='Kilépés']");


    public SettingsPage(WebDriver driver) {
        super(driver);
        this.driver.get(baseUrl);
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

    

    public String getToolTipText() {
        if(!isToolTipDisplayed())
        {
            return "";
        }

        WebElement toolTipDiv = waitAndReturnElement(hoverToolTipDivLocator);
        return toolTipDiv.getText();
    }
}
