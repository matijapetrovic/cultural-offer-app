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
public class UpdateCategoryForm {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#update-category-input")
    private WebElement updateCategoryInput;

    @FindBy(css = "#update-category-button")
    private WebElement updateCategoryButton;

    public void updateCategory() {
        updateCategoryButton.click();
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(updateCategoryButton));
    }
}
