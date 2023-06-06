package site.nomoreparties.stellarburgers.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {
    private static final String PROFILE_PAGE_URL = "https://stellarburgers.nomoreparties.site/account/profile";
    private static final By PROFILE_BUTTON = By.xpath("//a[text()='Профиль']");
    private static final By HISTORY_BUTTON = By.xpath("//a[text()='История заказов']");
    private static final By LOGOUT_BUTTON = By.xpath("//button[text()='Выход']");
    private static final By SAVE_BUTTON = By.xpath("//button[text()='Сохранить']");

    private static final By NAME_FIELD = By.xpath("//label[text()='Имя']/following-sibling::input");
    private static final By EMAIL_FIELD = By.xpath("//label[text()='Логин']/following-sibling::input");
    private static final By PASSWORD_FIELD = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private static final By CONSTRUCTOR_BUTTON = By.xpath("//p[text()='Конструктор']/parent::a");
    private static final By BURGER_LOGO_BUTTON = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']/a");



    private final WebDriver driver;
    @Step("Проверяем URL")
    public String getURL(){
        return PROFILE_PAGE_URL;
    }
    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Открываем страницу оичного кабинета")
    public void open() {
        driver.get(PROFILE_PAGE_URL);
        new WebDriverWait(driver, 5).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
    @Step("Проверка наличия и активности кнопки Профилья")
    public boolean isProfileButton () {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(PROFILE_BUTTON));
        if (driver.findElement(PROFILE_BUTTON).isEnabled()){
            return true;
        } else {
            return false;
        }
    }
    @Step("Проверка наличия кнопки истории заказа")
    public boolean isHistoryButton () {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(HISTORY_BUTTON));
        if (driver.findElement(HISTORY_BUTTON).isEnabled()){
            return true;
        } else {
            return false;
        }
    }
    @Step("Проверка на наличие кнопки выхода")
    public boolean isPLogoutButton () {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON));
        if (driver.findElement(LOGOUT_BUTTON).isEnabled()){
            return true;
        } else {
            return false;
        }
    }
    @Step("Проверка имени")
    public String getName (){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(NAME_FIELD));
        return driver.findElement(NAME_FIELD).getAttribute("value");

    }
    @Step("Проверка EMAIL")
    public String getEmail (){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD));
        return driver.findElement(EMAIL_FIELD).getAttribute("value");
    }
    @Step("Ожидание загрузки страницы")
    public void waitForPageLoaded(){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON));
    }
    @Step("Нажимаем кнопку конструктора")
    public void constructorButtonClick (){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(CONSTRUCTOR_BUTTON));
        driver.findElement(CONSTRUCTOR_BUTTON).click();
    }
    @Step("Нажимаем на лого")
    public void logoButtonClick (){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(BURGER_LOGO_BUTTON));
        driver.findElement(BURGER_LOGO_BUTTON).click();
    }
    @Step("Нажимаем кнопку Выхода")
    public void logoutButtonClick (){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON));
        driver.findElement(LOGOUT_BUTTON).click();
    }

}
