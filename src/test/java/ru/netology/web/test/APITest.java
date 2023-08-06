package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.ApiHelper;
import ru.netology.web.data.DataHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class APITest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    public static String generateDate(Integer addMonth, String pattern) {
        return LocalDate.now().plusMonths(addMonth).format(DateTimeFormatter.ofPattern(pattern));}
    String getApprovedCard = "1111 2222 3333 4444";
    String getDeclinedCard = "5555 6666 7777 8888";
    @Test
    @DisplayName("GET запрос приложения Путешествие дня")
    void apiGet () {
        ApiHelper.shouldReturnPage(200);
    }

    @Test
    @DisplayName("POST запрос приложения Путешествие дня формы Оплата по карте, оплата APPROVED картой")
    void payApiAPPROVEDCard () {
        String month = generateDate(1, "MM");
        String year = generateDate(1, "yy");
        var cardInfo = DataHelper.getCardInfo(getApprovedCard, "IVAN IVANOV", "321", month, year);
        ApiHelper.testingPOSTRequestsPay(cardInfo, 200, "APPROVED");
    }
    @Test
    @DisplayName("POST запрос приложения Путешествие дня формы Оплата по карте, оплата DECLINED картой")
    void payApiDECLINEDCard () {
        String month = generateDate(1, "MM");
        String year = generateDate(1, "yy");
        var cardInfo = DataHelper.getCardInfo(getDeclinedCard, "IVAN IVANOV", "321", month, year);
        ApiHelper.testingPOSTRequestsPay(cardInfo, 400, "DECLINED");
    }

    @Test
    @DisplayName("POST запрос приложения Путешествие дня формы Кредит по данным карты, кредит по данным APPROVED карты")
    void creditApiAPPROVEDCard () {
        String month = generateDate(1, "MM");
        String year = generateDate(1, "yy");
        var cardInfo = DataHelper.getCardInfo(getApprovedCard, "IVAN IVANOV", "321", month, year);
        ApiHelper.testingPOSTRequestsCredit(cardInfo, 200, "APPROVED");
    }
    @Test
    @DisplayName("POST запрос приложения Путешествие дня формы Кредит по данным карты, кредит по данным DECLINED карты")
    void creditApiDECLINEDCard () {
        String month = generateDate(1, "MM");
        String year = generateDate(1, "yy");
        var cardInfo = DataHelper.getCardInfo(getDeclinedCard, "IVAN IVANOV", "321", month, year);
        ApiHelper.testingPOSTRequestsCredit(cardInfo, 400, "DECLINED");
    }
}
