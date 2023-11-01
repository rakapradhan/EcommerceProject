package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
public class ProductDetailPage {
    public static WebDriver driver;
    public ProductDetailPage(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath="//div[text()=\"Prestige Stove\"]")
    public static WebElement psName;
    @FindBy(xpath="//div[text()=\"14500\"]")
    public static WebElement psPrice;
    @FindBy(xpath="//div[text()=\"Add To Cart\"]")
    public static WebElement addToCartBtn;
    @FindBy(xpath="//a[text()=\"Go To Cart\"]")
    public static WebElement goToCartBtn;
    @FindBy(xpath="//div[text()=\"SG Bat\"]")
    public WebElement sgbat;
    @FindBy(xpath="//div[text()=\"25500\"]")
    public WebElement sgbatPrice;
    @FindBy(xpath="//div[text()=\"s21\"]")
    public WebElement s21;
    @FindBy(xpath="//div[text()=\"65000\"]")
    public WebElement s21Price;
}
