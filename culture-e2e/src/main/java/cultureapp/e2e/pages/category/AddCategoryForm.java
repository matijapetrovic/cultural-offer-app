package cultureapp.e2e.pages.category;

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
public class AddCategoryForm {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#add-category-input")
    private WebElement addCategoryInput;

    @FindBy(css = "#submit-category-button")
    private WebElement submitCategoryButton;

    public void submitCategory() {
        submitCategoryButton.click();
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(submitCategoryButton));
    }
}
