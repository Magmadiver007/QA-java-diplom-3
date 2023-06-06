package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import site.nomoreparties.stellarburgers.SupportData.BrowserType;
import site.nomoreparties.stellarburgers.pageobjects.LoginPage;
import site.nomoreparties.stellarburgers.pageobjects.RegisterPage;
import site.nomoreparties.stellarburgers.restclients.User;
import site.nomoreparties.stellarburgers.restclients.UserClient;
import site.nomoreparties.stellarburgers.restclients.UserGenerator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class TestRegistryUser {
    private static UserClient userClient;
    private WebDriver driver;
    private User user;
    private User failUser;
    private String accessToken;
    private BrowserType browserType;
    private boolean isSuccess;
    public TestRegistryUser(BrowserType browserType){
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
        failUser = UserGenerator.getWrongRandom();

        userClient = new UserClient();
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

    }
    @Test
    @DisplayName("Тест успещной регистрации пользователя")
    @Description("Тест успещной регистрации пользователя")
    public void testRegistrationSuccess() {
        RegisterPage regPage = new RegisterPage(driver);
        regPage.open();
        assertEquals("Регистрация",regPage.getHeaderText());
        regPage.enterEmailField(user.getEmail());
        regPage.enterNameField(user.getName());
        regPage.enterPasswordField(user.getPassword());
        regPage.clickRegistryButton();
        LoginPage loginPage = new LoginPage(driver);
        assertEquals("Вход",loginPage.getHeaderText());

        ValidatableResponse createResponse = userClient.loginUser(UserGenerator.userForLogin(user.getEmail(), user.getPassword()));
        createResponse.assertThat().statusCode(equalTo(200));
        createResponse.assertThat().body("user.email",equalTo(user.getEmail().toLowerCase()));
        createResponse.assertThat().body("user.name",equalTo(user.getName()));
        createResponse.assertThat().body("accessToken", CoreMatchers.not(isEmptyOrNullString()));
        isSuccess = createResponse.extract().path("success");
        accessToken = createResponse.extract().path("accessToken");
    }
    @Test
    @DisplayName("Тест ошибки регистрации (пароль менее 6 символов)")
    @Description("Тест ошибки регистрации (пароль менее 6 символов)")
    public void testRegistrationFail() {
        RegisterPage regPage = new RegisterPage(driver);
        regPage.open();
        assertEquals("Регистрация",regPage.getHeaderText());
        regPage.enterEmailField(failUser.getEmail());
        regPage.enterNameField(failUser.getName());
        regPage.enterPasswordField(failUser.getPassword());
        regPage.clickRegistryButton();
        assertEquals("Некорректный пароль",regPage.getErrorText());

    }

    @After
    public void teardown() {
        if (isSuccess) {
            userClient.deleteUser(accessToken, user);
        }
        driver.quit();
    }
}
