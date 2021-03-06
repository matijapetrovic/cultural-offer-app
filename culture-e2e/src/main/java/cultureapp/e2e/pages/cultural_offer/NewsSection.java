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
public class NewsSection {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = ".news-section .pagination-prev-button")
    private WebElement paginationPrevButton;

    @FindBy(css = ".news-section .pagination-next-button")
    private WebElement paginationNextButton;

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".news-section")),
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".no-news-text"))
                ));
    }
}
