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


class SettingsAccountSubPage extends SettingsBasePage {

    private By inputUsernameLocator = By.xpath("//input[contains(@name,'username')]");
    private By regionDropdownLocator = By.xpath("//select[@id='timezone-region']");
    private By regionOptionEuropeLocator = By.xpath("//option[@value='Europe']");
    private By regionOptionAntarcticaLocator = By.xpath("//option[@value='Antarctica']");
    private By regionSelectedLocator = By.xpath("//select[@id='timezone-region']//option[@selected]");

    // THIS IS THE DEFAULT PAGE WHEN WE OPEN THE SETTINGS
    public SettingsAccountSubPage(WebDriver driver) {
        super(driver);
    }

    public String getRegionText() {
        WebElement regionSelected = waitAndReturnElement(regionSelectedLocator);
        return regionSelected.getText();
    }

    public void hoverOverUsernameInput() {
        WebElement input = waitAndReturnElement(inputUsernameLocator);
        Actions actions = new Actions(this.driver);
        actions.moveToElement(input).perform();
    }
}
