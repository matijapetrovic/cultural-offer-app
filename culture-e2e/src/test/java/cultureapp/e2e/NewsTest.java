package cultureapp.e2e;

import cultureapp.e2e.pages.NavigationBar;
import cultureapp.e2e.pages.cultural_offer_table.CulturalOffersTablePage;
import cultureapp.e2e.pages.home.HomePage;
import cultureapp.e2e.pages.login.LoginPage;
import cultureapp.e2e.pages.news.NewsPage;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static cultureapp.e2e.common.CulturalOfferPageTestData.*;
import static cultureapp.e2e.common.NewsPageTestData.*;
import static cultureapp.e2e.util.Util.APP_URL;
import static cultureapp.e2e.util.Util.CHROME_DRIVER_PATH;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NewsTest {
    private WebDriver browser;

    private HomePage homePage;
    private NavigationBar navigationBar;
    private LoginPage loginPage;
    private CulturalOffersTablePage culturalOffersTablePage;
    private NewsPage newsPage;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(APP_URL);

        homePage = PageFactory.initElements(browser, HomePage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        navigationBar = PageFactory.initElements(browser, NavigationBar.class);
        culturalOffersTablePage = PageFactory.initElements(browser, CulturalOffersTablePage.class);
        newsPage = PageFactory.initElements(browser, NewsPage.class);
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }

    private void loginAdmin() {
        homePage.ensureIsDisplayed();
        navigationBar.ensureLoginPageLinkIsDisplayed();
        navigationBar.getLoginPageLink().click();

        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(ADMIN_USERNAME);
        loginPage.getPasswordInput().sendKeys(ADMIN_PASSWORD);
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        navigationBar.getCulturalOfferPageLink().click();
        culturalOffersTablePage.ensureIsDisplayed();
        culturalOffersTablePage.showNewsPage();
        newsPage.ensureIsDisplayed();
    }

    @Test
    public void test1addValidNews() {
        loginAdmin();

        newsPage.getNewsList().ensureIsDisplayed();
        newsPage.showAddForm();
        newsPage.getNewsForm().ensureIsDisplayed();

        newsPage.getNewsForm().getNewsTitleFormInput().sendKeys(VALID_NEWS_TITLE);
        newsPage.getNewsForm().getNewsTextFormInput().sendKeys(VALID_NEWS_TEXT);
        newsPage.getNewsForm().getNewsImageFormInput().sendKeys(TEST_IMAGE_PATH_2);

        newsPage.getNewsForm().submitNews();
        newsPage.ensureSuccessToastIsDisplayed();

        browser.navigate().refresh();

        int index = newsPage.getNewsList().getIndexByNewsTitle(VALID_NEWS_TITLE);
        assertNotEquals(index, -1);
    }

    @Test
    public void test2addInvalidNewsData() {
        loginAdmin();

        newsPage.getNewsList().ensureIsDisplayed();
        newsPage.showAddForm();
        newsPage.getNewsForm().ensureIsDisplayed();

        newsPage.getNewsForm().getNewsTitleFormInput().sendKeys(INVALID_NEWS_TITLE);
        newsPage.getNewsForm().getNewsTextFormInput().sendKeys(VALID_NEWS_TEXT);
        newsPage.getNewsForm().getNewsImageFormInput().sendKeys(TEST_IMAGE_PATH_2);

        boolean isEnabled = newsPage.getNewsForm().isSubmitButtonDisabled();
        assertFalse(isEnabled);
    }

    @Test
    public void test3updateNews() {
        loginAdmin();

        newsPage.getNewsList().ensureIsDisplayed();
        newsPage.showUpdateForm();
        newsPage.getNewsForm().ensureIsDisplayed();

        newsPage.getNewsForm().getNewsTitleFormInput().clear();
        newsPage.getNewsForm().getNewsTextFormInput().clear();
        newsPage.getNewsForm().getNewsImageFormInput().clear();
        newsPage.getNewsForm().getNewsTitleFormInput().sendKeys(VALID_NEWS_TITLE_2);
        newsPage.getNewsForm().getNewsTextFormInput().sendKeys(VALID_NEWS_TEXT_2);
        newsPage.getNewsForm().getNewsImageFormInput().sendKeys(TEST_IMAGE_PATH_2);

        newsPage.getNewsForm().submitNews();
        newsPage.ensureSuccessToastIsDisplayed();

        browser.navigate().refresh();
        newsPage.getNewsList().ensureIsDisplayed();

        int index = newsPage.getNewsList().getIndexByNewsTitle(VALID_NEWS_TITLE_2);
        assertNotEquals(index, -1);
    }


    @Test
    public void test4deleteNews() {
        loginAdmin();
        addNews();

        newsPage.getNewsList().ensureIsDisplayed();
        newsPage.showDeleteDialog();
        newsPage.getDeleteNewsDialog().ensureIsDisplayed();
        newsPage.getDeleteNewsDialog().accept();

        newsPage.ensureInfoToastIsDisplayed();
        browser.navigate().refresh();

        int index = newsPage.getNewsList().getIndexByNewsTitle(VALID_NEWS_TITLE_2);
        assertNotEquals(index, -1);
    }

    @Test
    public void test5rejectNewsDeleteDialog() {
        loginAdmin();
        addNews();

        newsPage.getNewsList().ensureIsDisplayed();
        newsPage.showDeleteDialog();
        newsPage.getDeleteNewsDialog().ensureIsDisplayed();
        newsPage.getDeleteNewsDialog().reject();

        browser.navigate().refresh();
        newsPage.getNewsList().ensureIsDisplayed();

        int index = newsPage.getNewsList().getIndexByNewsTitle(VALID_NEWS_TITLE);
        assertNotEquals(index, -1);
    }

    private void addNews() {
        newsPage.showAddForm();
        newsPage.getNewsForm().ensureIsDisplayed();

        newsPage.getNewsForm().getNewsTitleFormInput().sendKeys(VALID_NEWS_TITLE);
        newsPage.getNewsForm().getNewsTextFormInput().sendKeys(VALID_NEWS_TEXT);
        newsPage.getNewsForm().getNewsImageFormInput().sendKeys(TEST_IMAGE_PATH_2);

        newsPage.getNewsForm().submitNews();
        newsPage.ensureSuccessToastIsDisplayed();

        browser.navigate().refresh();
        newsPage.getNewsList().ensureIsDisplayed();
    }
}
