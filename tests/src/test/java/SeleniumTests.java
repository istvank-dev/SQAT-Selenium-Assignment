import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.*;  
import java.net.URL;
import java.net.MalformedURLException;


public class SeleniumTests {
    public WebDriver driver;
    
    @Before
    public void setup()  throws MalformedURLException  {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
    }
    
    @Test
    public void testTitle() {
        MainPage mainPage = new MainPage(this.driver);
        Assert.assertTrue(mainPage.getPageTitle().contains("Képküldés - Képfeltöltés - Ingyenes és gyors képfeltöltés közvetlen linkkel!"));
        Assert.assertTrue(mainPage.isLoginButtionDisplayed());
    }

    @Test
    public void testLoginThenLogoutGoToMainPage() {
        // Log in
        MainPage mainPage = new MainPage(this.driver);
        Assert.assertTrue(mainPage.isLoginButtionDisplayed());
        LoginPage loginPage = mainPage.goToLoginPage();
        Assert.assertEquals(loginPage.getPageUrl(), "https://kepkuldes.com/login");
        LoggedInPage loggedInPage = loginPage.login("teszteleksqat", "tesztelek1"); // For now its hardcoded credentials

        // Asserting successful login
        Assert.assertFalse(loggedInPage.isLoginButtionDisplayed());

        // Log out
        LoggedOutPage loggedOutPage = loggedInPage.logout();

        // Jump to main page from logout page
        MainPage finalMainPage = loggedOutPage.goToMainPage();
        Assert.assertTrue(finalMainPage.isLoginButtionDisplayed());
    }

    @Test
    public void testHistory() {
        MainPage mainPage = new MainPage(this.driver);
        String startUrl = mainPage.getPageUrl();

        LoginPage loginPage = mainPage.goToLoginPage();
        String newUrl = loginPage.getPageUrl();

        Assert.assertNotEquals(startUrl, newUrl);

        // Magic
        driver.navigate().back();
        String endUrl = driver.getCurrentUrl();

        Assert.assertEquals(startUrl, endUrl);
    }

    @Test
    public void testSingleStaticPage() {
        By locator = By.xpath("//a[contains(@href, 'aszf')]");
        MainPage mainPage = new MainPage(this.driver);
        String startUrl = mainPage.getPageUrl();

        BasePage staticPage = mainPage.openAboutStaticPage(locator);
        String staticUrl = staticPage.getPageUrl();

        Assert.assertNotEquals(startUrl, staticUrl);
        Assert.assertTrue(staticPage.getBodyText().contains("Általános Szerzõdési Feltételek"));
    }

    @Test
    public void testLoginGoToSettingsLogout() {
        // Log in
        MainPage mainPage = new MainPage(this.driver);
        Assert.assertTrue(mainPage.isLoginButtionDisplayed());
        LoginPage loginPage = mainPage.goToLoginPage();
        Assert.assertEquals(loginPage.getPageUrl(), "https://kepkuldes.com/login");
        LoggedInPage loggedInPage = loginPage.login("teszteleksqat", "tesztelek1"); // For now its hardcoded credentials

        // Asserting successful login
        Assert.assertFalse(loggedInPage.isLoginButtionDisplayed());

        // GoTo settings page
        SettingsPage settingsPage = loggedInPage.goToSettingsPage();

        // Log out
        LoggedOutPage loggedOutPage = settingsPage.logout();

        // Jump to main page from logout page
        MainPage finalMainPage = loggedOutPage.goToMainPage();
        Assert.assertTrue(finalMainPage.isLoginButtionDisplayed());
    }
    
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
