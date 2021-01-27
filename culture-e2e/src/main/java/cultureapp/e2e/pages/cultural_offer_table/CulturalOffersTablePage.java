package cultureapp.e2e.pages.cultural_offer_table;

import cultureapp.e2e.pages.category.CategoriesList;
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
public class CulturalOffersTablePage {
    @Getter(value= AccessLevel.PRIVATE)
    private final WebDriver driver;

    private final CulturalOfferList culturalOfferList;
    private final DeleteCulturalOfferDialog deleteCulturalOfferDialog;
    private CulturalOfferForm culturalOfferForm;

    @FindBy(css = "#show-add-cultural-offer-form-button")
    private WebElement showAddCulturalOfferFormButton;

    @FindBy(css = "#show-update-cultural-offer-form-button")
    private WebElement showUpdateCulturalOfferFormButton;

    @FindBy(css = "#show-delete-cultural-offer-dialog-button")
    private WebElement showDeleteCulturalOfferDialogButton;

    @FindBy(css = "#show-news-page-button")
    private WebElement showNewsPageButton;

    public CulturalOffersTablePage(WebDriver driver) {
        this.driver = driver;
        this.culturalOfferList = PageFactory.initElements(driver, CulturalOfferList.class);
        this.deleteCulturalOfferDialog = PageFactory.initElements(driver, DeleteCulturalOfferDialog.class);
    }

    private void createCulturalOfferForm() {
        this.culturalOfferForm = PageFactory.initElements(driver, CulturalOfferForm.class);
    }

    public void ensureIsDisplayed() {
        culturalOfferList.ensureIsDisplayed();
    }

    public void showAddForm() {
        showAddCulturalOfferFormButton.click();
        createCulturalOfferForm();
    }

    public void showUpdateForm() {
        showUpdateCulturalOfferFormButton.click();
        createCulturalOfferForm();
    }

    public void showDeleteDialog() { showDeleteCulturalOfferDialogButton.click(); }

    public void showNewsPage() { showNewsPageButton.click(); }

    public void ensureSuccessToastIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-success")));
    }

    public void ensureInfoToastIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-info")));
    }

    public void ensureErrorToastIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-error")));
    }
}
