package cultureapp.e2e.pages.subcategory;

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
public class AddSubcategoryForm {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#add-subcategory-name-input")
    private WebElement addSubcategoryInput;

    @FindBy(css = "#add-subcategory-submit-button")
    private WebElement submitSubcategoryButton;

    public void submitSubcategory() {
        submitSubcategoryButton.click();
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(submitSubcategoryButton));
    }

    public boolean isEnabledSubmitButton() {
        return submitSubcategoryButton.isEnabled();
    }
}
