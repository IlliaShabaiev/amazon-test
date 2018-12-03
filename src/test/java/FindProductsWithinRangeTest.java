import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class FindProductsWithinRangeTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void before(){
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 30);
    }

    @Test
    public void findFirstFiveAirPads20To100dollars(){

        driver.manage().window().maximize();
        driver.navigate().to("https://www.amazon.com/");
        if (!driver.getTitle().contains("Amazon.com: Online Shopping for")) {
            throw new IllegalStateException("Couldn't navigate to amazon.com");
        }
        WebElement searchBar = driver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys("case ipad AIR2");
        WebElement searchButton = driver.findElement(By.className("nav-input"));
        searchButton.submit();
        wait.until(ExpectedConditions.titleContains("case ipad AIR2"));
        /** MIN price */
        WebElement min = driver.findElement(By.id("low-price"));
        min.sendKeys("20");
        /** MAX price*/
        WebElement max = driver.findElement(By.id("high-price"));
        max.sendKeys("100");

        WebElement buttonGo = driver.findElement(By.xpath("//*[@class='a-button-input' and @value='Go']"));
        buttonGo.submit();

        String nameOfTheItem = "(//*[@id='result_%d']//*[@class='a-row a-spacing-mini'])[1]//a";
        String priceOfTheItem = "(//*[@id='result_%d']//*[@class='a-row a-spacing-mini'])[2]//a";

        Map<Integer, String> productNames = new HashMap<>();
        Map<Integer, Float> productCost = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            String pathToCurrentName = String.format(nameOfTheItem, i);
            String pathToCurrentPrice = String.format(priceOfTheItem, i);

            String nameOfCurrentProduct = driver.findElement(By.xpath(pathToCurrentName)).getText();
            String priceOfCurrentProduct = driver.findElement(By.xpath(pathToCurrentPrice)).getText();
            priceOfCurrentProduct = priceOfCurrentProduct.replace("$ ", "");
            priceOfCurrentProduct = priceOfCurrentProduct.replace(" ", ".");

            Float price = Float.valueOf(priceOfCurrentProduct);

            productNames.put(i + 1, nameOfCurrentProduct);
            productCost.put(i + 1, price);
        }
        printPriceAndNames(productNames, productCost);

        Assert.assertEquals(productNames.size(), 5);
        Assert.assertEquals(productCost.size(), 5);
    }

    public void printPriceAndNames(Map<Integer, String> productNames, Map<Integer, Float> productValues){
        for (int i = 1; i<=5; i++){
            System.out.println("Product name: " + productNames.get(i));
            System.out.println("Product price: " + productValues.get(i));
            System.out.println("\n");
        }
    }

    @AfterClass
    public void after(){
        if (driver!=null){
            driver.quit();
            System.out.println("Successfully closed browser");
        }
    }

}
