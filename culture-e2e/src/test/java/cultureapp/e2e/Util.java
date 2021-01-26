package cultureapp.e2e;

import org.openqa.selenium.WebDriver;

public class Util {
    public static String APP_URL = "http://localhost:4200";

    public static String CHROME_DRIVER_PATH = "C:/javatools/selenium-chrome-driver-2018/chromedriver.exe";

    public static void wait(WebDriver driver, int milliseconds) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(milliseconds);
        }
    }

}
