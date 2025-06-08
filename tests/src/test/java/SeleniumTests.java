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
import java.io.File;


public class SeleniumTests {
    public WebDriver driver;
    private final String avatarImagePath = "src/test/resources/avatar.png";
    
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
        SettingsAccountSubPage settingsPage = loggedInPage.goToSettingsPage();

        // Log out
        LoggedOutPage loggedOutPage = settingsPage.logout();

        // Jump to main page from logout page
        MainPage finalMainPage = loggedOutPage.goToMainPage();
        Assert.assertTrue(finalMainPage.isLoginButtionDisplayed());
    }

    @Test
    public void testHoverInSettingsPage() {
        // Log in
        MainPage mainPage = new MainPage(this.driver);
        LoginPage loginPage = mainPage.goToLoginPage();
        LoggedInPage loggedInPage = loginPage.login("teszteleksqat", "tesztelek1"); // For now its hardcoded credentials

        // Hover test
        SettingsAccountSubPage settingsAccountSubPage = loggedInPage.goToSettingsPage();
        Assert.assertFalse(settingsAccountSubPage.isToolTipDisplayed());
        settingsAccountSubPage.hoverOverUsernameInput();
        Assert.assertTrue(settingsAccountSubPage.isToolTipDisplayed());
    }

    @Test
    public void testSendUserForm() {
        // Log in
        MainPage mainPage = new MainPage(this.driver);
        LoginPage loginPage = mainPage.goToLoginPage();
        LoggedInPage loggedInPage = loginPage.login("teszteleksqat", "tesztelek1"); // For now its hardcoded credentials

        // GoTo settings page
        SettingsAccountSubPage settingsAccountSubPage = loggedInPage.goToSettingsPage();
        Assert.assertEquals(settingsAccountSubPage.getPageUrl(), "https://kepkuldes.com/settings");
        SettingsProfileSubPage settingsProfileSubPage = settingsAccountSubPage.goToProfileSubPage();

        // Add bio text and submit form
        String bioText = "Hello World!";
        settingsProfileSubPage.addAboutText(bioText);
        String bioAfter = settingsProfileSubPage.getAboutText();
        Assert.assertEquals(bioText, bioAfter);

        // Clean bio
        String emptyString = "";
        settingsProfileSubPage.addAboutText(emptyString);
        String bioFinal = settingsProfileSubPage.getAboutText();
        Assert.assertEquals(emptyString, bioFinal);
        Assert.assertNotEquals(bioAfter, bioFinal);
    }
/*
    @Test
    public void testUploadAvatarThenDelete() {
        // Log in
        MainPage mainPage = new MainPage(this.driver);
        LoginPage loginPage = mainPage.goToLoginPage();
        LoggedInPage loggedInPage = loginPage.login("teszteleksqat", "tesztelek1"); // For now its hardcoded credentials

        // GoTo settings page
        SettingsAccountSubPage settingsAccountSubPage = loggedInPage.goToSettingsPage();
        SettingsProfileSubPage settingsProfileSubPage = settingsAccountSubPage.goToProfileSubPage();

        // If theres no avatar, then there should be no delete button either
        Assert.assertFalse(settingsProfileSubPage.isDeleteAvatarButtonDisplayed());

        settingsProfileSubPage.uploadAvatarImage(avatarImagePath);

        // Upload succesfull if we can delete the avatar
        Assert.assertTrue(settingsProfileSubPage.isDeleteAvatarButtonDisplayed());

        settingsProfileSubPage.deleteAvatarImage();

        // Successfull delete, test will be repeatable
        Assert.assertFalse(settingsProfileSubPage.isDeleteAvatarButtonDisplayed());
    }*/
    
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
