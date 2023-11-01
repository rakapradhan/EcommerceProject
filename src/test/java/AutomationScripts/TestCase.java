package AutomationScripts;

import context.TestContext;
import dataProvider.ConfigFileReader;
import dataProvider.ReadWriteExcel;
import extentReport.ExtentReport;
import objectManager.DriverManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pageObjects.*;
import utils.Logging;
import utils.Utility;

import java.io.IOException;

public class TestCase {
    WebDriver driver;
    ExtentReport extentReport;
    TestContext testContext;
    ReadWriteExcel readWriteExcel;
    LoginPage loginPage;
    RelevelHomePage relevelHomePage;
    SoftAssert softAssert;
    @BeforeSuite
    public void beforeSuiteSetup() throws IOException {
        driver= DriverManager.getDriver();
        driver.get(ConfigFileReader.getUrl());
        extentReport=new ExtentReport();
        testContext= new TestContext();
        readWriteExcel= new ReadWriteExcel();
        loginPage=new LoginPage(driver);
        relevelHomePage=new RelevelHomePage(driver);
        softAssert=new SoftAssert();
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
    }

    @AfterSuite
    public void afterSuiteTearDown()
    {
        softAssert.assertAll();
        extentReport.flush();
    }
    @BeforeMethod
    public void startTest()
    {
        Logging.info("Starting the test case Execution ");
    }
    @AfterMethod
    public void closeTest()
    {
        Logging.info("Ending the Test Case Execution");
        if((driver.findElements(By.xpath("//div[text()=\"Logout\"]")).size())>0)
        {
            loginPage.logoutBtn.click();
            Logging.info("clicked on Logout Button");
        }
        else
        {
            Logging.info("Logout button is not displayed");
        }
    }
    @Test(description = "TC01-verify the login flow with blank username and password",priority = 2)
            public void loginTestWithBlankCredentials() throws IOException {
            extentReport.createTest("TC01-verify the login flow with blank username and password");
            relevelHomePage.loginLink.click();
            extentReport.info("User clicked on login link ");
            Logging.info("User clicked on login link");
            loginPage.loginBtn.click();
            extentReport.info("clicked on login button present in login page");
            Logging.info("clicked on login button present in login page");
            if ((driver.findElements(By.xpath("//div[text()=\"Username and Password are required!!\"]"))).size()>0)
    {
        String errorText= loginPage.errorMessage.getText();
        extentReport.info("Error message :Username and Password are required!! is displayed ");
        Logging.info("Error message :Username and Password are required!! is displayed");
        softAssert.assertEquals(errorText,"Username and Password are required!!");
        extentReport.pass("Error message is displayed when user login without username and password");
        extentReport.addScreenshot(driver);
        Logging.info("Error message is displayed when user login without username and password");
        Logging.endTestCase();
    }
else
    {
        extentReport.fail("Username and Password are required!! is not displayed");
        extentReport.addScreenshot(driver);
        Logging.error("Username and Password are required!! is not displayed");
    }
}
@Test(description ="TC-02: validate the login functionality with valid username and password",priority = 1)
        public void loginTestWithValidCredentials() throws IOException {

        extentReport.createTest("TC-02: validate the login functionality with valid username and password");
                relevelHomePage.loginLink.click();
        extentReport.info("user clicked on login link");
        Logging.info("user clicked on login link");
        loginPage.userNameField.sendKeys(ConfigFileReader.getUserName());
        extentReport.info("user entered the username");
        Logging.info("user entered the username");
        loginPage.passwordField.sendKeys(ConfigFileReader.getPassword());
        extentReport.info("user entered the password");
        Logging.info("user entered the password");
        loginPage.loginBtn.click();
        ConfigFileReader.getImplicitWait();
        ConfigFileReader.getPageLoadTimeOut();
        extentReport.info("waiting for page to load completely");
        Logging.info("waiting for page to load completely");
        extentReport.info("user clicked on login button");
        Logging.info("user clicked on login button ");
        if(loginPage.logoutBtn.isDisplayed())
        {
        extentReport.info("Logout button is displayed");
        Logging.info("Logout button is displayed");
        extentReport.pass("user loggedin successfully");
        Logging.info("user loggedin successfully");
        extentReport.addScreenshot(driver);
        Logging.endTestCase();
        }
        else
        {
                extentReport.fail("logout button is not displayed , user is not loggedin");
        Logging.error("logout button is not displayed , user is not loggedin");
        extentReport.addScreenshot(driver);
        Logging.endTestCase();
        }
        }

