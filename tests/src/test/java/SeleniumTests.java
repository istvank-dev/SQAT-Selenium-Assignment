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
    }

    @Test
    public void testLoginThenLogout() {
        // Log in
        MainPage mainPage = new MainPage(this.driver);
        Assert.assertTrue(mainPage.isLoginButtionDisplayed());
        LoginPage loginPage = mainPage.goToLoginPage();
        Assert.assertEquals(loginPage.getPageUrl(), "https://kepkuldes.com/login");
        LoggedInPage loggedInPage = loginPage.login("teszteleksqat", "tesztelek1"); // For now its hardcoded credentials

        // Asserting successful login
        Assert.assertFalse(loggedInPage.isLoginButtionDisplayed());

        // Log out
        mainPage = loggedInPage.logout();
    }
    
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
