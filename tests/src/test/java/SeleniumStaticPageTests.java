import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.junit.Assert;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.openqa.selenium.By;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@RunWith(Parameterized.class)
public class SeleniumStaticPageTests {

    public WebDriver driver;
    private MainPage mainPage;

    private String xpath;
    private String expectedUrlSnippet;
    private String expectedBodyText;

    public SeleniumStaticPageTests(String xpath, String expectedUrlSnippet, String expectedBodyText) {
        this.xpath = xpath;
        this.expectedUrlSnippet = expectedUrlSnippet;
        this.expectedBodyText = expectedBodyText;
    }

    @Parameters(name = "Test URL containing: {1}")
    public static Collection<Object[]> data() throws Exception {
        List<Object[]> params = new ArrayList<>();
        try (
            InputStream is = SeleniumStaticPageTests.class.getResourceAsStream("/static-pages.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {
            for (CSVRecord csvRecord : csvParser) {
                params.add(new Object[] { csvRecord.get(0), csvRecord.get(1), csvRecord.get(2) });
            }
        }
        return params;
    }

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444"), options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30)); // Add this
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
    }

    @Test
    public void testStaticPageLoadsCorrectly() {
        By locator = By.xpath(xpath); // We read it as a string from the file
        BasePage staticPage = mainPage.openAboutStaticPage(locator);
        Assert.assertTrue("URL does not contain: " + expectedUrlSnippet,
            staticPage.getPageUrl().contains(expectedUrlSnippet));
        Assert.assertTrue("BODY does not contain: " + expectedBodyText,
            staticPage.getBodyText().contains(expectedBodyText));
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}