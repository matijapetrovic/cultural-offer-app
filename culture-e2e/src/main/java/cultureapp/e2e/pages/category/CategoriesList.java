package cultureapp.e2e.pages.category;

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

@RequiredArgsConstructor
@Getter
public class CategoriesList {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#categories-table")
    private WebElement categoriesTable;

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(categoriesTable));
    }

    public int getIndexByCategoryName(String name) {
       int idx = -1;
       var categoryNames = getArrayOfNames();
       for (int i = 0; i < categoryNames.size(); i++) {
           if(categoryNames.get(i).equals(name)) {
               idx = i;
               break;
           }
       }
       return idx;
    }

    private List<String> getArrayOfNames() {
        return getCategoryNamesRows().stream().map(name -> name.getText()).collect(Collectors.toList());
    }

    public List<WebElement> getCategoryNamesRows() {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
                        categoriesTable, By.id("category-name")));
    }
}
