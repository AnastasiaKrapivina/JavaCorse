package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.ApiHelper;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SQLHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class APITest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    String getApprovedCard = DataHelper.getApprovedCard();
    String getDeclinedCard = DataHelper.getDeclinedCard();
    String month = DataHelper.generateDate(2, "MM");
    String year = DataHelper.generateDate(2, "yy");
    String cvc = DataHelper.generateСVC(3);
    String usualName = DataHelper.generateUsualName();

    @Test
    @DisplayName("GET запрос приложения Путешествие дня")
    void apiGet() {
        ApiHelper.shouldReturnPage(200);
    }

    @Test
    @DisplayName("POST запрос приложения Путешествие дня формы Оплата по карте, оплата APPROVED картой")
    void payApiAPPROVEDCard() {
        var cardInfo = DataHelper.getCardInfo(getApprovedCard, usualName, cvc, month, year);
        assertAll(() -> ApiHelper.testingPOSTRequestsPay(cardInfo, 200, "APPROVED"),
                () -> assertEquals("APPROVED", SQLHelper.getPayData()[1]),
                () -> assertEquals(SQLHelper.getUserPaymentId(), SQLHelper.getPayData()[0]));
    }

    @Test
    @DisplayName("POST запрос приложения Путешествие дня формы Оплата по карте, оплата DECLINED картой")
    void payApiDECLINEDCard() {
        var cardInfo = DataHelper.getCardInfo(getDeclinedCard, usualName, cvc, month, year);
        assertAll(() -> ApiHelper.testingPOSTRequestsPay(cardInfo, 400, "DECLINED"),
                () -> assertEquals("DECLINED", SQLHelper.getPayData()[1]),
                () -> assertEquals(SQLHelper.getUserPaymentId(), SQLHelper.getPayData()[0]));
    }

    @Test
    @DisplayName("POST запрос приложения Путешествие дня формы Кредит по данным карты, кредит по данным APPROVED карты")
    void creditApiAPPROVEDCard() {
        var cardInfo = DataHelper.getCardInfo(getApprovedCard, usualName, cvc, month, year);
        assertAll(() -> ApiHelper.testingPOSTRequestsCredit(cardInfo, 200, "APPROVED"),
                () -> assertEquals("APPROVED", SQLHelper.getCreditData()[1]),
                () -> assertEquals(SQLHelper.getUserPaymentId(), SQLHelper.getCreditData()[0]));
    }
    @Test
    @DisplayName("POST запрос приложения Путешествие дня формы Кредит по данным карты, кредит по данным DECLINED карты")
    void creditApiDECLINEDCard() {
        var cardInfo = DataHelper.getCardInfo(getDeclinedCard, usualName, cvc, month, year);
        assertAll(() -> ApiHelper.testingPOSTRequestsCredit(cardInfo, 400, "DECLINED"),
                () -> assertEquals("DECLINED", SQLHelper.getCreditData()[1]),
                () -> assertEquals(SQLHelper.getUserPaymentId(), SQLHelper.getCreditData()[0]));
    }
}
