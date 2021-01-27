package cultureapp.e2e;

import cultureapp.e2e.pages.login.LoginPage;
import cultureapp.e2e.pages.NavigationBar;
import cultureapp.e2e.pages.category.CategoriesPage;
import cultureapp.e2e.pages.home.HomePage;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.*;
import static cultureapp.e2e.util.Util.APP_URL;
import static cultureapp.e2e.util.Util.CHROME_DRIVER_PATH;
import static cultureapp.e2e.common.CulturalOfferPageTestData.*;
import static cultureapp.e2e.common.CategoryPageTestData.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryTest {
    private WebDriver browser;

    private HomePage homePage;
    private NavigationBar navigationBar;
    private LoginPage loginPage;
    private CategoriesPage categoriesPage;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(APP_URL);

        homePage = PageFactory.initElements(browser, HomePage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        navigationBar = PageFactory.initElements(browser, NavigationBar.class);
        categoriesPage = PageFactory.initElements(browser, CategoriesPage.class);
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
        navigationBar.getCategoriesPageLink().click();
        categoriesPage.ensureIsDisplayed();
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }

    @Test
    public void test1addValidCategory() {
        loginAdmin();

        categoriesPage.showAddForm();
        categoriesPage.getAddCategoryForm().ensureIsDisplayed();
        categoriesPage.getAddCategoryForm().getAddCategoryInput().sendKeys(NON_EXISTING_CATEGORY_NAME);
        categoriesPage.getAddCategoryForm().submitCategory();
        categoriesPage.ensureSuccessToastIsDisplayed();

        browser.navigate().refresh();
        categoriesPage.getCategoriesList().ensureIsDisplayed();

        int index = categoriesPage.getCategoriesList().getIndexByCategoryName(NON_EXISTING_CATEGORY_NAME);
        assertNotEquals(index, -1);
    }

    @Test
    public void test2addExistingCategory() {
        loginAdmin();

        categoriesPage.showAddForm();
        categoriesPage.getAddCategoryForm().ensureIsDisplayed();
        categoriesPage.getAddCategoryForm().getAddCategoryInput().sendKeys(EXISTING_CATEGORY_NAME);
        categoriesPage.getAddCategoryForm().submitCategory();
        categoriesPage.ensureErrorToastIsDisplayed();

        browser.navigate().refresh();
        categoriesPage.getCategoriesList().ensureIsDisplayed();
    }

    @Test
    public void test3updateValidCategory() {
        loginAdmin();

        categoriesPage.showUpdateForm();
        categoriesPage.getUpdateCategoryForm().ensureIsDisplayed();
        categoriesPage.getUpdateCategoryForm().getUpdateCategoryInput().clear();
        categoriesPage.getUpdateCategoryForm().getUpdateCategoryInput().sendKeys(NON_EXISTING_CATEGORY_NAME_2);
        categoriesPage.getUpdateCategoryForm().updateCategory();
        categoriesPage.ensureSuccessToastIsDisplayed();

        browser.navigate().refresh();
        categoriesPage.getCategoriesList().ensureIsDisplayed();

        int index = categoriesPage.getCategoriesList().getIndexByCategoryName(NON_EXISTING_CATEGORY_NAME_2);
        assertNotEquals(index, -1);
    }

    @Test
    public void test4deleteCategoryWithoutSubcategory() {
        loginAdmin();

        categoriesPage.ensureIsDisplayed();
        categoriesPage.showDeleteDialog();
        categoriesPage.getDeleteCategoryDialog().ensureIsDisplayed();
        categoriesPage.getDeleteCategoryDialog().accept();

        browser.navigate().refresh();
        categoriesPage.getCategoriesList().ensureIsDisplayed();

        int index = categoriesPage.getCategoriesList().getIndexByCategoryName(NON_EXISTING_CATEGORY_NAME_2);
        assertEquals(index, -1);
    }

    @Test
    public void test5rejectCategoryDeleteDialog() {
        loginAdmin();

        categoriesPage.ensureIsDisplayed();
        categoriesPage.showDeleteDialog();
        categoriesPage.getDeleteCategoryDialog().ensureIsDisplayed();
        categoriesPage.getDeleteCategoryDialog().reject();

        browser.navigate().refresh();
        categoriesPage.getCategoriesList().ensureIsDisplayed();

        int index = categoriesPage.getCategoriesList().getIndexByCategoryName(EXISTING_CATEGORY_NAME);
        assertNotEquals(index, -1);
    }

    @Test
    public void test6deleteCategoryWithSubcategory() {
        loginAdmin();

        categoriesPage.ensureIsDisplayed();
        categoriesPage.showDeleteDialog();
        categoriesPage.getDeleteCategoryDialog().ensureIsDisplayed();
        categoriesPage.getDeleteCategoryDialog().accept();
        categoriesPage.ensureErrorToastIsDisplayed();

        browser.navigate().refresh();
        categoriesPage.getCategoriesList().ensureIsDisplayed();

        int index = categoriesPage.getCategoriesList().getIndexByCategoryName(EXISTING_CATEGORY_NAME);
        assertNotEquals(index, -1);
    }
}
