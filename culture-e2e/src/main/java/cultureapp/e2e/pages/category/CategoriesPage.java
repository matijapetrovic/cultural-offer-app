package cultureapp.e2e.pages.category;

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
public class CategoriesPage {
    @Getter(value= AccessLevel.PRIVATE)
    private final WebDriver driver;

    private final CategoriesList categoriesList;
    private final AddCategoryForm addCategoryForm;
    private final UpdateCategoryForm updateCategoryForm;
    private final DeleteCategoryDialog deleteCategoryDialog;

    @FindBy(css = "#show-add-category-form-button")
    private WebElement showAddCategoryFormButton;

    @FindBy(css = "#show-update-category-form-button")
    private WebElement showUpdateCategoryFormButton;

    @FindBy(css = "#show-delete-category-dialog-button")
    private WebElement showDeleteCategoryDialogButton;

    @FindBy(css = "#search-categories-input")
    private WebElement searchCategoriesInput;

    public void showAddForm() { showAddCategoryFormButton.click(); }

    public void showUpdateForm() { showUpdateCategoryFormButton.click(); }

    public void showDeleteDialog() { showDeleteCategoryDialogButton.click(); }

    public CategoriesPage(WebDriver driver) {
        this.driver = driver;
        this.categoriesList = PageFactory.initElements(driver, CategoriesList.class);
        this.addCategoryForm = PageFactory.initElements(driver, AddCategoryForm.class);
        this.updateCategoryForm = PageFactory.initElements(driver, UpdateCategoryForm.class);
        this.deleteCategoryDialog = PageFactory.initElements(driver, DeleteCategoryDialog.class);
    }

    public void ensureIsDisplayed() {
        categoriesList.ensureIsDisplayed();
    }

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
