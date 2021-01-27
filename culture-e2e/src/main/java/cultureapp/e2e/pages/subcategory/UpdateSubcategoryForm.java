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
public class UpdateSubcategoryForm {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#update-subcategory-name-input")
    private WebElement updateSubcategoryInput;

    @FindBy(css = "#update-subcategory-submit-button")
    private WebElement updateSubcategoryButton;

    public void updateSubcategory() {
        updateSubcategoryButton.click();
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(updateSubcategoryButton));
    }
}
