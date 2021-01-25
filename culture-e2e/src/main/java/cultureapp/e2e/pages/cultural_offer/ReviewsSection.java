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
public class ReviewsSection {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = ".reviews-section .pagination-prev-button")
    private WebElement paginationPrevButton;

    @FindBy(css = ".reviews-section .pagination-next-button")
    private WebElement paginationNextButton;

    @FindBy(css = ".reviews-section .add-review-button")
    private WebElement addReviewButton;


    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".reviews-section")),
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".no-reviews-text"))
                ));
    }

    public WebElement getReviewComment(int index) {
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(String.format(".review-items > li:nth-child(%d) .offer-review .review-comment", index))));
    }

    public WebElement getReplyComment(int index) {
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(String.format(".review-items > li:nth-child(%d) .offer-review .reply-comment", index))));
    }

    public WebElement getReplyButton(int index) {
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(String.format(".review-items > li:nth-child(%d) .offer-review .review-reply-button", index))));
    }

}
