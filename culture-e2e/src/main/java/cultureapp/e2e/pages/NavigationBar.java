package cultureapp.e2e.pages;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RequiredArgsConstructor
public class NavigationBar {
    private final WebDriver driver;

    @FindBy(css = "#home-nav-link")
    private WebElement homePageLink;

    @FindBy(css = "#map-nav-link")
    private WebElement mapPageLink;

    @FindBy(css = "#dashboard-nav-link")
    private WebElement dashboardPageLink;

    @FindBy(css = "#categories-nav-link")
    private WebElement categoriesPageLink;

    @FindBy(css = "#subcategories-nav-link")
    private WebElement subcategoriesPageLink;

    @FindBy(css = "#news-nav-link")
    private WebElement newsPageLink;

    @FindBy(css = "#login-nav-link")
    private WebElement loginPageLink;

    @FindBy(css = "#register-nav-link")
    private WebElement registerPageLink;

    public void navigateToHomePage() {
        homePageLink.click();
    }
    public void navigateToMapPage() {
        mapPageLink.click();
    }
    public void navigateToDashboardPage() {
        dashboardPageLink.click();
    }
    public void navigateToCategoriesPage() {
        categoriesPageLink.click();
    }
    public void navigateToSubcategoriesPage() {
        subcategoriesPageLink.click();
    }
    public void navigateToNewsPage() {
        newsPageLink.click();
    }
    public void navigateToLoginPage() {
        loginPageLink.click();
    }
    public void navigateToRegisterPage() {
        registerPageLink.click();
    }

    public void ensureHomePageLinkIsDisplayed(WebDriver driver) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }
    public void ensureMapPageLinkIsDisplayed(WebDriver driver) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(mapPageLink));
    }
    public void ensureDashboardPageLinkIsDisplayed(WebDriver driver) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }
    public void ensureCategoriesPageLinkIsDisplayed(WebDriver driver) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }
    public void ensureSubcategoriesPageLinkIsDisplayed(WebDriver driver) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }
    public void ensureNewsPageLinkIsDisplayed(WebDriver driver) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }
    public void ensureLoginPageLinkIsDisplayed(WebDriver driver) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }
    public void ensureRegisterPageLinkIsDisplayed(WebDriver driver) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }

}
