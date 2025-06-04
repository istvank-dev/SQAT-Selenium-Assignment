import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;
import java.net.URL;
import java.net.MalformedURLException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class MyLoginTest {
    private String ValidUsername = "tomsmith"; // Valid
    private String ValidPassword = "SuperSecretPassword!"; // VAlid
    private String Url = "http://the-internet.herokuapp.com/login"; // baseurl

    private WebDriver Driver;
    private WebDriverWait DriverWait;

    private final By FieldUsername = By.id("username");
    private final By FieldPassword = By.id("password");

    private final By ButtonLogin = By.cssSelector("button[type='submit']");
    private final By ButtonLogout = By.cssSelector("a[href='/logout']");
    
    private final By Message = By.id("flash");

    private WebElement waitAndReturnElement(By locator) {
        this.DriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.Driver.findElement(locator);
    }
  


    @Before
    public void setUp() throws MalformedURLException {
        ChromeOptions Options = new ChromeOptions();
        Driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), Options);
        this.Driver.manage().window().maximize();
        this.DriverWait = new WebDriverWait(this.Driver, 20);
    }

    @Test
    public void TestLoginPageLoaded() {
        // Just a basic test to see if the webpage is even loaded or not
        this.Driver.get(Url);
            
        WebElement UsernameInput = waitAndReturnElement(FieldUsername);
        WebElement PasswordInput = waitAndReturnElement(FieldPassword);
        WebElement LoginButtonElement = waitAndReturnElement(ButtonLogin);

        assertTrue(UsernameInput.isDisplayed());
        assertTrue(PasswordInput.isDisplayed());
        assertTrue(LoginButtonElement.isDisplayed());
    }

     @Test
     public void TestLoginValid() {
        // test for valid credentials succesfull login and logout
        this.Driver.get(Url);

        WebElement UsernameInput = waitAndReturnElement(FieldUsername);
        WebElement PasswordInput = waitAndReturnElement(FieldPassword);
        WebElement LoginButtonElement = waitAndReturnElement(ButtonLogin);

        UsernameInput.sendKeys(ValidUsername);
        PasswordInput.sendKeys(ValidPassword);
        LoginButtonElement.click();

        // Login test
        WebElement MessageHTMLElement = waitAndReturnElement(Message);
        Assert.assertTrue(MessageHTMLElement.getText().contains("You logged into a secure area!"));

        // Logout test
        WebElement LogoutHTMLButtonElement = waitAndReturnElement(ButtonLogout);
        LogoutHTMLButtonElement.click();
        WebElement MessageHTMLElement2 = waitAndReturnElement(Message);
        Assert.assertTrue(MessageHTMLElement2.getText().contains("You logged out of the secure area!"));
     }

    @Test
    public void TestLoginInvalid() {
        // test for invalid username (password is invalid but doesnt even matter for now)
        this.Driver.get(Url);
        
        WebElement UsernameInput = waitAndReturnElement(FieldUsername);
        WebElement PasswordInput = waitAndReturnElement(FieldPassword);
        WebElement LoginButtonElement = waitAndReturnElement(ButtonLogin);

        UsernameInput.sendKeys("notauser");
        PasswordInput.sendKeys("definetlynotpassword");

        LoginButtonElement.click();
        WebElement MessageHTMLElement = waitAndReturnElement(Message);
        
        Assert.assertTrue(MessageHTMLElement.getText().contains("Your username is invalid!"));
    }


    @Test
    public void TestLoginValidUsernameInvalidPassword() {
        // test for valid username and invalid password
        this.Driver.get(Url);

        WebElement UsernameInput = waitAndReturnElement(FieldUsername);
        WebElement PasswordInput = waitAndReturnElement(FieldPassword);
        WebElement LoginButtonElement = waitAndReturnElement(ButtonLogin);

        UsernameInput.sendKeys(ValidUsername);
        PasswordInput.sendKeys("definetlywrongpassword");

        LoginButtonElement.click();
        WebElement MessageHTMLElement = waitAndReturnElement(Message);

        Assert.assertTrue(MessageHTMLElement.getText().contains("Your password is invalid!"));
    }


    @After
    public void close() {
        if (this.Driver != null) {
            this.Driver.quit();
        }
    }

}
