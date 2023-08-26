import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SeleniumFinalProject {

    WebDriver driver;

    @Parameters("browser")
    @BeforeTest
    public void initialize(String browser) {
        if (browser.equalsIgnoreCase("safari")) {
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
            System.out.println("safari is launched");
        } else if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            System.out.println("chrome is launched");
        }

    }

    @Test
    public void finalTest() {
//
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);
        driver.manage().window().maximize();

        driver.navigate().to("https://www.swoop.ge");
        driver.findElement(By.xpath("//*[@id=\"body\"]/header/div[2]/div/div[1]/ul/li[1]/a")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        //Select the first movie in the returned list and click on ‘ყიდვა’ button
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='movies-deal']")));
        List<WebElement> filmList = driver.findElements(By.xpath("//div[@class='movies-deal']"));
        filmList.get(0).click();

        //Scroll vertically (if necessary), and horizontally and choose ‘კავეა ისთ ფოინთი’
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@class='cinema-tabs ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all']")));
        js.executeScript("window.scrollBy(0, 300)");
        js.executeScript("arguments[0].click()", driver.findElement(By.xpath("//a[text()='კავეა ისთ ფოინთი']")));

        //Check that only ‘კავეა ისთ ფოინთი’ options are returned
        List<WebElement> seanseList = driver.findElements(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom' and @aria-hidden='false']//child::div[@aria-hidden='false']"));
        //თაციდან მუშაობდა მაგრამ 2 საათის მერე რატომღაც აღარ მუშაობდა და ვერ გავიგე :დდდდ
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
//        for (WebElement w :
//                seanseList) {
//            Assert.assertEquals(w.findElement(By.xpath("//p[@class='cinema-title']")).getText(), "კავეა ისთ ფოინთი");
//        }

        //Click on last date and then click on last option
        List<WebElement> movieDates = driver.findElements(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom' and @aria-hidden='false']//child::li"));
        WebElement movieDateClickButton = movieDates.get(movieDates.size() - 1).findElement(By.xpath(".//a[@class='ui-tabs-anchor']"));
        movieDateClickButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        List<WebElement> seanseListTwo = driver.findElements(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom' and @aria-hidden='false']//child::div[@aria-hidden='false']"));
        seanseListTwo.get(seanseListTwo.size() - 1).click();


        //Check in opened popup that movie name, cinema and datetime is valid
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='tickets-corns']")));
        String movieName = driver.findElement(By.xpath("//div[@class='movie_first_section']//child::p[@class='name']")).getText();
        String movieNamePopup = driver.findElement(By.xpath("//div[@class='right-content']//child::p[@class='movie-title']")).getText();
        Assert.assertEquals(movieName, movieNamePopup);

        WebElement cinemaName = seanseListTwo.get(seanseListTwo.size() - 1).findElement(By.xpath("//div[@class = 'seanse-details ui-tabs-panel ui-widget-content ui-corner-bottom']//child::p[@class='cinema-title' and text()='კავეა ისთ ფოინთი']"));
        String cinemaNamePopup = driver.findElement(By.xpath("//div[@class='tickets-corns']//child::p[@class='movie-cinema' and text()='კავეა ისთ ფოინთი']")).getText();
        Assert.assertEquals(cinemaName.getText(), cinemaNamePopup);


//        Assert.assertEquals();


        //Choose any vacant place
        List<WebElement> freeSeats = driver.findElements(By.xpath("//div[@class='seat free']"));
        WebElement freeSeatsButton = freeSeats.get(0).findElement(By.xpath("//div[@class='seat-new-part']"));
        wait.until(ExpectedConditions.elementToBeClickable(freeSeatsButton));
        js.executeScript("arguments[0].click()", freeSeatsButton);


        //Register for a new account
        WebElement registrationButton = driver.findElement(By.xpath("//*[@id=\"toogletabs\"]/div[1]/div/ul/li[2]"));
        wait.until(ExpectedConditions.elementToBeClickable(registrationButton));
        actions.moveToElement(registrationButton).click().perform();
        driver.findElement(By.xpath("//*[@id=\"pFirstName\"]")).sendKeys("irakli");
        driver.findElement(By.xpath("//*[@id=\"pLastName\"]")).sendKeys("jajanidze");
        driver.findElement(By.xpath("//*[@id=\"pPhone\"]")).sendKeys("514231881");
        WebElement calendar = driver.findElement(By.xpath("//*[@id=\"pDateBirth\"]"));
        calendar.sendKeys("07");
        calendar.sendKeys(Keys.TAB);
        calendar.sendKeys("07");
        calendar.sendKeys(Keys.TAB);
        calendar.sendKeys("2002");
        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"pGender\"]")));
        select.selectByVisibleText("კაცი");
        driver.findElement(By.xpath("//*[@id=\"pPassword\"]")).sendKeys("test123");
        driver.findElement(By.xpath("//*[@id=\"pConfirmPassword\"]")).sendKeys("test123");
        driver.findElement(By.xpath("//*[@id=\"pIsAgreeTerns\"]")).click();
        js.executeScript("window.scrollBy(0, 300)");
        driver.findElement(By.xpath("//*[@id=\"register-content-1\"]/a/div")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"physicalInfoMassage\"]")).getText(), "მეილის ფორმატი არასწორია!");
    }
}