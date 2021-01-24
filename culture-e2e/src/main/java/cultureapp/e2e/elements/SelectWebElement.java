package cultureapp.e2e.elements;

import cultureapp.e2e.exception.SelectDropdownNotOpen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelectWebElement {
    private final WebDriver driver;
    private final String cssSelector;
    private final WebElement element;

    private boolean isOpen;

    public SelectWebElement(WebDriver driver, String cssSelector) {
        this.driver = driver;
        this.cssSelector = cssSelector;
        this.element = driver.findElement(By.cssSelector(cssSelector));
        this.isOpen = false;
    }

    public void toggle() {
        element.click();
        isOpen = true;
    }

    public void ensureDropdownItemCount(int count) throws SelectDropdownNotOpen {
        if (!isOpen)
            throw new SelectDropdownNotOpen();

        String selector = String.format("%s .ng-option", cssSelector);
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector(selector), count));
    }

    public void chooseFromDropdown(int index) throws SelectDropdownNotOpen {
        if (!isOpen)
            throw new SelectDropdownNotOpen();

        String selector = String.format("%s .ng-option:nth-child(%d)", cssSelector, index);
        driver.findElement(By.cssSelector(selector)).click();
    }
}
