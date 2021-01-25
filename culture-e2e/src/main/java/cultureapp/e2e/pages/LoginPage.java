package cultureapp.e2e.pages;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
@RequiredArgsConstructor
public class LoginPage {
    @Getter(value= AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#login-username-input")
    private WebElement usernameInput;

    @FindBy(css = "#login-password-input")
    private WebElement passwordInput;

    @FindBy(css = "#login-submit-button")
    private WebElement loginButton;

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(loginButton));
    }
}
