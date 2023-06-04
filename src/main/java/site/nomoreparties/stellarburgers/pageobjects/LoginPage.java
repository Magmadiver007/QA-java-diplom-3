package site.nomoreparties.stellarburgers.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";

    private static final By EMAIL_FIELD = By.xpath("//label[text()='Email']/following-sibling::input");
    private static final By PASSWORD_FIELD = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private static final By LOGIN_BUTTON = By.xpath("//button[text()='Войти']");
    private static final By PAGE_TEXT = By.xpath("//div[@class='Auth_login__3hAey']/h2[text()='Вход']");

    private final WebDriver driver;
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Открываем страницу")
    public void open() {
        driver.get(LOGIN_PAGE_URL);
    }
    @Step("Вводим EMAIL")
    public void enterEmailField (String Text){
        driver.findElement(EMAIL_FIELD).sendKeys(Text);
    }
    @Step("Вводим пароль")
    public void enterPasswordField (String Text){
        driver.findElement(PASSWORD_FIELD).sendKeys(Text);
    }
    @Step("Нажимаем кнопку войти")
    public void clickLoginButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
        driver.findElement(LOGIN_BUTTON).click();
    }
    @Step("Проверяем")
    public String getHeaderText(){
//

        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(PAGE_TEXT));
        return driver.findElement(PAGE_TEXT).getText();
    }
    @Step("Проверяем URL")
    public String getURL(){
        return LOGIN_PAGE_URL;
    }
}
