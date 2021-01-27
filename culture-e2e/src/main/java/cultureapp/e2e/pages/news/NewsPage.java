package cultureapp.e2e.pages.news;

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
public class NewsPage {
    @Getter(value= AccessLevel.PRIVATE)
    private final WebDriver driver;

    private final NewsList newsList;
    private final NewsForm newsForm;
    private final DeleteNewsDialog deleteNewsDialog;

    @FindBy(css = "#show-add-news-form-button")
    private WebElement showAddNewsFormButton;

    @FindBy(css = "#show-update-news-form-button")
    private WebElement showUpdateNewsFormButton;

    @FindBy(css = "#show-delete-news-dialog-button")
    private WebElement showDeleteNewsDialogButton;

    @FindBy(css = "#search-news-input")
    private WebElement searchNewsInput;

    public NewsPage(WebDriver driver) {
        this.driver = driver;
        this.newsList = PageFactory.initElements(driver, NewsList.class);
        this.newsForm = PageFactory.initElements(driver, NewsForm.class);
        this.deleteNewsDialog = PageFactory.initElements(driver, DeleteNewsDialog.class);
    }

    public void ensureIsDisplayed() {
        newsList.ensureIsDisplayed();
    }

    public void showAddForm() { showAddNewsFormButton.click(); }

    public void showUpdateForm() { showUpdateNewsFormButton.click(); }

    public void showDeleteDialog() { showDeleteNewsDialogButton.click(); }

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