    @Test(description = "validate the login functionality with valid data that is taken form excel sheet",priority = 3)
            public void LoginTestwithExcelData() throws IOException {
            XSSFSheet sheet = readWriteExcel.getXssfSheet("Sheet2");
            for (Integer i = 1; i <= sheet.getLastRowNum(); i++) {
        extentReport.createTest("TC03-validate the login functionality with valid data that is taken form excel sheet");
        relevelHomePage.loginLink.click();
        extentReport.info("clicked on login link");
        Logging.info("clicked on login link");
        loginPage.userNameField.sendKeys(sheet.getRow(i).getCell(0).getStringCellValue(
        ));
        extentReport.info("user entered the username");
        Logging.info("user entered the username");
        loginPage.passwordField.sendKeys(sheet.getRow(i).getCell(1).getStringCellValue(
        ));
        extentReport.info("user entered the password");
        Logging.info("user entered the password");
        loginPage.loginBtn.click();
        extentReport.info("user clicked on login button");
        Logging.info("user clicked on login button");
        ConfigFileReader.getImplicitWait();
        ConfigFileReader.getPageLoadTimeOut();
        if (loginPage.logoutBtn.isDisplayed()) {
            extentReport.pass("user loggedin Successfully");
            Logging.info("user loggedin Successfully ");
            extentReport.addScreenshot(driver);
        } else {
            extentReport.fail("user is not loggedin ");

            Logging.info("user is not loggedin");
            extentReport.addScreenshot(driver);
        }
    }
}
@Test(description = "search the product category wise and compare the prices",priority = 4)
        public void searchProductTest() throws IOException {
        double actualPrice, expectedPrice;
        XSSFSheet sheet = readWriteExcel.getXssfSheet("Sheet1");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        extentReport.createTest("Tc-04 :search the product category wise and compare the prices");
        switch (sheet.getRow(i).getCell(0).getStringCellValue()) {
        case "Electronics":
        relevelHomePage.electronicLink.click();
        extentReport.info("clicked on Electronics category");
        Logging.info("clicked on Electronics category");
        ConfigFileReader.getPageLoadTimeOut();
        ElectronicsPage.searchBar.sendKeys(sheet.getRow(i).getCell(1).getStringCellValue());
        extentReport.info("searched the electronic product");
        Logging.info("searched the electronic product");
        String actualProductName =
        ElectronicsPage.s21Product.getText();
        String expectedProductName = sheet.getRow(i).getCell(1).getStringCellValue();
        actualPrice = Double.parseDouble(ElectronicsPage.s21Price.getText());
        expectedPrice = Double.parseDouble(sheet.getRow(i).getCell(2).getStringCellValue());
        if (actualProductName.equals(expectedProductName)) {
        softAssert.assertEquals(expectedPrice, actualPrice, "prices are not equal");
        if (actualPrice == expectedPrice) {
        extentReport.info("compared the prices , it is equal");
        Logging.info("compared the prices , it is equal");
        extentReport.pass("able to search electronic product");
        extentReport.addScreenshot(driver);
        Logging.info("able to search electronic product");
        }
        else {
        extentReport.info("compared the prices , it is not equal" + actualPrice);
        System.out.println("actual and expected price:" +
        actualPrice + expectedPrice);
        Logging.info("compared the prices , it is not equal");
        extentReport.fail("not able to search required electronic product");
        extentReport.addScreenshot(driver);
        Logging.info("not able to search required electronic product");
        }
        }
        break;
        case "Kitchen Items":
        relevelHomePage.kitchenItems.click();

        extentReport.info("clicked on kitchen Items Link");
        Logging.info("clicked on kitchen Items Link");
        ConfigFileReader.getPageLoadTimeOut();
        KitchenPage.searchBar.sendKeys(sheet.getRow(i).getCell(1).getStringCellValue());
        KitchenPage.searchBar.click();
        extentReport.info("searched the required product ");
        Logging.info("searched the required product ");
        String actualProduct = KitchenPage.psProduct.getText();
        String expectedProduct =
        sheet.getRow(i).getCell(1).getStringCellValue();
        Double actualPrice1 =
        Double.parseDouble(KitchenPage.psPrice.getText());
        Double expectedPrice1 =
        Double.parseDouble(sheet.getRow(i).getCell(2).getStringCellValue());
        if (actualProduct.equals(expectedProduct)) {
        extentReport.info("product name is similar");
        Logging.info("product name is similar");
        softAssert.assertEquals(actualPrice1, expectedPrice1,
        "prices not equal for kitchen product");
        if (actualPrice1.equals(expectedPrice1)) {
        extentReport.info("actual price and expected price are same" + actualPrice1 + expectedPrice1);
        Logging.info("actual price and expected price are same");
        extentReport.pass("able to search kitchen items ");
        extentReport.addScreenshot(driver);
        } else {
        extentReport.info("compared the prices , that is not equal" + actualPrice1 + expectedPrice1);
        Logging.info("compared the prices , it is not equal");
        extentReport.fail("unable to search the kitchen items ");
        extentReport.addScreenshot(driver);
        Logging.error("unable to search the kitchen items");
        }
        } else {
        extentReport.info("product information are not same");
        Logging.info("product details are not same ");
        extentReport.fail("details are not matched for expected kitchen item ");
        Logging.error("details are not matched for expected kitchen item ");
        }
        break;
        case "Sports":
        relevelHomePage.sports.click();
        extentReport.info("clicked on sports link");
        Logging.info("clicked on sports link");
        ConfigFileReader.getPageLoadTimeOut();
        SportsPage.searchBar.sendKeys(sheet.getRow(i).getCell(1).getStringCellValue());
        SportsPage.searchBar.click();
        extentReport.info("searched the sport product");
        Logging.info("searched the sport product");

        String actualSportProduct = SportsPage.sgbatProduct.getText();
        String expectedSportProduct = sheet.getRow(i).getCell(1).getStringCellValue();
        Double actualProductPrice = Double.parseDouble(SportsPage.sgbatPrice.getText());
        Double expectedProductPrice = Double.parseDouble(sheet.getRow(i).getCell(2).getStringCellValue());
        if (actualSportProduct.equals(expectedSportProduct)) {
        extentReport.info("actual and expected product name are same ");
        Logging.info("actual and expected product name are same ");
        softAssert.assertEquals(actualProductPrice,
        expectedProductPrice, "product prices are not same");
        if (actualProductPrice.equals(expectedProductPrice)) {
        extentReport.info("compared the prices , it's equal");
        Logging.info("compared the prices , it's equal");
        extentReport.pass("able to search the sports product");
        Logging.info("able to search the sports product");
        extentReport.addScreenshot(driver);
        }
        } else {
        extentReport.info("compared the prices , it's not equal" + actualProductPrice + expectedProductPrice);
        Logging.info("compared the prices , it's not equal");
        extentReport.fail("not able to search the sports product");
        Logging.info("not able to search the sports product");
        extentReport.addScreenshot(driver);
        }
        break;
default:
        extentReport.info("No matching category is found");
        Logging.info("No matching category is found");
        extentReport.fail("matching category is not found , unable to search the product");
        Logging.info("matching category is not found , unable to search the product");
        extentReport.addScreenshot(driver);
        }
        driver.get(ConfigFileReader.getUrl());
        }
        Logging.endTestCase();
        }
