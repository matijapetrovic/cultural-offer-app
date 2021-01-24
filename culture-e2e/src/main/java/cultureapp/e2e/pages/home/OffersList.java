package cultureapp.e2e.pages.home;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RequiredArgsConstructor
public class OffersList {
    private final WebDriver driver;

    @FindBy(css = ".offer-filter-table")
    private WebElement offersListTable;

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(offersListTable));
    }

    public void ensureIsOfferCount(int count) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector(".offer-filter-table .offer-filter-item"), count));
    }
}
