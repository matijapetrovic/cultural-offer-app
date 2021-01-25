package cultureapp.e2e.pages.category;

import lombok.AccessLevel;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Getter
public class CategoriesPage {
    @Getter(value= AccessLevel.PRIVATE)
    private final WebDriver driver;

    private final CategoriesList categoriesList;

    public CategoriesPage(WebDriver driver) {
        this.driver = driver;
        this.categoriesList = PageFactory.initElements(driver, CategoriesList.class);
    }

    public void ensureIsDisplayed() {
        categoriesList.ensureIsDisplayed();
    }
}
