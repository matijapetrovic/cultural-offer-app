package cultureapp.e2e.pages.cultural_offer_table;

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
public class CulturalOfferList {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#cultural-offers-table")
    private WebElement culturalOffersTable;

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(culturalOffersTable));
    }

    public int getIndexByCulturalOfferName(String name) {
        int idx = -1;
        var culturalOfferNames = getArrayOfNames();
        for (int i = 0; i < culturalOfferNames.size(); i++) {
            if(culturalOfferNames.get(i).equals(name)) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    private List<String> getArrayOfNames() {
        return getCulturalOfferNamesRows().stream().map(name -> name.getText()).collect(Collectors.toList());
    }

    public List<WebElement> getCulturalOfferNamesRows() {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
                        culturalOffersTable, By.id("cultural-offer-name")));
    }
}
