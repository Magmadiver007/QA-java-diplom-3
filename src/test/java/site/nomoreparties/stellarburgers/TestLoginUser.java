package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import site.nomoreparties.stellarburgers.SupportData.BrowserType;
import site.nomoreparties.stellarburgers.pageobjects.LoginPage;
import site.nomoreparties.stellarburgers.pageobjects.MainPage;
import site.nomoreparties.stellarburgers.pageobjects.PasswordRecoveryPage;
import site.nomoreparties.stellarburgers.pageobjects.RegisterPage;
import site.nomoreparties.stellarburgers.restclients.User;
import site.nomoreparties.stellarburgers.restclients.UserClient;
import site.nomoreparties.stellarburgers.restclients.UserGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
@RunWith(Parameterized.class)
public class TestLoginUser {
    private WebDriver driver;
    private static UserClient userClient;
    private BrowserType browserType;
    private User user;
    private String accessToken;
    private MainPage mainPage;
    private LoginPage loginPage;

    public TestLoginUser(BrowserType browserType){
        this.browserType = browserType;
    }

    @Parameterized.Parameters
    public static Object[][] getBrowserVariants() {
        return new Object[][] {
                {BrowserType.CHROME},
                {BrowserType.YANDEX},
        };
    }
    @Before
    public void setUp() {
        user = UserGenerator.getRandom();
        userClient = new UserClient();
        ValidatableResponse createResponse = userClient.createUser(user);
        accessToken = createResponse.extract().path("accessToken");
        ChromeOptions options = new ChromeOptions();
        switch (browserType) {
            case CHROME:
                System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
                break;
            case YANDEX:
                System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\yandexdriver.exe");
                break;
            default:
                fail("Неожиданный браузер");
                break;
        }
        options.addArguments("--whitelisted-ips=''");
        driver = new ChromeDriver(options);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);

    }

    @Test
    @DisplayName("Тест логина через кнопку войти в аккаунт")
    @Description("Тест логина через кнопку войти в аккаунт")
    public void testLoginFromMain(){
        mainPage.open();
        mainPage.loginButtonClick();
        assertEquals("Вход",loginPage.getHeaderText());
        loginPage.enterEmailField(user.getEmail());
        loginPage.enterPasswordField(user.getPassword());
        loginPage.clickLoginButton();
        assertEquals(true,mainPage.isCreateOrderButton());
    }
    @Test
    @DisplayName("Тест логина через скнопку Личный кабинет")
    @Description("Тест логина через скнопку Личный кабинет")
    public void testLoginFromCabinetButton(){
        mainPage.open();
        mainPage.cabinetButtonClick();
        assertEquals("Вход",loginPage.getHeaderText());
        loginPage.enterEmailField(user.getEmail());
        loginPage.enterPasswordField(user.getPassword());
        loginPage.clickLoginButton();
        assertEquals(true,mainPage.isCreateOrderButton());
    }
    @Test
    @DisplayName("Тест логина через страницу регистрации")
    @Description("Тест логина через страницу регистрации")
    public void testLoginFromRegistrationPage(){
        RegisterPage regPage = new RegisterPage(driver);
        regPage.open();
        regPage.loginButtonClick();
        assertEquals("Вход",loginPage.getHeaderText());
        loginPage.enterEmailField(user.getEmail());
        loginPage.enterPasswordField(user.getPassword());
        loginPage.clickLoginButton();
        assertEquals(true,mainPage.isCreateOrderButton());

    }
    @Test
    @DisplayName("Тест логина через страницу восстановления пароля")
    @Description("Тест логина через страницу восстановления пароля")
    public void testLoginFromRecoveryPage(){
        PasswordRecoveryPage pwdRecPage = new PasswordRecoveryPage(driver);
        pwdRecPage.open();
        pwdRecPage.clickLoginButton();
        assertEquals("Вход",loginPage.getHeaderText());
        loginPage.enterEmailField(user.getEmail());
        loginPage.enterPasswordField(user.getPassword());
        loginPage.clickLoginButton();
        assertEquals(true,mainPage.isCreateOrderButton());

    }

    @After
    public void teardown() {
        userClient.deleteUser(accessToken, user);
        driver.quit();
    }
}
