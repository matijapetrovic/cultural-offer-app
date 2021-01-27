package cultureapp.e2e.pages.news;

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
public class NewsList {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#news-table")
    private WebElement categoriesTable;

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(categoriesTable));
    }

    public int getIndexByNewsTitle(String name) {
        int idx = -1;
        var newsNames = getArrayOfNames();
        for (int i = 0; i < newsNames.size(); i++) {
            if(newsNames.get(i).equals(name)) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    private List<String> getArrayOfNames() {
        return getNewsNamesRows().stream().map(name -> name.getText()).collect(Collectors.toList());
    }

    public List<WebElement> getNewsNamesRows() {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
                        categoriesTable, By.id("news-title")));
    }
}
