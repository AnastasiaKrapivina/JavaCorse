package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FormPage {
    private ElementsCollection button = $$(".button__content");
    private ElementsCollection value = $$(".input__control");

    private SelenideElement positiveNotificationContent = $(Selectors.withText("Операция одобрена Банком."));
    private SelenideElement negativeNotificationContent = $(Selectors.withText("Ошибка! Банк отказал в проведении операции."));

    private ElementsCollection validationMessage = $$(".input__inner");
    public FormPage() {}

    public void getUser(DataHelper.CardInfo info) {
        value.get(0).setValue(info.getNumber());
        value.get(1).setValue(String.valueOf(info.getMonth()));
        value.get(2).setValue(String.valueOf(info.getYear()));
        value.get(3).setValue(String.valueOf(info.getHolder()));
        value.get(4).setValue(String.valueOf(info.getCvc()));
        button.get(2).click();

    }
    public void getValidationMessage(int indexPage) {
        validationMessage.get(indexPage).shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    public void getCardHasExpired(int indexPage) {
        validationMessage.get(indexPage).shouldHave(text("Истёк срок действия карты")).shouldBe(visible);
    }
    public void getWrongFormat(int indexPage) {
        validationMessage.get(indexPage).shouldHave(text("Неверный формат")).shouldBe(visible);
    }
    public void positiveNotification() {
        positiveNotificationContent.shouldBe(visible, Duration.ofSeconds(15));;

    }
    public void negativeNotification() {
        negativeNotificationContent.shouldBe(visible, Duration.ofSeconds(15));;

    }
    public void clear() {
        value.get(0).sendKeys(Keys.CONTROL + "A");
        value.get(0).sendKeys(Keys.BACK_SPACE);
        value.get(1).sendKeys(Keys.CONTROL + "A");
        value.get(1).sendKeys(Keys.BACK_SPACE);
        value.get(2).sendKeys(Keys.CONTROL + "A");
        value.get(2).sendKeys(Keys.BACK_SPACE);
        value.get(3).sendKeys(Keys.CONTROL + "A");
        value.get(3).sendKeys(Keys.BACK_SPACE);
        value.get(4).sendKeys(Keys.CONTROL + "A");
        value.get(4).sendKeys(Keys.BACK_SPACE);
    }
}
