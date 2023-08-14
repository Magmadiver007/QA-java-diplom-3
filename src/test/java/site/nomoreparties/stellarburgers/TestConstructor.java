package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import site.nomoreparties.stellarburgers.SupportData.BrowserType;
import site.nomoreparties.stellarburgers.pageobjects.MainPage;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class TestConstructor {
    private BrowserType browserType;
    private WebDriver driver;
    private MainPage mainPage;
    public TestConstructor(BrowserType browserType){
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
        mainPage.open();
        mainPage.waitForPageLoadedUnauthorized();
    }

    @Test
    @DisplayName("Тест перехода на вкладку соусов")
    @Description("Тест перехода на вкладку соусов")
    public void testConstructorSauces() {
        mainPage.saucesTabClick();
        Assert.assertThat(mainPage.getBunClassName(), not(CoreMatchers.containsString("tab_tab_type_current")));
        Assert.assertThat(mainPage.getSauceClassName(), CoreMatchers.containsString("tab_tab_type_current"));
        Assert.assertThat(mainPage.getMainsClassName(), not(CoreMatchers.containsString("tab_tab_type_current")));
    }
    @Test
    @DisplayName("Тест перехода на вкладку начинок")
    @Description("Тест перехода на вкладку начинок")
    public void testConstructorMains() {
        mainPage.mainsTabClick();
        Assert.assertThat(mainPage.getBunClassName(), not(CoreMatchers.containsString("tab_tab_type_current")));
        Assert.assertThat(mainPage.getSauceClassName(), not(CoreMatchers.containsString("tab_tab_type_current")));
        Assert.assertThat(mainPage.getMainsClassName(), CoreMatchers.containsString("tab_tab_type_current"));
    }
    @Test
    @DisplayName("Тест перехода на вкладку булок")
    @Description("Тест перехода на вкладку булок")
    public void testConstructorBuns() {
        mainPage.saucesTabClick();
        mainPage.bunsTabClick();
        Assert.assertThat(mainPage.getBunClassName(), CoreMatchers.containsString("tab_tab_type_current"));
        Assert.assertThat(mainPage.getSauceClassName(), not(CoreMatchers.containsString("tab_tab_type_current")));
        Assert.assertThat(mainPage.getMainsClassName(), not(CoreMatchers.containsString("tab_tab_type_current")));
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
