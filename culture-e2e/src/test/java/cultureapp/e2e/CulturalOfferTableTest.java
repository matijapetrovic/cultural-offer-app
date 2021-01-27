package cultureapp.e2e;

import cultureapp.e2e.exception.SelectDropdownNotOpen;
import cultureapp.e2e.pages.NavigationBar;
import cultureapp.e2e.pages.cultural_offer_table.CulturalOfferForm;
import cultureapp.e2e.pages.cultural_offer_table.CulturalOffersTablePage;
import cultureapp.e2e.pages.home.HomePage;
import cultureapp.e2e.pages.login.LoginPage;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.*;

import static cultureapp.e2e.common.CulturalOfferPageTestData.*;
import static cultureapp.e2e.util.Util.APP_URL;
import static cultureapp.e2e.util.Util.CHROME_DRIVER_PATH;
import static org.junit.Assert.assertNotEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CulturalOfferTableTest {
    private WebDriver browser;

    private HomePage homePage;
    private NavigationBar navigationBar;
    private LoginPage loginPage;
    private CulturalOffersTablePage culturalOffersTablePage;

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
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }

    @Test
    public void test1addValidCulturalOffer() throws SelectDropdownNotOpen {
        loginAdmin();

        culturalOffersTablePage.showAddForm();
        culturalOffersTablePage.getCulturalOfferForm().ensureIsDisplayed();
        culturalOffersTablePage.getCulturalOfferForm().getAddNameInput().sendKeys(VALID_CULTURAL_OFFER_NAME);
        culturalOffersTablePage.getCulturalOfferForm().getAddDescriptionInput().sendKeys(VALID_CULTURAL_OFFER_DESCRIPTION);
        culturalOffersTablePage.getCulturalOfferForm().getAddImageInput().sendKeys(TEST_IMAGE_PATH_2);
        culturalOffersTablePage.getCulturalOfferForm().getAddAddressInput().sendKeys(VALID_ADDRESS);

        CulturalOfferForm form = culturalOffersTablePage.getCulturalOfferForm();
        form.getAddCategoryInput().toggle();
        form.getAddCategoryInput().ensureDropdownItemCount(NUMBER_OF_CATEGORIES);
        form.getAddCategoryInput().chooseFromDropdown(1);
        form.getAddSubcategoryInput().toggle();
        form.getAddSubcategoryInput().ensureDropdownItemCount(NUMBER_OF_SUBCATEGORIES);
        form.getAddSubcategoryInput().chooseFromDropdown(1);

        culturalOffersTablePage.getCulturalOfferForm().submitCulturalOffer();
        culturalOffersTablePage.ensureSuccessToastIsDisplayed();

        browser.navigate().refresh();
        culturalOffersTablePage.getCulturalOfferList().ensureIsDisplayed();

        int index = culturalOffersTablePage.getCulturalOfferList().getIndexByCulturalOfferName(VALID_CULTURAL_OFFER_NAME);
        assertNotEquals(index, -1);
    }

    @Test
    public void test2addInvalidCulturalOfferData() throws SelectDropdownNotOpen {
        loginAdmin();

        culturalOffersTablePage.showAddForm();
        culturalOffersTablePage.getCulturalOfferForm().ensureIsDisplayed();
        culturalOffersTablePage.getCulturalOfferForm().getAddNameInput().sendKeys(INVALID_CULTURAL_OFFER_NAME);
        culturalOffersTablePage.getCulturalOfferForm().getAddDescriptionInput().sendKeys(VALID_CULTURAL_OFFER_DESCRIPTION);
        culturalOffersTablePage.getCulturalOfferForm().getAddImageInput().sendKeys(TEST_IMAGE_PATH_2);
        culturalOffersTablePage.getCulturalOfferForm().getAddAddressInput().sendKeys(VALID_ADDRESS);

        CulturalOfferForm form = culturalOffersTablePage.getCulturalOfferForm();
        form.getAddCategoryInput().toggle();
        form.getAddCategoryInput().ensureDropdownItemCount(NUMBER_OF_CATEGORIES);
        form.getAddCategoryInput().chooseFromDropdown(1);
        form.getAddSubcategoryInput().toggle();
        form.getAddSubcategoryInput().ensureDropdownItemCount(NUMBER_OF_SUBCATEGORIES);
        form.getAddSubcategoryInput().chooseFromDropdown(1);

        boolean isEnabled = form.isSubmitButtonDisabled();
        assertFalse(isEnabled);
    }

    @Test
    public void test3updateCulturalOfferData() throws SelectDropdownNotOpen {
        loginAdmin();

        culturalOffersTablePage.showUpdateForm();
        culturalOffersTablePage.getCulturalOfferForm().ensureIsDisplayed();
        culturalOffersTablePage.getCulturalOfferForm().getAddNameInput().clear();
        culturalOffersTablePage.getCulturalOfferForm().getAddNameInput().sendKeys(UPDATE_VALID_CULTURAL_OFFER_NAME);
        culturalOffersTablePage.getCulturalOfferForm().getAddAddressInput().sendKeys(VALID_ADDRESS);

        CulturalOfferForm form = culturalOffersTablePage.getCulturalOfferForm();
        form.getAddCategoryInput().toggle();
        form.getAddCategoryInput().ensureDropdownItemCount(NUMBER_OF_CATEGORIES);
        form.getAddCategoryInput().chooseFromDropdown(1);
        form.getAddSubcategoryInput().toggle();
        form.getAddSubcategoryInput().ensureDropdownItemCount(NUMBER_OF_SUBCATEGORIES);
        form.getAddSubcategoryInput().chooseFromDropdown(1);

        culturalOffersTablePage.getCulturalOfferForm().submitCulturalOffer();
        culturalOffersTablePage.ensureSuccessToastIsDisplayed();

        browser.navigate().refresh();
        culturalOffersTablePage.getCulturalOfferList().ensureIsDisplayed();

        int index = culturalOffersTablePage.getCulturalOfferList().getIndexByCulturalOfferName(UPDATE_VALID_CULTURAL_OFFER_NAME);
        assertNotEquals(index, -1);
    }

    @Test
    public void test4deleteCulturalOffer() {
        loginAdmin();

        culturalOffersTablePage.showDeleteDialog();
        culturalOffersTablePage.getDeleteCulturalOfferDialog().ensureIsDisplayed();
        culturalOffersTablePage.getDeleteCulturalOfferDialog().accept();
        culturalOffersTablePage.ensureInfoToastIsDisplayed();

        browser.navigate().refresh();
        culturalOffersTablePage.getCulturalOfferList().ensureIsDisplayed();

        int index = culturalOffersTablePage.getCulturalOfferList().getIndexByCulturalOfferName(UPDATE_VALID_CULTURAL_OFFER_NAME);
        assertEquals(index, -1);
    }

    @Test
    public void test5rejectCulturalOfferDeleteDialog() {
        loginAdmin();

        culturalOffersTablePage.showDeleteDialog();
        culturalOffersTablePage.getDeleteCulturalOfferDialog().ensureIsDisplayed();
        culturalOffersTablePage.getDeleteCulturalOfferDialog().reject();

        browser.navigate().refresh();
        culturalOffersTablePage.getCulturalOfferList().ensureIsDisplayed();

        int index = culturalOffersTablePage.getCulturalOfferList().getIndexByCulturalOfferName(EXISTING_CULTURAL_OFFER_NAME);
        assertNotEquals(index, -1);
    }

}
