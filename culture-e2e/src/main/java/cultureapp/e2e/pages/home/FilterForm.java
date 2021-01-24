package cultureapp.e2e.pages.home;

import cultureapp.e2e.elements.SelectWebElement;
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
public class FilterForm {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    private final SelectWebElement categorySelect;
    private final SelectWebElement subcategorySelect;

    @FindBy(css = "#submit-filter-form")
    private WebElement submitButton;

    @FindBy(css = "#reset-filter-form")
    private WebElement resetButton;

    public FilterForm(WebDriver driver) {
        this.driver = driver;
        categorySelect = new SelectWebElement(driver,"#filter-category-select");
        subcategorySelect = new SelectWebElement(driver, "#filter-subcategory-select");
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(submitButton));
    }

    public void submit() {
        submitButton.click();
    }

    public void reset() {
        resetButton.click();
    }
}