@Test(description = "Add item into cart and verify the details",priority =
        5)
public void addITemCartTest() throws IOException {
        extentReport.createTest("TC05:Add item into cart and verify the details");
        extentReport.info("user is giving details to login in to site ");
        Logging.info("user is giving details to login in to site");
        Utility.login(driver);
        extentReport.info("user is loggedin ");
        Logging.info("user is loggedin ");
        extentReport.info("Clicking on Kitchen category");
        Logging.info("Clicking on Kitchen category");
        relevelHomePage.kitchenItems.click();
        KitchenPage.psProduct.click();
        extentReport.info("clicked on prestige stove");
        Logging.info("clicked on prestige stove");
        ConfigFileReader.getPageLoadTimeOut();
        String productNameOnDetailPage = ProductDetailPage.psName.getText();
        String productPriceOnDetailPage = ProductDetailPage.psPrice.getText();
        ProductDetailPage.addToCartBtn.click();
        extentReport.info("clicked on add to cart button ");
        Logging.info("clicked on add to cart button");
        ConfigFileReader.getPageLoadTimeOut();
        ProductDetailPage.goToCartBtn.click();
        extentReport.info("clicked on go to cart button");
        Logging.info("clicked on go to cart button");
        ConfigFileReader.getPageLoadTimeOut();
        String productNameOnCartPage = CartPage.psNameOnCart.getText();
        String productPriceOnCartPage = CartPage.psNameOnCart.getText();
        if (productNameOnCartPage.equals(productNameOnDetailPage)) {
        extentReport.info("product name are same on product detail Page and cart Page");
        Logging.info("product name are same on product detail Page and cart Page");
        if (productPriceOnCartPage.equals(productPriceOnDetailPage)) {
        extentReport.info("product prices are same on product detail page and cart page "+productPriceOnCartPage+productPriceOnDetailPage);
        Logging.info("product prices are same on product detail page and cart page");
        extentReport.pass("product details are similar on product detail page and cart page ");
        Logging.info("product details are similar on product detail page and cart page ");
        extentReport.addScreenshot(driver);
        Logging.endTestCase();
        }
        else
        {
            extentReport.info("product prices are not same on product detail page and cart page "+productPriceOnCartPage +productPriceOnDetailPage);
            Logging.error("product prices are not same on product detail page and cart page");
            extentReport.fail("product price details are not similar on product detail page and cart page ");
            Logging.error("product price details are not similar on product detail page and cart page ");
            extentReport.addScreenshot(driver);
            Logging.endTestCase();
        }
        }
        else {
            extentReport.info("product name are not same on product detail page and cart page");
            Logging.error("product name are not same on product detail page and cart page");
            extentReport.fail("product name details are not similar on product detail page and cart page");
            Logging.error("product name details are not similar on product detail page and cart page");
            extentReport.addScreenshot(driver);
            Logging.endTestCase();
        }
}
}
