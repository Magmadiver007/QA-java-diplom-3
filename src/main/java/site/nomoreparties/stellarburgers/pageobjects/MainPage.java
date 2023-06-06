package site.nomoreparties.stellarburgers.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class MainPage {
    private static final String MAIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final By LOGIN_BUTTON = By.xpath("//button[text()='Войти в аккаунт']");
    private static final By CREATE_ORDER_BUTTON = By.xpath("//button[text()='Оформить заказ']");
    private static final By CABINET_BUTTON = By.xpath("//a/p[text()='Личный Кабинет']");
    private static final By BUNS_TAB = By.xpath("//span[text()='Булки']/parent::div");
    private static final By SAUCES_TAB = By.xpath("//span[text()='Соусы']/parent::div");
    private static final By MAINS_TAB = By.xpath("//span[text()='Начинки']/parent::div");



    private final WebDriver driver;
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Проверяем URL")
    public String getURL(){
        return MAIN_PAGE_URL;
    }
    @Step("Открываем главную страницу/конструктор")
    public void open() {
        driver.get(MAIN_PAGE_URL);
        new WebDriverWait(driver, 5).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
    @Step("Нажимаем кнопку Логина")
    public void loginButtonClick (){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
        driver.findElement(LOGIN_BUTTON).click();
    }
    @Step("Нажимаем кнопку Личный кабинет")
    public void cabinetButtonClick (){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(CABINET_BUTTON));
        driver.findElement(CABINET_BUTTON).click();
    }
    @Step("Проверяем лрступна ли кнопка создания заказа")
    public boolean isCreateOrderButton () {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(CREATE_ORDER_BUTTON));
        if (driver.findElement(CREATE_ORDER_BUTTON).isEnabled()){
            return true;
        } else {
            return false;
        }
    }
    @Step("Ожидание загрузки страницы(неавторизованный пользователь)")
    public void waitForPageLoadedUnauthorized(){
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
    }
    @Step("ожидание загрузки страницы (авторизованный пользователь)")
    public void waitForPageLoadedAuthorized(){
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(CREATE_ORDER_BUTTON));
    }
    @Step("Нажимаем вкладку с Булками")
    public void bunsTabClick (){
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(BUNS_TAB));

        driver.findElement(BUNS_TAB).click();
    }
    @Step("Нажимаем вкладку с соусами")
    public void saucesTabClick (){
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(SAUCES_TAB));

        driver.findElement(SAUCES_TAB).click();
    }
    @Step("Нажимаем вкладку с начинками")
    public void mainsTabClick (){
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(MAINS_TAB));
        driver.findElement(MAINS_TAB).click();
    }
    @Step("Проверка нажата ли вкладка Булки")
    public String getBunClassName () {
        WebElement element = driver.findElement(BUNS_TAB);
        return element.getAttribute("class");
    }
    @Step("Провека нажата ли вкладка соусов")
    public String getSauceClassName () {
        WebElement element = driver.findElement(SAUCES_TAB);
        return element.getAttribute("class");
    }
    @Step("Проверка нажата ли вкладка начинок")
    public String getMainsClassName () {
        WebElement element = driver.findElement(MAINS_TAB);
        return element.getAttribute("class");
    }

}
