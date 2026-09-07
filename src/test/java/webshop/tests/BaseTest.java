package webshop.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import webshop.config.Config;
import webshop.pages.HomePage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.microsoft.playwright.assertions.PlaywrightAssertions
        .setDefaultAssertionTimeout;

public abstract class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected HomePage homePage;

    @BeforeMethod
    public void setUp() throws Throwable {
        try {
            playwright = Playwright.create();

            browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(Config.headless())
            );

            context = browser.newContext(
                    new Browser.NewContextOptions()
                            .setBaseURL(Config.baseUrl())
                            .setViewportSize(1440, 900)
            );

            context.setDefaultTimeout(Config.timeoutMs());
            context.setDefaultNavigationTimeout(Config.timeoutMs());

            setDefaultAssertionTimeout(Config.timeoutMs());

            page = context.newPage();
            homePage = new HomePage(page).open();

        } catch (Throwable failure) {
            saveScreenshot("setup");
            throw failure;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                saveScreenshot(result.getMethod().getMethodName());
            }
        } finally {
            closeResources();
        }
    }

    private void saveScreenshot(String testName) {
        if (page == null || page.isClosed()) {
            return;
        }

        try {
            Path directory = Paths.get("build", "screenshots");
            Files.createDirectories(directory);

            Path file = directory.resolve(
                    testName + "-" + UUID.randomUUID() + ".png"
            );

            page.screenshot(
                    new Page.ScreenshotOptions()
                            .setPath(file)
                            .setFullPage(true)
                            .setTimeout(5000)
            );

            System.out.println(
                    "Screenshot saved: " + file.toAbsolutePath()
            );

        } catch (Exception e) {
            System.err.println(
                    "Не вдалося зберегти скриншот: " + e.getMessage()
            );
        }
    }

    private void closeResources() {
        try {
            if (context != null) {
                context.close();
            }
        } finally {
            try {
                if (browser != null) {
                    browser.close();
                }
            } finally {
                try {
                    if (playwright != null) {
                        playwright.close();
                    }
                } finally {
                    homePage = null;
                    page = null;
                    context = null;
                    browser = null;
                    playwright = null;
                }
            }
        }
    }
}