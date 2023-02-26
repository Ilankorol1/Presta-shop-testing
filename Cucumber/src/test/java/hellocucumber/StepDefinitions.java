package hellocucumber;

import io.cucumber.java.en.*;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.CoreMatchers.containsString;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class StepDefinitions {
    private WebDriver driver1;
    private WebDriver driver2;
    private String projectPath = System.getProperty("user.dir");
    private String webDriver = "webdriver.chrome.driver";
    private String prod_name;
    private double newPrice;


    //the customer is logging in
    @Given("^the customer is logged in$")
    public void the_customer_is_logged_in() {
        System.setProperty(webDriver, "C:\\Users\\Kalanit\\IdeaProjects\\sqe-hw3\\Selenium\\chromedriver.exe");
        driver1 = new ChromeDriver();
        driver1.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver1.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver1.manage().window().maximize();
        //navigate to the url of the shop
        driver1.navigate().to("http://localhost:8888");
        //log in as customer
        driver1.findElement(By.xpath("//a[@title='Log in to your customer account']")).click();
        driver1.findElement(By.id("field-email")).sendKeys("harry@potter.com");
        driver1.findElement(By.id("field-password")).sendKeys("H@rrP1!ot");
        driver1.findElement(By.id("submit-login")).click();
    }

    //the customer is in the home page
    @And("^the customer is in the home page$")
    public void the_customer_is_in_the_home_page() {
    }

    //the customer clicks on a product that he wants to buy, and enters its page
    @And("^the customer navigates to the page of the product he wants to buy$")
    public void the_customer_navigates_to_prod() {
        //navigate to the page of the product
        driver1.findElement(By.xpath("//img[contains(@alt,'The adventure begins Framed poster')]")).click();
    }

    //the customer adds the product to his cart
    //we save the product name in order to compare it later to the product in the cart
    @When("^the customer click on 'ADD TO CART' button$")
    public void the_customer_clicks_add_to_cart() {
        //add product to cart
        driver1.findElement(By.xpath("//*[@class='btn btn-primary add-to-cart']")).click();
        //save the product name
        prod_name = driver1.findElement(By.xpath("//*[@class='h6 product-name']")).getAttribute("innerText");
    }

    //the message was displayed properly
    @Then("^message displayed 'Product successfully added to your shopping cart'$")
    public void product_added_successfully_message() {
        //check that the message displayed correctly
        String added = driver1.findElement(By.xpath("//*[@class='modal-title h6 text-sm-center']")).getAttribute("innerText");
        MatcherAssert.assertThat(added, containsString("Product successfully added to your shopping cart"));
    }

    //enter customer cart
    //and compare the name of the product in the cart and the product that was added to the cart
    @And("^the product should be added to the cart$")
    public void product_added_to_cart() {
        //navigate to cart
        driver1.findElement(By.xpath("//*[@class='btn btn-secondary']")).click();
        driver1.findElement(By.xpath("//*[@class='blockcart cart-preview active']")).click();
        //check that the product added to the cart properly
        String product_in_cart = driver1.findElement(By.xpath("//*[@class='product-line-info']")).getAttribute("innerText");
        assertEquals(prod_name.toLowerCase(), product_in_cart.toLowerCase());
        // close the driver window
        driver1.close();
    }


    //the seller is logging in
    @Given("^the seller is logged in$")
    public void the_seller_is_logged_in() {
        System.setProperty(webDriver, "C:\\Users\\Kalanit\\IdeaProjects\\sqe-hw3\\Selenium\\chromedriver.exe");
        driver2 = new ChromeDriver();
        driver2.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver2.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver2.manage().window().maximize();
        //navigate to the url of the shop management
        driver2.navigate().to("http://localhost:8888/admin2");
        //log in as admin
        driver2.findElement(By.id("email")).sendKeys("some@admin.com");
        driver2.findElement(By.id("passwd")).sendKeys("greatP@ssw0rd!");
        driver2.findElement(By.id("submit_login")).click();
    }

    //the seller is on the dashboard page
    @And("^the seller is on the dashboard page$")
    public void the_seller_is_on_the_dashboard_page() {
    }

    //the seller navigates to the product editing page of the product he wants to change its price
    @And("^the seller navigates to the product editing page of the product he wants to change its price$")
    public void the_seller_navigates_to_the_product_editing_page_of_the_product_he_wants_to_change_its_price() {
        //navigate to the editing page of the product
        driver2.findElement(By.xpath("//*[@class='material-icons mi-store']")).click();
        driver2.findElement(By.xpath("//*[@id='subtab-AdminProducts']")).click();
        driver2.findElement(By.linkText("The adventure begins Framed poster")).click();
    }

    //the seller changes the price of the product
    @When("^the seller changes the price of the product$")
    public void the_seller_changes_the_price_of_the_product() {
        //pick new random price between 20-55 exclude the old price
        double oldPrice = Double.parseDouble(driver2.findElement(By.xpath("//*[@id='form_step1_price_shortcut']")).getAttribute("value"));
        var rand = new Random();
        newPrice = rand.nextInt(36)+20;
        while (newPrice == oldPrice)
        {
            newPrice = rand.nextInt(36)+20;
        }
        //clear the old price and insert the new one
        driver2.findElement(By.xpath("//*[@id='form_step1_price_shortcut']")).sendKeys(Keys.chord(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Double.toString(newPrice)));
    }

    //save the new price
    @Then("^click on 'Save' button$")
    public void save_product_price() {
        //save the new product price
        driver2.findElement(By.xpath("//button[@class='btn btn-primary js-btn-save ml-3']")).click();
    }

    //check if the price of the product in the products dashboard was changed correctly
    @And("^the price of the product should be changed$")
    public void product_changed_price() {
        //navigate to products dashboard
        driver2.findElement(By.xpath("//*[@class='material-icons mi-store']")).click();
        driver2.findElement(By.xpath("//*[@id='subtab-AdminProducts']")).click();
        //check that the price was changed correctly
        String product_price = driver2.findElement(By.xpath("//*[@id=\"product_catalog_list\"]/div/table/tbody/tr[16]/td[7]/a")).getAttribute("innerText");
        assertEquals("$" + String.format("%.2f",newPrice), product_price);
        // close the driver window
        driver2.close();
    }
}