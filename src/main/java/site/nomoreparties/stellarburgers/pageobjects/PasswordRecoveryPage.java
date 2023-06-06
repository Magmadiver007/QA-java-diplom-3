package site.nomoreparties.stellarburgers.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PasswordRecoveryPage {
    private static final String RECOVERY_PAGE_URL = "https://stellarburgers.nomoreparties.site/forgot-password";
    private static final By LOGIN_BUTTON = By.xpath("//a[text()='Войти']");
    private final WebDriver driver;
    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Открываем сраницу восстановления пароля")
    public void open() {
        driver.get(RECOVERY_PAGE_URL);
    }
    @Step("Нажимем кнопку Войти")
    public void clickLoginButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
        driver.findElement(LOGIN_BUTTON).click();
    }
}
