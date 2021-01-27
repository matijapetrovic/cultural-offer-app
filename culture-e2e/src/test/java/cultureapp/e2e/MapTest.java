package cultureapp.e2e;

import cultureapp.e2e.exception.SelectDropdownNotOpen;
import cultureapp.e2e.pages.home.FilterForm;
import cultureapp.e2e.pages.home.HomePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static cultureapp.e2e.common.MapPageTestData.*;
import static cultureapp.e2e.util.Util.APP_URL;
import static cultureapp.e2e.util.Util.CHROME_DRIVER_PATH;

public class MapTest {
    private WebDriver browser;

    private HomePage homePage;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(APP_URL);

        homePage = PageFactory.initElements(browser, HomePage.class);
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }

    @Test
    public void filterByCategoryWithResults() throws SelectDropdownNotOpen {
        homePage.ensureIsDisplayed();
        homePage.getOffersList().ensureIsOfferCount(START_OFFER_COUNT);

        FilterForm filterForm = homePage.getFilterForm();
        filterForm.getCategorySelect().toggle();
        filterForm.getCategorySelect().ensureDropdownItemCount(FILTER_CATEGORY_COUNT);
        filterForm.getCategorySelect().chooseFromDropdown(15);
        filterForm.submit();

        homePage.getOffersList().ensureIsOfferCount(FILTER_CATEGORY_15_OFFER_COUNT);
    }

    @Test
    public void filterByCategoryAndSubcategoryWithResults() throws SelectDropdownNotOpen {
        homePage.ensureIsDisplayed();
        homePage.getOffersList().ensureIsOfferCount(START_OFFER_COUNT);

        FilterForm filterForm = homePage.getFilterForm();

        filterForm.getCategorySelect().toggle();
        filterForm.getCategorySelect().ensureDropdownItemCount(FILTER_CATEGORY_COUNT);
        filterForm.getCategorySelect().chooseFromDropdown(15);

        filterForm.getSubcategorySelect().toggle();
        filterForm.getSubcategorySelect().ensureDropdownItemCount(FILTER_CATEGORY_15_SUBCATEGORY_COUNT);
        filterForm.getSubcategorySelect().chooseFromDropdown(1);

        filterForm.submit();

        homePage.getOffersList().ensureIsOfferCount(FILTER_CATEGORY_15_SUBCATEGORY_1_OFFER_COUNT);
    }

    @Test
    public void filterByCategoryAndSubcategoryWithoutResults() throws SelectDropdownNotOpen {
        homePage.ensureIsDisplayed();
        homePage.getOffersList().ensureIsOfferCount(START_OFFER_COUNT);

        FilterForm filterForm = homePage.getFilterForm();

        filterForm.getCategorySelect().toggle();
        filterForm.getCategorySelect().ensureDropdownItemCount(FILTER_CATEGORY_COUNT);
        filterForm.getCategorySelect().chooseFromDropdown(15);

        filterForm.getSubcategorySelect().toggle();
        filterForm.getSubcategorySelect().ensureDropdownItemCount(FILTER_CATEGORY_15_SUBCATEGORY_COUNT);
        filterForm.getSubcategorySelect().chooseFromDropdown(2);

        filterForm.submit();

        homePage.getOffersList().ensureIsOfferCount(FILTER_CATEGORY_15_SUBCATEGORY_2_OFFER_COUNT);
    }

    @Test
    public void resetFilterForm() throws SelectDropdownNotOpen {
        homePage.ensureIsDisplayed();
        homePage.getOffersList().ensureIsOfferCount(START_OFFER_COUNT);

        FilterForm filterForm = homePage.getFilterForm();
        filterForm.getCategorySelect().toggle();
        filterForm.getCategorySelect().ensureDropdownItemCount(FILTER_CATEGORY_COUNT);
        filterForm.getCategorySelect().chooseFromDropdown(15);

        filterForm.submit();
        filterForm.reset();

        homePage.getOffersList().ensureIsOfferCount(START_OFFER_COUNT);
    }

    @Test
    public void searchValidLocation() {
        homePage.ensureIsDisplayed();
        homePage.getOffersList().ensureIsOfferCount(START_OFFER_COUNT);

        homePage.getSearchLocationForm().getLocationInput().sendKeys(SEARCH_VALID_LOCATION);
        homePage.getSearchLocationForm().submit();

        homePage.getOffersList().ensureIsOfferCount(SEARCH_VALID_LOCATION_OFFER_COUNT);
    }

    @Test
    public void searchInvalidLocation() {
        homePage.ensureIsDisplayed();
        homePage.getOffersList().ensureIsOfferCount(START_OFFER_COUNT);

        homePage.getSearchLocationForm().getLocationInput().sendKeys(SEARCH_INVALID_LOCATION);
        homePage.getSearchLocationForm().submit();

        homePage.ensureToastIsDisplayed();
        homePage.getOffersList().ensureIsOfferCount(START_OFFER_COUNT);
    }

}
