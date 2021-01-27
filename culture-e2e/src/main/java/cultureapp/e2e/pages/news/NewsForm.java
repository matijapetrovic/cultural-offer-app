package cultureapp.e2e.pages.news;

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
public class NewsForm {

    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#news-form-title-input")
    private WebElement newsTitleFormInput;

    @FindBy(css = "#news-text-input")
    private WebElement newsTextFormInput;

    @FindBy(css = "#news-images-input")
    private WebElement newsImageFormInput;

    @FindBy(css = "#submit-news-form-button")
    private WebElement submitButton;

    public void submitNews() {
        submitButton.click();
    }

    public boolean isSubmitButtonDisabled() {
        return submitButton.isEnabled();
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(submitButton));
    }
}
