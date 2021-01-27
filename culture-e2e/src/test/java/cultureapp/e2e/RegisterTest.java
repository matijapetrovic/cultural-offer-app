package cultureapp.e2e;

import cultureapp.e2e.pages.NavigationBar;
import cultureapp.e2e.pages.home.HomePage;
import cultureapp.e2e.pages.register.RegisterPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import static cultureapp.e2e.common.RegisterPageTestData.*;

import static cultureapp.e2e.util.Util.APP_URL;
import static cultureapp.e2e.util.Util.CHROME_DRIVER_PATH;

public class RegisterTest {
    private WebDriver browser;

    private HomePage homePage;
    private NavigationBar navigationBar;
    private RegisterPage registerPage;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(APP_URL);

        registerPage = PageFactory.initElements(browser, RegisterPage.class);
        homePage = PageFactory.initElements(browser, HomePage.class);
        navigationBar = PageFactory.initElements(browser, NavigationBar.class);
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }

    @Test
    public void registerValidUserData() {
        homePage.ensureIsDisplayed();
        navigationBar.ensureRegisterPageLinkIsDisplayed();
        navigationBar.getRegisterPageLink().click();

        registerPage.ensureIsDisplayed();
        registerPage.getFirstNameInput().sendKeys(VALID_FIRST_NAME);
        registerPage.getLastNameInput().sendKeys(VALID_LAST_NAME);
        registerPage.getUsernameInput().sendKeys(VALID_USERNAME);
        registerPage.getPasswordInput().sendKeys(VALID_PASSWORD);
        registerPage.getConfirmPasswordInput().sendKeys(VALID_PASSWORD);
        registerPage.submit();

        registerPage.ensureSuccessToastIsDisplayed();
    }

    @Test
    public void registerWithInvalidUsername() {
        homePage.ensureIsDisplayed();
        navigationBar.ensureRegisterPageLinkIsDisplayed();
        navigationBar.getRegisterPageLink().click();

        registerPage.ensureIsDisplayed();
        registerPage.getFirstNameInput().sendKeys(VALID_FIRST_NAME);
        registerPage.getLastNameInput().sendKeys(VALID_LAST_NAME);
        registerPage.getUsernameInput().sendKeys(INVALID_USERNAME);
        registerPage.getPasswordInput().sendKeys(INVALID_PASSWORD);
        registerPage.getConfirmPasswordInput().sendKeys(VALID_PASSWORD);
        registerPage.submit();

        registerPage.ensureWarnToastIsDisplayed();
    }

    @Test
    public void registerWithNotEqualPasswords() {
        homePage.ensureIsDisplayed();
        navigationBar.ensureRegisterPageLinkIsDisplayed();
        navigationBar.getRegisterPageLink().click();

        registerPage.ensureIsDisplayed();
        registerPage.getFirstNameInput().sendKeys(VALID_FIRST_NAME);
        registerPage.getLastNameInput().sendKeys(VALID_LAST_NAME);
        registerPage.getUsernameInput().sendKeys(VALID_USERNAME);
        registerPage.getPasswordInput().sendKeys(VALID_PASSWORD);
        registerPage.getConfirmPasswordInput().sendKeys(NOT_EQUl_VALID_CONFIRM_PASSWORD);
        registerPage.submit();

        registerPage.ensureWarnToastIsDisplayed();
    }

    @Test
    public void registerWithInvalidPassword() {
        homePage.ensureIsDisplayed();
        navigationBar.ensureRegisterPageLinkIsDisplayed();
        navigationBar.getRegisterPageLink().click();

        registerPage.ensureIsDisplayed();
        registerPage.getFirstNameInput().sendKeys(VALID_FIRST_NAME);
        registerPage.getLastNameInput().sendKeys(VALID_LAST_NAME);
        registerPage.getUsernameInput().sendKeys(VALID_USERNAME);
        registerPage.getPasswordInput().sendKeys(INVALID_PASSWORD);
        registerPage.getConfirmPasswordInput().sendKeys(INVALID_PASSWORD);
        registerPage.submit();

        registerPage.ensureErrorToastIsDisplayed();
    }
}
