package cultureapp.e2e;

import cultureapp.e2e.pages.HomePage;
import cultureapp.e2e.pages.NavigationBar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static cultureapp.common.MapPageTestData.*;
import static cultureapp.e2e.Util.APP_URL;
import static cultureapp.e2e.Util.CHROME_DRIVER_PATH;
import static org.junit.Assert.assertTrue;

public class MapTest {
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
    public void filterByCategoryWithResults() {
        homePage.ensureIsDisplayed();
        homePage.ensureIsOfferCount(START_OFFER_COUNT);
        homePage.getFilterFormCategorySelect().click();
        homePage.ensureIsSelectCategoryCount(FILTER_CATEGORY_COUNT);

        homePage.chooseFilterFormCategory(1).click();

        homePage.getFilterFormSubmitButton().click();
        homePage.ensureIsOfferCount(FILTER_CATEGORY_1_OFFER_COUNT);
    }

    @Test
    public void filterByCategoryAndSubcategoryWithResults() {
        homePage.ensureIsDisplayed();
        homePage.ensureIsOfferCount(START_OFFER_COUNT);
        homePage.getFilterFormCategorySelect().click();
        homePage.ensureIsSelectCategoryCount(FILTER_CATEGORY_COUNT);

        homePage.chooseFilterFormCategory(1).click();
        homePage.getFilterFormSubcategorySelect().click();
        homePage.ensureIsSelectSubcategoryCount(FILTER_CATEGORY_1_SUBCATEGORY_COUNT);

        homePage.chooseFilterFormSubcategory(1).click();

        homePage.getFilterFormSubmitButton().click();
        homePage.ensureIsOfferCount(FILTER_CATEGORY_1_SUBCATEGORY_1_OFFER_COUNT);
    }

    @Test
    public void filterByCategoryAndSubcategoryWithoutResults() {
        homePage.ensureIsDisplayed();
        homePage.ensureIsOfferCount(START_OFFER_COUNT);

        homePage.getFilterFormCategorySelect().click();
        homePage.ensureIsSelectCategoryCount(FILTER_CATEGORY_COUNT);

        homePage.chooseFilterFormCategory(1).click();
        homePage.getFilterFormSubcategorySelect().click();
        homePage.ensureIsSelectSubcategoryCount(FILTER_CATEGORY_1_SUBCATEGORY_COUNT);

        homePage.chooseFilterFormSubcategory(2).click();

        homePage.getFilterFormSubmitButton().click();
        homePage.ensureIsOfferCount(FILTER_CATEGORY_1_SUBCATEGORY_2_OFFER_COUNT);
    }

    @Test
    public void searchValidLocation() {
        homePage.ensureIsDisplayed();
        homePage.ensureIsOfferCount(START_OFFER_COUNT);

        homePage.getSearchLocationFormLocationInput().sendKeys(SEARCH_VALID_LOCATION);
        homePage.getSearchLocationFormSubmitButton().click();

        homePage.ensureIsOfferCount(SEARCH_VALID_LOCATION_OFFER_COUNT);
    }

}
