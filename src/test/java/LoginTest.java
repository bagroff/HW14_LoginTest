import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {
    static WebDriver chromeDriver;

    public void userNameLogin(String login) {
        WebElement userNameField = chromeDriver.findElement(By.xpath("//*[@id=\"username\"]"));
        userNameField.clear();
        userNameField.sendKeys(login);
    }

    public void userPasswordLogin(String password) {
        WebElement userPasswordField = chromeDriver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[2]/div/input"));
        userPasswordField.clear();
        userPasswordField.sendKeys(password);
    }

    public void loginButtonClick() {
        WebElement loginButton = chromeDriver.findElement(By.xpath("/html/body/div[2]/div/div/form/button"));
        loginButton.click();
    }

    @BeforeAll
    public static void init() {
        chromeDriver = new ChromeDriver();
    }

    @BeforeEach
    void setMaxWindowSize() {
        chromeDriver.manage().window().maximize();
        chromeDriver.get("https://the-internet.herokuapp.com/login");
    }
    // Success login test
    @Test
    public void loginVerify() throws InterruptedException {
        userNameLogin("tomsmith");
        userPasswordLogin("SuperSecretPassword!");
        loginButtonClick();
        WebElement successLogin = chromeDriver.findElement(By.xpath("//div[contains(text(), 'You logged into a secure area!')]"));
        Assertions.assertTrue(successLogin.isDisplayed());
        Thread.sleep(3000);
    }

    // Unsuccess login test
    @Test
    public void loginVerifyError() throws InterruptedException {
        userNameLogin("tralala");
        userPasswordLogin("SuperSecretPassword!");
        loginButtonClick();
        WebElement unSuccessLogin = chromeDriver.findElement(By.xpath("//div[contains(text(), 'Your username is invalid!')]"));
        Assertions.assertTrue(unSuccessLogin.isDisplayed());
        Thread.sleep(3000);
    }

    @AfterAll
    public static void tearDown() {
        chromeDriver.quit();
    }
}