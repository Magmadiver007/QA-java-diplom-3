package site.nomoreparties.stellarburgers.pageobjects;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {
    private static final String REGISTRATION_PAGE_URL = "https://stellarburgers.nomoreparties.site/register";

    private static final By NAME_FIELD = By.xpath("//label[text()='Имя']/following-sibling::input");
    private static final By EMAIL_FIELD = By.xpath("//label[text()='Email']/following-sibling::input");
    private static final By PASSWORD_FIELD = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private static final By REGISTRATION_BUTTON = By.xpath("//button[text()='Зарегистрироваться']");
    private static final By PAGE_TEXT = By.xpath("//div[@class='Auth_login__3hAey']/h2[text()='Регистрация']");
    private static final By PASSWORD_ERROR_TEXT = By.xpath("//label[text()='Пароль']/parent::div/following-sibling::p");
    private static final By LOGIN_BUTTON = By.xpath("//a[text()='Войти']");

    private final WebDriver driver;
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Открывем страницу регистрации")
    public void open() {
        driver.get(REGISTRATION_PAGE_URL);
    }
    @Step("Нажимем кнопку регистрации")
    public void clickRegistryButton() {
        driver.findElement(REGISTRATION_BUTTON).click();

    }
    @Step("Вводим имя")
    public void enterNameField (String Text){
        driver.findElement(NAME_FIELD).sendKeys(Text);
    }
    @Step("Вводим EMAIL")
    public void enterEmailField (String Text){
        driver.findElement(EMAIL_FIELD).sendKeys(Text);
    }
    @Step("Водим пароль")
    public void enterPasswordField (String Text){
        driver.findElement(PASSWORD_FIELD).sendKeys(Text);
    }
    @Step("Проверяем страницу")
    public String getHeaderText(){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(PAGE_TEXT));
        return driver.findElement(PAGE_TEXT).getText();
    }
    @Step("Проверяем текст ошибки")
    public String getErrorText(){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.presenceOfElementLocated(PASSWORD_ERROR_TEXT));
        return driver.findElement(PASSWORD_ERROR_TEXT).getText();
    }
    @Step("Нажимем кнопку Войти")
    public void loginButtonClick (){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
        driver.findElement(LOGIN_BUTTON).click();
    }
}
