package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import site.nomoreparties.stellarburgers.SupportData.BrowserType;
import site.nomoreparties.stellarburgers.pageobjects.LoginPage;
import site.nomoreparties.stellarburgers.pageobjects.MainPage;
import site.nomoreparties.stellarburgers.pageobjects.ProfilePage;
import site.nomoreparties.stellarburgers.restclients.User;
import site.nomoreparties.stellarburgers.restclients.UserClient;
import site.nomoreparties.stellarburgers.restclients.UserGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(Parameterized.class)
public class TestCabinet {
    private BrowserType browserType;
    private MainPage mainPage;
    private LoginPage loginPage;
    private WebDriver driver;
    private User user;
    private String accessToken;
    private static UserClient userClient;

    public TestCabinet(BrowserType browserType){
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
        mainPage.open();
        mainPage.cabinetButtonClick();
        assertEquals("Вход",loginPage.getHeaderText());
        loginPage.enterEmailField(user.getEmail());
        loginPage.enterPasswordField(user.getPassword());
        loginPage.clickLoginButton();

    }
    @Test
    @DisplayName("Тест входа в кабинет")
    @Description("Тест входа в кабинет")
    public void testEnterCabinet(){
        mainPage.cabinetButtonClick();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForPageLoaded();
        assertEquals(profilePage.getURL(),driver.getCurrentUrl());
        assertEquals(true,profilePage.isProfileButton());
        assertEquals(true,profilePage.isHistoryButton());
        assertEquals(true,profilePage.isPLogoutButton());
        assertEquals(user.getName(),profilePage.getName());
        assertEquals(user.getEmail().toLowerCase(),profilePage.getEmail());
    }
    @Test
    @DisplayName("Тест перехода в конструктор по нажатию на кнопку конструкотра")
    @Description("Тест перехода в конструктор по нажатию на кнопку конструкотра")
    public void testEnterConstructorFromConstructorButton(){
        mainPage.cabinetButtonClick();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForPageLoaded();
        profilePage.constructorButtonClick();
        mainPage.waitForPageLoadedAuthorized();
        assertEquals(mainPage.getURL(),driver.getCurrentUrl());
        assertEquals(true,mainPage.isCreateOrderButton());

    }
    @Test
    @DisplayName("Тест перехода в конструктор по нажатию на лого")
    @Description("Тест перехода в конструктор по нажатию на лого")
    public void testEnterConstructorFromStellarBurgersLogo(){
        mainPage.cabinetButtonClick();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForPageLoaded();
        profilePage.logoButtonClick();
        mainPage.waitForPageLoadedAuthorized();
        assertEquals(mainPage.getURL(),driver.getCurrentUrl());
        assertEquals(true,mainPage.isCreateOrderButton());
    }
    @Test
    @DisplayName("Тест выхода из аккаунта")
    @Description("Тест выхода из аккаунта")
    public void testLogout(){
        mainPage.cabinetButtonClick();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForPageLoaded();
        profilePage.logoutButtonClick();
        assertEquals("Вход",loginPage.getHeaderText());
        assertEquals(loginPage.getURL(),driver.getCurrentUrl());
    }

    @After
    public void teardown() {
        userClient.deleteUser(accessToken, user);
        driver.quit();
    }
}
