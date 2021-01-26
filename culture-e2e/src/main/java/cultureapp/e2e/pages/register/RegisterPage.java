package cultureapp.e2e.pages.register;

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
@RequiredArgsConstructor
public class RegisterPage {
    @Getter(value= AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#register-submit-button")
    private WebElement registerButton;

    @FindBy(css = "#register-first-name-input")
    private WebElement firstNameInput;

    @FindBy(css = "#register-last-name-input")
    private WebElement lastNameInput;

    @FindBy(css = "#register-username-input")
    private WebElement usernameInput;

    @FindBy(css = "#register-password-input")
    private WebElement passwordInput;

    @FindBy(css = "#register-confirm-password-input")
    private WebElement confirmPasswordInput;

    public void submit() {
        registerButton.click();
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(registerButton));
    }

    public void ensureSuccessToastIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-success")));
    }

    public void ensureInfoToastIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-info")));
    }

    public void ensureWarnToastIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-warn")));
    }

    public void ensureErrorToastIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-message-error")));
    }
}
