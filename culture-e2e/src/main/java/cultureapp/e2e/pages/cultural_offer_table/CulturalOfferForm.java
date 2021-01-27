package cultureapp.e2e.pages.cultural_offer_table;

import cultureapp.e2e.elements.SelectWebElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class CulturalOfferForm {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    private SelectWebElement addCategoryInput;
    private SelectWebElement addSubcategoryInput;

    @FindBy(css = "#cultural-offer-name-input")
    private WebElement addNameInput;

    @FindBy(css = "#cultural-offer-address-input")
    private WebElement addAddressInput;

    @FindBy(css = "#cultural-offer-description-input")
    private WebElement addDescriptionInput;

    @FindBy(css = "#cultural-offer-images")
    private WebElement addImageInput;

    @FindBy(css = "#cultural-offer-submit-button")
    private WebElement submitCulturalOfferButton;

    public CulturalOfferForm(WebDriver driver) {
        this.driver = driver;
        addCategoryInput = new SelectWebElement(driver,"#cultural-offer-category-select");
        addSubcategoryInput = new SelectWebElement(driver, "#cultural-offer-subcategory-select");
    }

    public void submitCulturalOffer() {
        submitCulturalOfferButton.click();
    }

    public boolean isSubmitButtonDisabled() {
        return submitCulturalOfferButton.isEnabled();
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(submitCulturalOfferButton));
    }
}
