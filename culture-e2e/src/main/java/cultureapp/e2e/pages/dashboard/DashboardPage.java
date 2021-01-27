package cultureapp.e2e.pages.dashboard;

import lombok.AccessLevel;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class DashboardPage {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".no-subscriptions-message")),
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-tabview"))));
    }

    public WebElement getCategoryTabLink(int index) {
        By selector = By.cssSelector(String.format(".p-tabview-nav > li:nth-child(%d) .p-tabview-nav-link", index));
        return (new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.elementToBeClickable(selector)));
    }

    public int getSubscriptionsCount() {
        By selector = By.cssSelector(".p-dataview-content .panel-card");
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.or(
                        ExpectedConditions.numberOfElementsToBeMoreThan(selector, 0),
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-dataview-emptymessage"))));
        return driver.findElements(selector).size();
    }

    public WebElement getUnsubscribeButton(int index) {
        By selector = By.cssSelector(String.format(".p-dataview-content .panel-card:nth-child(%d) .unsubscribe-dashboard-button", index));
        return driver.findElement(selector);
    }

    public WebElement getDetailsButton(int index) {
        By selector = By.cssSelector(String.format(".p-dataview-content .panel-card:nth-child(%d) .details-dashboard-button", index));
        return driver.findElement(selector);
    }

    public void ensureSuccessToastIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-success")));
    }

}
