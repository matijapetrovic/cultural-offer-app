package cultureapp.e2e.pages.subcategory;

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
public class SubcategoriesPage {
    @Getter(value= AccessLevel.PRIVATE)
    private final WebDriver driver;

    private final SubcategoriesList subcategoriesList;
    private final AddSubcategoryForm addSubcategoryForm;
    private final UpdateSubcategoryForm updateSubcategoryForm;
    private final DeleteSubcategoryDialog deleteSubcategoryDialog;

    @FindBy(css = "#show-add-subcategory-button")
    private WebElement showAddSubcategoryFormButton;

    @FindBy(css = "#show-update-category-form-button")
    private WebElement showUpdateSubcategoryFormButton;

    @FindBy(css = "#show-delete-category-dialog-button")
    private WebElement showDeleteSubcategoryDialogButton;

    @FindBy(css = "#search-categories-input")
    private WebElement searchCategoriesInput;

    public void showAddForm() { showAddSubcategoryFormButton.click(); }

    public void showUpdateForm() { showUpdateSubcategoryFormButton.click(); }

    public void showDeleteDialog() { showDeleteSubcategoryDialogButton.click(); }

    public SubcategoriesPage(WebDriver driver) {
        this.driver = driver;
        this.subcategoriesList = PageFactory.initElements(driver, SubcategoriesList.class);
        this.addSubcategoryForm = PageFactory.initElements(driver, AddSubcategoryForm.class);
        this.updateSubcategoryForm = PageFactory.initElements(driver, UpdateSubcategoryForm.class);
        this.deleteSubcategoryDialog = PageFactory.initElements(driver, DeleteSubcategoryDialog.class);
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(showAddSubcategoryFormButton));
    }

    public void ensureSuccessToastIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-success")));
    }

    public void ensureErrorToastIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-error")));
    }
}
