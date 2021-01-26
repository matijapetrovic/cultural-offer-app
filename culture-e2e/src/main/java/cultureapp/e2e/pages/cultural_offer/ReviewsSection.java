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

    public void openReview(int index) {
        By selector =  By.cssSelector(String.format(".review-items p-accordiontab:nth-child(%d) .p-accordion-header-link", index));
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(selector)).click();
    }

    public WebElement getOpenReviewComment() {
        By selector = By.cssSelector(".p-accordion-tab-active .offer-review .review-comment");
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public WebElement getOpenReplyComment() {
        By selector = By.cssSelector(".p-accordion-tab-active .offer-review .reply-comment");
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public WebElement getOpenReplyButton() {
        By selector = By.cssSelector(".p-accordion-tab-active .offer-review .review-reply-button");
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

}
