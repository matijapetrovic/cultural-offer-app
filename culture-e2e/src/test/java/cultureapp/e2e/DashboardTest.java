package cultureapp.e2e;

import cultureapp.e2e.pages.NavigationBar;
import cultureapp.e2e.pages.cultural_offer.CulturalOfferPage;
import cultureapp.e2e.pages.dashboard.DashboardPage;
import cultureapp.e2e.pages.home.HomePage;
import cultureapp.e2e.pages.login.LoginPage;
import cultureapp.e2e.util.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static cultureapp.e2e.common.CulturalOfferPageTestData.*;
import static cultureapp.e2e.common.CulturalOfferPageTestData.OFFER_NEWS_PAGE_COUNT;
import static cultureapp.e2e.util.Util.APP_URL;
import static cultureapp.e2e.util.Util.CHROME_DRIVER_PATH;
import static org.junit.Assert.*;

public class DashboardTest {
    private WebDriver browser;

    private HomePage homePage;
    private NavigationBar navigationBar;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CulturalOfferPage culturalOfferPage;


    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(APP_URL);

        dashboardPage = PageFactory.initElements(browser, DashboardPage.class);
        homePage = PageFactory.initElements(browser, HomePage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        navigationBar = PageFactory.initElements(browser, NavigationBar.class);
        culturalOfferPage = PageFactory.initElements(browser, CulturalOfferPage.class);
    }


    @After
    public void closeSelenium() {
        browser.quit();
    }


    @Test
    public void unsubscribeFromOffer() throws InterruptedException {
        homePage.ensureIsDisplayed();
        navigationBar.ensureLoginPageLinkIsDisplayed();
        navigationBar.getLoginPageLink().click();

        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(REGULAR_USER_USERNAME);
        loginPage.getPasswordInput().sendKeys(REGULAR_USER_PASSWORD);
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        navigationBar.ensureDashboardPageLinkIsDisplayed();
        navigationBar.getDashboardPageLink().click();

        dashboardPage.ensureIsDisplayed();
        int count = dashboardPage.getSubscriptionsCount();
        assertTrue(count > 0);

        String name = dashboardPage.getSubscriptionOfferName(1);
        dashboardPage.getUnsubscribeButton(1).click();

        dashboardPage.ensureSuccessToastIsDisplayed();
        Util.wait(browser, 5000);
        assertNotEquals(name, dashboardPage.getSubscriptionOfferName(1));
    }

    @Test
    public void changeCategoryAndViewSubscriptionOfferDetails() {
        homePage.ensureIsDisplayed();
        navigationBar.ensureLoginPageLinkIsDisplayed();
        navigationBar.getLoginPageLink().click();

        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(REGULAR_USER_USERNAME);
        loginPage.getPasswordInput().sendKeys(REGULAR_USER_PASSWORD);
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        navigationBar.ensureDashboardPageLinkIsDisplayed();
        navigationBar.getDashboardPageLink().click();

        dashboardPage.ensureIsDisplayed();
        dashboardPage.getCategoryTabLink(2).click();

        int count = dashboardPage.getSubscriptionsCount();
        assertTrue(count > 0);
        dashboardPage.getDetailsButton(1).click();
        culturalOfferPage.ensureIsDisplayed();
        assertTrue(browser.getCurrentUrl().contains("cultural-offers"));
    }

    @Test
    public void navigateSubcategoryPagination() throws InterruptedException {
        homePage.ensureIsDisplayed();
        navigationBar.ensureLoginPageLinkIsDisplayed();
        navigationBar.getLoginPageLink().click();

        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(REGULAR_USER_USERNAME);
        loginPage.getPasswordInput().sendKeys(REGULAR_USER_PASSWORD);
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        navigationBar.ensureDashboardPageLinkIsDisplayed();
        navigationBar.getDashboardPageLink().click();

        dashboardPage.ensureIsDisplayed();
        assertTrue(dashboardPage.getSubscriptionsCount() > 0);
        String name = dashboardPage.getSubscriptionOfferName(1);
        assertFalse(dashboardPage.getSubcategoryPaginationPrevButton().isEnabled());
        assertTrue(dashboardPage.getSubcategoryPaginationNextButton().isEnabled());

        dashboardPage.getSubcategoryPaginationNextButton().click();
        Util.wait(browser, 5000);
        assertTrue(dashboardPage.getSubcategoryPaginationPrevButton().isEnabled());
        assertTrue(dashboardPage.getSubcategoryPaginationNextButton().isEnabled());
        assertTrue(dashboardPage.getSubscriptionsCount() > 0);
        assertNotEquals(name, dashboardPage.getSubscriptionOfferName(1));
    }

    @Test
    public void navigateSubscriptionPagination() throws InterruptedException {
        homePage.ensureIsDisplayed();
        navigationBar.ensureLoginPageLinkIsDisplayed();
        navigationBar.getLoginPageLink().click();

        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(REGULAR_USER_USERNAME);
        loginPage.getPasswordInput().sendKeys(REGULAR_USER_PASSWORD);
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        navigationBar.ensureDashboardPageLinkIsDisplayed();
        navigationBar.getDashboardPageLink().click();

        dashboardPage.ensureIsDisplayed();
        assertTrue(dashboardPage.getSubscriptionsCount() > 0);
        String name = dashboardPage.getSubscriptionOfferName(1);
        assertFalse(dashboardPage.getSubscriptionPaginationPrevButton().isEnabled());
        assertTrue(dashboardPage.getSubscriptionPaginationNextButton().isEnabled());

        dashboardPage.getSubscriptionPaginationNextButton().click();
        Util.wait(browser, 5000);
        assertTrue(dashboardPage.getSubscriptionPaginationPrevButton().isEnabled());
        assertTrue(dashboardPage.getSubscriptionsCount() > 0);
        assertNotEquals(name, dashboardPage.getSubscriptionOfferName(1));
    }


}
