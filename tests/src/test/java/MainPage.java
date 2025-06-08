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


class MainPage extends BasePage {

    private By aboutMenuButtonLocator = By.xpath("//li[@data-nav='about']");

    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get(baseUrl);
    }

    public LoginPage goToLoginPage() {
        WebElement loginPageButton = waitAndReturnElement(loginButtonLocator);
        loginPageButton.click();
        return new LoginPage(this.driver);
    }

    public BasePage openAboutStaticPage(By locator) {
        openAboutMenu();
        WebElement aboutStaticPageButton = waitAndReturnElement(locator);
        aboutStaticPageButton.click();
        return new BasePage(this.driver);
    }

    public void openAboutMenu() {
        if(isAboutMenuDisplayed())
        {
            return;
        }
        clickAboutMenuButton();
    }

    public void closeAboutMenu() {
        if(!isAboutMenuDisplayed())
        {
            return;
        }
        clickAboutMenuButton();
    }

    public boolean isAboutMenuDisplayed() {
        WebElement aboutMenuButton = waitAndReturnElement(aboutMenuButtonLocator);
        String classAttribute = aboutMenuButton.getAttribute("class");

        // Guard clause, to check if it even has class or not
        if(classAttribute == null)
        {
            return false;
        }

        return classAttribute.contains("opened");
    }

    private void clickAboutMenuButton() {
        WebElement aboutMenuButton = waitAndReturnElement(aboutMenuButtonLocator);
        aboutMenuButton.click();
    }
}
