package cultureapp.e2e.pages.subcategory;


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

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class SubcategoriesList {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#subcategories-table")
    private WebElement subcategoriesTable;

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(subcategoriesTable));
    }

    public int getIndexBySubcategoryName(String name) {
        int idx = -1;
        var subcategoryNames = getArrayOfNames();
        for (int i = 0; i < subcategoryNames.size(); i++) {
            if(subcategoryNames.get(i).equals(name)) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    private List<String> getArrayOfNames() {
        return getSubcategoryNamesRows().stream().map(name -> name.getText()).collect(Collectors.toList());
    }

    public List<WebElement> getSubcategoryNamesRows() {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
                        subcategoriesTable, By.cssSelector(".subcategory-name")));
    }
}
