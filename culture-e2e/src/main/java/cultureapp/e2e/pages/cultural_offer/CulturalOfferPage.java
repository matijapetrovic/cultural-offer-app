package cultureapp.e2e.pages.cultural_offer;

import lombok.AccessLevel;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class CulturalOfferPage {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    private final ReviewsSection reviewsSection;
    private final NewsSection newsSection;

    @FindBy(css = "#subscribe-button")
    private WebElement subscribeButton;
    @FindBy(css = "#unsubscribe-button")
    private WebElement unsubscribeButton;

    @FindBy(css = ".accept-confirm-button")
    private WebElement acceptConfirmButton;

    @FindBy(css = ".reject-confirm-button")
    private WebElement rejectConfirmButton;

    public CulturalOfferPage(WebDriver driver) {
        this.driver = driver;
        this.reviewsSection = PageFactory.initElements(driver, ReviewsSection.class);
        this.newsSection = PageFactory.initElements(driver, NewsSection.class);
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".offer-description")));
        reviewsSection.ensureIsDisplayed();
        newsSection.ensureIsDisplayed();
    }

    public void ensureIsDisplayedSubscribeButton() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(subscribeButton));
    }

    public void ensureIsDisplayedUnsubscribeButton() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(unsubscribeButton));
    }

    public void ensureToastIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-text")));
    }
}
