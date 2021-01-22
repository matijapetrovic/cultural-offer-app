package cultureapp.e2e.pages;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

@RequiredArgsConstructor
@Getter
public class HomePage {
    @Getter(value= AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#filter-category-select")
    private WebElement filterFormCategorySelect;

    @FindBy(css = "#filter-subcategory-select")
    private WebElement filterFormSubcategorySelect;

    @FindBy(css = "#submit-filter-form")
    private WebElement filterFormSubmitButton;

    @FindBy(css = "#reset-filter-form")
    private WebElement filterFormResetButton;

    @FindBy(css = "#search-location-input")
    private WebElement searchLocationFormLocationInput;

    @FindBy(css = "#submit-search-location")
    private WebElement searchLocationFormSubmitButton;

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(filterFormSubmitButton));
    }

    public void ensureIsSelectCategoryCount(int count) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("#filter-category-select .ng-option"), count));
    }

    public void ensureIsSelectSubcategoryCount(int count) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("#filter-subcategory-select .ng-option"), count));
    }

    public void ensureIsOfferCount(int count) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector(".offer-filter-table .offer-filter-item"), count));
    }

    public WebElement chooseFilterFormCategory(int index) {
        String selector = String.format("#filter-category-select .ng-option:nth-child(%d)", index);
        return driver.findElement(By.cssSelector(selector));
    }

    public WebElement chooseFilterFormSubcategory(int index) {
        String selector = String.format("#filter-subcategory-select .ng-option:nth-child(%d)", index);
        return driver.findElement(By.cssSelector(selector));
    }

}
