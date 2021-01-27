package cultureapp.e2e;

import cultureapp.e2e.exception.SelectDropdownNotOpen;
import cultureapp.e2e.pages.NavigationBar;
import cultureapp.e2e.pages.home.HomePage;
import cultureapp.e2e.pages.login.LoginPage;
import cultureapp.e2e.pages.subcategory.SubcategoriesPage;
import cultureapp.e2e.util.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.*;
import static cultureapp.e2e.common.SubcategoryPageTestData.*;
import static cultureapp.e2e.common.CulturalOfferPageTestData.ADMIN_PASSWORD;
import static cultureapp.e2e.common.CulturalOfferPageTestData.ADMIN_USERNAME;
import static cultureapp.e2e.util.Util.APP_URL;
import static cultureapp.e2e.util.Util.CHROME_DRIVER_PATH;
import static org.junit.Assert.assertNotEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubcategoryTest {
    private WebDriver browser;

    private HomePage homePage;
    private NavigationBar navigationBar;
    private LoginPage loginPage;
    private SubcategoriesPage subcategoriesPage;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(APP_URL);

        homePage = PageFactory.initElements(browser, HomePage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        navigationBar = PageFactory.initElements(browser, NavigationBar.class);
        subcategoriesPage = PageFactory.initElements(browser, SubcategoriesPage.class);
    }

    private void loginAdmin() throws SelectDropdownNotOpen {
        homePage.ensureIsDisplayed();
        navigationBar.ensureLoginPageLinkIsDisplayed();
        navigationBar.getLoginPageLink().click();

        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(ADMIN_USERNAME);
        loginPage.getPasswordInput().sendKeys(ADMIN_PASSWORD);
        loginPage.getLoginButton().click();

        homePage.ensureIsDisplayed();
        navigationBar.getSubcategoriesPageLink().click();
    }

    private void chooseFirstCategory() throws SelectDropdownNotOpen {
        subcategoriesPage.getSelectCategory().toggle();
        subcategoriesPage.getSelectCategory().ensureDropdownItemCount(NUMBER_OF_CATEGORIES);
        subcategoriesPage.getSelectCategory().chooseFromDropdown(1);
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }

    @Test
    public void test1addSubcategory() throws SelectDropdownNotOpen {
        loginAdmin();

        subcategoriesPage.ensureIsDisplayed();
        chooseFirstCategory();

        subcategoriesPage.showAddForm();
        subcategoriesPage.getAddSubcategoryForm().ensureIsDisplayed();
        subcategoriesPage.getAddSubcategoryForm().getAddSubcategoryInput().sendKeys(VALID_SUBCATEGORY_NAME);
        subcategoriesPage.getAddSubcategoryForm().submitSubcategory();
        subcategoriesPage.ensureSuccessToastIsDisplayed();

        subcategoriesPage.getSubcategoriesList().ensureIsDisplayed();

        int index = subcategoriesPage.getSubcategoriesList().getIndexBySubcategoryName(VALID_SUBCATEGORY_NAME);
        assertNotEquals(index, -1);
    }

    @Test
    public void test2addExistingSubcategory() throws SelectDropdownNotOpen {
        loginAdmin();

        subcategoriesPage.ensureIsDisplayed();
        chooseFirstCategory();

        subcategoriesPage.showAddForm();
        subcategoriesPage.getAddSubcategoryForm().ensureIsDisplayed();
        subcategoriesPage.getAddSubcategoryForm().getAddSubcategoryInput().sendKeys(EXISTING_SUBCATEGORY_NAME);
        subcategoriesPage.getAddSubcategoryForm().submitSubcategory();
        subcategoriesPage.ensureErrorToastIsDisplayed();
    }

    @Test
    public void test3addInvalidSubcategoryName() throws SelectDropdownNotOpen {
        loginAdmin();

        subcategoriesPage.ensureIsDisplayed();
        chooseFirstCategory();

        subcategoriesPage.showAddForm();
        subcategoriesPage.getAddSubcategoryForm().ensureIsDisplayed();
        subcategoriesPage.getAddSubcategoryForm().getAddSubcategoryInput().sendKeys(INVALID_SUBCATEGORY_NAME);

        boolean isEnabled = subcategoriesPage.getAddSubcategoryForm().isEnabledSubmitButton();
        assertFalse(isEnabled);
    }

    @Test
    public void test4updateValidCategory() throws SelectDropdownNotOpen, InterruptedException {
        loginAdmin();

        subcategoriesPage.ensureIsDisplayed();
        chooseFirstCategory();

        subcategoriesPage.showUpdateForm();
        subcategoriesPage.getUpdateSubcategoryForm().ensureIsDisplayed();
        subcategoriesPage.getUpdateSubcategoryForm().getUpdateSubcategoryInput().clear();
        subcategoriesPage.getUpdateSubcategoryForm().getUpdateSubcategoryInput().sendKeys(VALID_SUBCATEGORY_NAME_2);
        subcategoriesPage.getUpdateSubcategoryForm().updateSubcategory();
        subcategoriesPage.ensureSuccessToastIsDisplayed();

        Util.wait(browser, 1000);

        int index = subcategoriesPage.getSubcategoriesList().getIndexBySubcategoryName(VALID_SUBCATEGORY_NAME_2);
        assertNotEquals(index, -1);
        Util.wait(browser, 3000);
    }

    @Test
    public void test5rejectCategoryDeleteDialog() throws SelectDropdownNotOpen, InterruptedException {
        loginAdmin();

        subcategoriesPage.ensureIsDisplayed();
        chooseFirstCategory();

        subcategoriesPage.ensureIsDisplayed();
        subcategoriesPage.showDeleteDialog();
        subcategoriesPage.getDeleteSubcategoryDialog().ensureIsDisplayed();
        subcategoriesPage.getDeleteSubcategoryDialog().reject();

        Util.wait(browser, 1000);

        int index = subcategoriesPage.getSubcategoriesList().getIndexBySubcategoryName(VALID_SUBCATEGORY_NAME_2);
        assertNotEquals(index, -1);
        Util.wait(browser, 3000);
    }

    @Test
    public void test6deleteSubcategory() throws SelectDropdownNotOpen, InterruptedException {
        loginAdmin();

        subcategoriesPage.ensureIsDisplayed();
        chooseFirstCategory();

        subcategoriesPage.ensureIsDisplayed();
        subcategoriesPage.showDeleteDialog();
        subcategoriesPage.getDeleteSubcategoryDialog().ensureIsDisplayed();
        subcategoriesPage.getDeleteSubcategoryDialog().accept();

        subcategoriesPage.getSubcategoriesList().ensureIsDisplayed();
        subcategoriesPage.ensureSuccessToastIsDisplayed();

        int index = subcategoriesPage.getSubcategoriesList().getIndexBySubcategoryName(VALID_SUBCATEGORY_NAME_2);
        assertEquals(index, -1);
        Util.wait(browser, 3000);
    }


}
