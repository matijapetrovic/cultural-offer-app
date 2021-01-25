package cultureapp.e2e;

import cultureapp.e2e.pages.category.CategoriesPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static cultureapp.e2e.Util.APP_URL;
import static cultureapp.e2e.Util.CHROME_DRIVER_PATH;

public class CategoryTest {
    private WebDriver browser;

    private CategoriesPage categoriesPage;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(APP_URL);

        categoriesPage = PageFactory.initElements(browser, CategoriesPage.class);
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }


    @Test
    public void addCategoryWithResults() {
        categoriesPage.ensureIsDisplayed();
    }
}
