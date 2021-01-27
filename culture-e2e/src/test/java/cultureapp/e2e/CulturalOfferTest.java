package cultureapp.e2e;

import cultureapp.e2e.pages.login.LoginPage;
import cultureapp.e2e.pages.NavigationBar;
import cultureapp.e2e.pages.cultural_offer.AddReplyDialog;
import cultureapp.e2e.pages.cultural_offer.AddReviewDialog;
import cultureapp.e2e.pages.cultural_offer.CulturalOfferPage;
import cultureapp.e2e.pages.home.HomePage;
import cultureapp.e2e.util.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static cultureapp.e2e.util.Util.APP_URL;
import static cultureapp.e2e.util.Util.CHROME_DRIVER_PATH;
import static cultureapp.e2e.common.CulturalOfferPageTestData.*;

import static org.junit.Assert.*;

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
        loginPage.getUsernameInput().sendKeys(REGULAR_USER_USERNAME);
        loginPage.getPasswordInput().sendKeys(REGULAR_USER_PASSWORD);
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        browser.navigate().to(String.format("%s/cultural-offers/%d", APP_URL, REGULAR_USER_UNSUBSCRIBED_OFFER_ID));

        culturalOfferPage.ensureIsDisplayed();
        culturalOfferPage.ensureIsDisplayedSubscribeButton();
        culturalOfferPage.getSubscribeButton().click();
        culturalOfferPage.getAcceptConfirmButton().click();

        culturalOfferPage.ensureSuccessToastIsDisplayed();
        culturalOfferPage.ensureIsDisplayedUnsubscribeButton();
    }

    @Test
    public void unsubscribeFromOffer() {
        homePage.ensureIsDisplayed();
        navigationBar.ensureLoginPageLinkIsDisplayed();
        navigationBar.getLoginPageLink().click();

        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(REGULAR_USER_USERNAME);
        loginPage.getPasswordInput().sendKeys(REGULAR_USER_PASSWORD);
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        browser.navigate().to(String.format("%s/cultural-offers/%d", APP_URL, REGULAR_USER_SUBSCRIBED_OFFER_ID));

        culturalOfferPage.ensureIsDisplayed();
        culturalOfferPage.ensureIsDisplayedUnsubscribeButton();
        culturalOfferPage.getUnsubscribeButton().click();
        culturalOfferPage.getAcceptConfirmButton().click();

        culturalOfferPage.ensureSuccessToastIsDisplayed();
        culturalOfferPage.ensureIsDisplayedSubscribeButton();
    }

    @Test
    public void navigateReviewsPagination() throws InterruptedException {
        homePage.ensureIsDisplayed();
        browser.navigate().to(String.format("%s/cultural-offers/%d", APP_URL, CULTURAL_OFFER_WITH_REVIEWS_ID));

        culturalOfferPage.ensureIsDisplayed();

        assertFalse(culturalOfferPage.getReviewsSection().getPaginationPrevButton().isEnabled());
        assertTrue(culturalOfferPage.getReviewsSection().getPaginationNextButton().isEnabled());

        for (int i = 0; i < OFFER_REVIEW_PAGE_COUNT - 2; i++) {
            culturalOfferPage.getReviewsSection().getPaginationNextButton().click();
            Util.wait(browser, 100);
            assertTrue(culturalOfferPage.getReviewsSection().getPaginationPrevButton().isEnabled());
            assertTrue(culturalOfferPage.getReviewsSection().getPaginationNextButton().isEnabled());
        }

        culturalOfferPage.getReviewsSection().getPaginationNextButton().click();
        Util.wait(browser, 100);
        assertTrue(culturalOfferPage.getReviewsSection().getPaginationPrevButton().isEnabled());
        assertFalse(culturalOfferPage.getReviewsSection().getPaginationNextButton().isEnabled());
    }

    @Test
    public void navigateNewsPagination() throws InterruptedException {
        homePage.ensureIsDisplayed();
        browser.navigate().to(String.format("%s/cultural-offers/%d", APP_URL, CULTURAL_OFFER_WITH_NEWS_ID));

        culturalOfferPage.ensureIsDisplayed();

        assertFalse(culturalOfferPage.getNewsSection().getPaginationPrevButton().isEnabled());
        assertTrue(culturalOfferPage.getNewsSection().getPaginationNextButton().isEnabled());

        for (int i = 0; i < OFFER_NEWS_PAGE_COUNT - 2; i++) {
            culturalOfferPage.getNewsSection().getPaginationNextButton().click();
            Util.wait(browser, 100);
            assertTrue(culturalOfferPage.getNewsSection().getPaginationPrevButton().isEnabled());
            assertTrue(culturalOfferPage.getNewsSection().getPaginationNextButton().isEnabled());
        }

        culturalOfferPage.getNewsSection().getPaginationNextButton().click();
        Util.wait(browser, 100);
        assertTrue(culturalOfferPage.getNewsSection().getPaginationPrevButton().isEnabled());
        assertFalse(culturalOfferPage.getNewsSection().getPaginationNextButton().isEnabled());
    }

    @Test
    public void addReviewSuccess() {
        homePage.ensureIsDisplayed();
        navigationBar.ensureLoginPageLinkIsDisplayed();
        navigationBar.getLoginPageLink().click();

        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(REGULAR_USER_USERNAME);
        loginPage.getPasswordInput().sendKeys(REGULAR_USER_PASSWORD);
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        browser.navigate().to(String.format("%s/cultural-offers/%d", APP_URL, NO_REVIEWS_OFFER_ID));

        culturalOfferPage.ensureIsDisplayed();
        culturalOfferPage.getReviewsSection().getAddReviewButton().click();
        AddReviewDialog dialog = culturalOfferPage.getAddReviewDialog();
        dialog.ensureIsDisplayed();
        dialog.setComment(REVIEW_TEXT);
        dialog.addImage(TEST_IMAGE_PATH);
        dialog.getAddButton().click();
        culturalOfferPage.ensureInfoToastIsDisplayed();
        culturalOfferPage.ensureSuccessToastIsDisplayed();

        culturalOfferPage.getReviewsSection().openReview(1);
        assertEquals(REVIEW_TEXT, culturalOfferPage.getReviewsSection().getOpenReviewComment().getText());
    }

    @Test
    public void addReviewNoComment() {
        homePage.ensureIsDisplayed();
        navigationBar.ensureLoginPageLinkIsDisplayed();
        navigationBar.getLoginPageLink().click();

        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(REGULAR_USER_USERNAME);
        loginPage.getPasswordInput().sendKeys(REGULAR_USER_PASSWORD);
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        browser.navigate().to(String.format("%s/cultural-offers/%d", APP_URL, NO_REVIEWS_OFFER_ID));

        culturalOfferPage.ensureIsDisplayed();
        culturalOfferPage.getReviewsSection().getAddReviewButton().click();
        AddReviewDialog dialog = culturalOfferPage.getAddReviewDialog();
        dialog.ensureIsDisplayed();
        dialog.addImage(TEST_IMAGE_PATH);

        assertFalse(dialog.getAddButton().isEnabled());
    }

    @Test
    public void replyToReviewSuccess() {
        homePage.ensureIsDisplayed();
        navigationBar.ensureLoginPageLinkIsDisplayed();
        navigationBar.getLoginPageLink().click();

        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(ADMINISTRATOR_USERNAME);
        loginPage.getPasswordInput().sendKeys(ADMINISTRATOR_PASSWORD);
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        browser.navigate().to(String.format("%s/cultural-offers/%d", APP_URL, REVIEWS_WITH_NO_REPLIES_OFFER_ID));

        culturalOfferPage.ensureIsDisplayed();
        culturalOfferPage.getReviewsSection().openReview(1);
        culturalOfferPage.getReviewsSection().getOpenReplyButton().click();
        AddReplyDialog dialog = culturalOfferPage.getAddReplyDialog();
        dialog.ensureIsDisplayed();
        dialog.setComment(REPLY_TEXT);
        dialog.getAddButton().click();
        culturalOfferPage.ensureSuccessToastIsDisplayed();

        culturalOfferPage.getReviewsSection().openReview(1);
        assertEquals(REPLY_TEXT, culturalOfferPage.getReviewsSection().getOpenReplyComment().getText());
    }

    @Test
    public void replyToReviewNoComment() {
        homePage.ensureIsDisplayed();
        navigationBar.ensureLoginPageLinkIsDisplayed();
        navigationBar.getLoginPageLink().click();

        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(ADMINISTRATOR_USERNAME);
        loginPage.getPasswordInput().sendKeys(ADMINISTRATOR_PASSWORD);
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        browser.navigate().to(String.format("%s/cultural-offers/%d", APP_URL, REVIEWS_WITH_NO_REPLIES_OFFER_ID));

        culturalOfferPage.ensureIsDisplayed();
        culturalOfferPage.getReviewsSection().openReview(2);
        culturalOfferPage.getReviewsSection().getOpenReplyButton().click();
        AddReplyDialog dialog = culturalOfferPage.getAddReplyDialog();
        dialog.ensureIsDisplayed();
        assertFalse(dialog.getAddButton().isEnabled());
    }

}
