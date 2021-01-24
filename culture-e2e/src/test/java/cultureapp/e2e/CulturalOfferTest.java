package cultureapp.e2e;

import cultureapp.e2e.pages.LoginPage;
import cultureapp.e2e.pages.NavigationBar;
import cultureapp.e2e.pages.cultural_offer.CulturalOfferPage;
import cultureapp.e2e.pages.home.HomePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static cultureapp.e2e.Util.APP_URL;
import static cultureapp.e2e.Util.CHROME_DRIVER_PATH;

public class CulturalOfferTest {
    private WebDriver browser;

    private HomePage homePage;
    private NavigationBar navigationBar;
    private LoginPage loginPage;
    private CulturalOfferPage culturalOfferPage;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(APP_URL);

        culturalOfferPage = PageFactory.initElements(browser, CulturalOfferPage.class);culturalOfferPage = PageFactory.initElements(browser, CulturalOfferPage.class);
        homePage = PageFactory.initElements(browser, HomePage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        navigationBar = PageFactory.initElements(browser, NavigationBar.class);
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }

    @Test
    public void subscribeToOffer() {
        homePage.ensureIsDisplayed();
        navigationBar.ensureLoginPageLinkIsDisplayed();
        navigationBar.getLoginPageLink().click();

        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys("user1@gmail.com");
        loginPage.getPasswordInput().sendKeys("123");
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        browser.navigate().to(APP_URL + "/cultural-offers/1");

        culturalOfferPage.ensureIsDisplayed();
        culturalOfferPage.ensureIsDisplayedSubscribeButton();
        culturalOfferPage.getSubscribeButton().click();
        culturalOfferPage.getAcceptConfirmButton().click();

        culturalOfferPage.ensureToastIsDisplayed();
        culturalOfferPage.ensureIsDisplayedUnsubscribeButton();
    }

    @Test
    public void unsubscribeFromOffer() {
        homePage.ensureIsDisplayed();
        navigationBar.ensureLoginPageLinkIsDisplayed();
        navigationBar.getLoginPageLink().click();

        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys("user1@gmail.com");
        loginPage.getPasswordInput().sendKeys("123");
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        browser.navigate().to(APP_URL + "/cultural-offers/1");

        culturalOfferPage.ensureIsDisplayed();
        culturalOfferPage.ensureIsDisplayedUnsubscribeButton();
        culturalOfferPage.getUnsubscribeButton().click();
        culturalOfferPage.getAcceptConfirmButton().click();

        culturalOfferPage.ensureToastIsDisplayed();
        culturalOfferPage.ensureIsDisplayedSubscribeButton();
    }
}
