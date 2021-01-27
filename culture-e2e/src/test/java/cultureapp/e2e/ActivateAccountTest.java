package cultureapp.e2e;

import cultureapp.e2e.pages.NavigationBar;
import cultureapp.e2e.pages.home.HomePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static cultureapp.e2e.common.AccountActivationTestData.*;
import static cultureapp.e2e.util.Util.APP_URL;
import static cultureapp.e2e.util.Util.CHROME_DRIVER_PATH;

public class ActivateAccountTest {
    private WebDriver browser;

    private HomePage homePage;
    private NavigationBar navigationBar;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(APP_URL);

        homePage = PageFactory.initElements(browser, HomePage.class);
        navigationBar = PageFactory.initElements(browser, NavigationBar.class);
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }

    @Test
    public void activateAccount() {
        browser.navigate().to(String.format("%s/auth/activate/%d", APP_URL, INACTIVE_ACCOUNT_ID));
        ensureSuccessToastIsDisplayed();
        homePage.ensureIsDisplayed();
    }

    @Test
    public void activateActiveAccount() {
        browser.navigate().to(String.format("%s/auth/activate/%d", APP_URL, ACTIVE_ACCOUNT_ID));
        ensureErrorToastIsDisplayed();
        homePage.ensureIsDisplayed();
    }

    public void ensureSuccessToastIsDisplayed() {
        (new WebDriverWait(browser, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-success")));
    }

    public void ensureErrorToastIsDisplayed() {
        (new WebDriverWait(browser, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-error")));
    }
}
