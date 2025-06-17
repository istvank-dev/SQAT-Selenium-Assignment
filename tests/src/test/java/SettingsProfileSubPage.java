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
import org.openqa.selenium.JavascriptExecutor;


class SettingsProfileSubPage extends SettingsBasePage {

    private By anonymousHoverElementLocator = By.xpath("//div[@id='tiptip_content' and contains(text(), 'anonymous')]");
    private By webpageInputHoverElementLocator = By.xpath("//div[@id='tiptip_content' and contains(text(), 'http://weboldala.hu')]");
    private By inputUsernameLocator = By.xpath("//input[contains(@name,'username')]");
    private By bioTextareaLocator = By.xpath("//textarea[@name='bio']");
    private By avatarUploadButtonLocator = By.xpath("//a[@data-trigger='user-avatar-upload']");
    private By avatarUploadInputLocator = By.xpath("//div[@div='user-avatar-upload']"); // Hidden by default
    private By avatarDeleteButtonLocator = By.xpath("//a[@data-action='delete-avatar']");


    public SettingsProfileSubPage(WebDriver driver) {
        super(driver);
    }
    
    public void addAboutText(String text) {
        goToProfileSubPage();

        WebElement bioTextarea = waitAndReturnElement(bioTextareaLocator);
        bioTextarea.clear();
        bioTextarea.sendKeys(text);

        submitForm();
    }

    public String getAboutText() {
        goToProfileSubPage();

        WebElement bioTextarea = waitAndReturnElement(bioTextareaLocator);
        return bioTextarea.getText();
    }

    public void uploadAvatarImage(String imagePath) {
        goToProfileSubPage();
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement avatarUploadInput = (WebElement) js.executeScript("return document.getElementById('user-avatar-upload');");

        if (avatarUploadInput == null) {
            throw new NoSuchElementException("Avatar upload input element not found.");
        }

        js.executeScript("arguments[0].style.display='block'; arguments[0].style.visibility='visible';", avatarUploadInput);

        //WebDriverWait wait = new WebDriverWait(driver, 10);
        //wait.until(ExpectedConditions.visibilityOf(avatarUploadInput));
        //wait.until(ExpectedConditions.elementToBeClickable(avatarUploadInput));

        File img = new File(imagePath);
        if (img.exists()) {
            System.out.println("Uploading file from path: " + img.getAbsolutePath());
            avatarUploadInput.sendKeys(img.getAbsolutePath());
        } else {
            throw new IllegalArgumentException("Image file does not exist: " + imagePath);
        }
    }

    public void deleteAvatarImage() {
        goToProfileSubPage();
        
        WebElement avatarDeleteButton = waitAndReturnElement(avatarDeleteButtonLocator);
        avatarDeleteButton.click();
    }

    public boolean isDeleteAvatarButtonDisplayed() {
        goToProfileSubPage(); // It can only be on the profile subpage.
        return isWebElementDisplayed(avatarDeleteButtonLocator);
    }
}
