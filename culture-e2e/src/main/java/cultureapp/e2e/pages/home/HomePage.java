package cultureapp.e2e.pages.home;

import lombok.AccessLevel;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class HomePage {
    @Getter(value= AccessLevel.PRIVATE)
    private final WebDriver driver;

    private final FilterForm filterForm;
    private final SearchLocationForm searchLocationForm;
    private final OffersList offersList;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.filterForm = PageFactory.initElements(driver, FilterForm.class);
        this.searchLocationForm = PageFactory.initElements(driver, SearchLocationForm.class);
        this.offersList = PageFactory.initElements(driver, OffersList.class);
    }

    public void ensureIsDisplayed() {
        filterForm.ensureIsDisplayed();
        offersList.ensureIsDisplayed();
        searchLocationForm.ensureIsDisplayed();
    }

    public void ensureToastIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-text")));
    }

}
