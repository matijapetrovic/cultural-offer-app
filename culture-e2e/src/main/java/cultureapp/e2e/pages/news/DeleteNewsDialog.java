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
public class DeleteNewsDialog {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = ".p-confirm-dialog-accept")
    private WebElement acceptButton;

    @FindBy(css = ".p-confirm-dialog-reject")
    private WebElement rejectButton;

    public void accept() {
        acceptButton.click();
    }

    public void reject() {
        rejectButton.click();
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(acceptButton));
    }
}
