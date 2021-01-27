package cultureapp.e2e.pages;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RequiredArgsConstructor
@Getter
public class NavigationBar {
    @Getter(value = AccessLevel.PRIVATE)
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

    @FindBy(css = "#cultural-offers-nav-link")
    private WebElement culturalOfferPageLink;

    public void ensureHomePageLinkIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }
    public void ensureMapPageLinkIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(mapPageLink));
    }
    public void ensureDashboardPageLinkIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }
    public void ensureCategoriesPageLinkIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }
    public void ensureSubcategoriesPageLinkIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }
    public void ensureNewsPageLinkIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }
    public void ensureCulturalOffersPageLinkIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }
    public void ensureLoginPageLinkIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }
    public void ensureRegisterPageLinkIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(homePageLink));
    }

}
