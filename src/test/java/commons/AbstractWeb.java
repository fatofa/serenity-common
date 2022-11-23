package commons;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AbstractWeb {
    protected WebDriver driver;

    public void openUrl(String url){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
    }

    public WebElement findElementByXpath(String locator){
        return driver.findElement(By.xpath(locator));
    }
    public void clickToElement(String locator){
        findElementByXpath(locator).click();
    }

    public void sendKeyToElement(String locator, String key){
        findElementByXpath(locator).sendKeys(key);
    }

    public String getInputText(String locator){
        return findElementByXpath(locator).getAttribute("value");
    }
    public void close(){
        driver.quit();
    }
}
