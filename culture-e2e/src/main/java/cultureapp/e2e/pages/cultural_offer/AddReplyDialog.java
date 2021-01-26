package cultureapp.e2e.pages.cultural_offer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RequiredArgsConstructor
@Getter
public class AddReplyDialog {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#submit-add-reply")
    private WebElement addButton;

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(addButton));
    }

    public void setComment(String comment) {
        String selector = "#add-reply-comment";
        WebElement textArea = driver.findElement(By.cssSelector(selector));
        textArea.click();
        textArea.sendKeys(comment);
    }
}
