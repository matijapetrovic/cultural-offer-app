package cultureapp.e2e.pages.subcategory;

import cultureapp.e2e.elements.SelectWebElement;
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

    @FindBy(css = "#show-update-subcategory-button")
    private WebElement showUpdateSubcategoryFormButton;

    @FindBy(css = "#show-delete-subcategory-button")
    private WebElement showDeleteSubcategoryDialogButton;

    @FindBy(css = "#search-categories-input")
    private WebElement searchCategoriesInput;

    private SelectWebElement selectCategory;

    public void showAddForm() {
        subcategoriesList.ensureIsDisplayed();
        showAddSubcategoryFormButton.click();
    }

    public void showUpdateForm() {
        subcategoriesList.ensureIsDisplayed();
        showUpdateSubcategoryFormButton.click();
    }

    public void showDeleteDialog() {
        subcategoriesList.ensureIsDisplayed();
        showDeleteSubcategoryDialogButton.click();
    }

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
        createSelect();
    }

    private void createSelect() {
        selectCategory =  new SelectWebElement(driver, "#subcategory-category-select");
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
