package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SQLHelper;
import ru.netology.web.page.RootPage;
import ru.netology.web.page.FormPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreditTest {
    FormPage formPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
        var rootPage = new RootPage();
        formPage = rootPage.openCreditPage(indexPage);
    }

    String getApprovedCard = DataHelper.getApprovedCard();
    String getDeclinedCard = DataHelper.getDeclinedCard();
    Integer indexPage = 1;
    String month = DataHelper.generateDate(2, "MM");
    String year = DataHelper.generateDate(2, "yy");
    String cvc = DataHelper.generateСVC(3);
    String usualName = DataHelper.generateUsualName();
    String nameWithDot = DataHelper.generateNameWithDot();
    String nameWithDash = DataHelper.generateNameWithDash();
    String minName = DataHelper.generateMinName();
    String cyrillicName = DataHelper.generateInCyrillicName();
    String lowercaseName = DataHelper.generateLowercaseName();
    String numbersName = DataHelper.generateСVC(6);
    String lessThanMinName =  DataHelper.generateLessThanMinName();
    String lessThanCVC = DataHelper.generateСVC(2);
    String lettersInCVC = DataHelper.generateLettersInCVC();

    @Test
    @DisplayName("Тест 1 Ввод допустимых значений в поля формы")
    void payTurAPPROVEDCard() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, usualName, cvc, month, year);
        formPage.getUser(approvedCardInfo);
        assertAll(() -> formPage.positiveNotification(),
                () -> assertEquals("APPROVED", SQLHelper.getCreditData()[1]),
                () -> assertEquals(SQLHelper.getUserPaymentId(), SQLHelper.getCreditData()[0]));
    }

    @Test
    @DisplayName("Тест 2 Ввод допустимых значений в поля формы, значение поля Владелец содержит точку")
    void payTurAPPROVEDCardOwnerWithDot() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, nameWithDot, cvc, month, year);
        formPage.getUser(approvedCardInfo);
        assertAll(() -> formPage.positiveNotification(),
                () -> assertEquals("APPROVED", SQLHelper.getCreditData()[1]),
                () -> assertEquals(SQLHelper.getUserPaymentId(), SQLHelper.getCreditData()[0]));
    }

    @Test
    @DisplayName("Тест 3 Ввод допустимых значений в поля формы, значение поля Владелец содержит дефис")
    void payTurAPPROVEDCardOwnerContainsHyphen() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, nameWithDash, cvc, month, year);
        formPage.getUser(approvedCardInfo);
        assertAll(() -> formPage.positiveNotification(),
                () -> assertEquals("APPROVED", SQLHelper.getCreditData()[1]),
                () -> assertEquals(SQLHelper.getUserPaymentId(), SQLHelper.getCreditData()[0]));
    }

    @Test
    @DisplayName("Тест 4 Ввод допустимых значений в поля формы, значение поля Владелец четыре символа")
    void payTurAPPROVEDCardOwnerFourCharacters() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, minName, cvc, month, year);
        formPage.getUser(approvedCardInfo);
        assertAll(() -> formPage.positiveNotification(),
                () -> assertEquals("APPROVED", SQLHelper.getCreditData()[1]),
                () -> assertEquals(SQLHelper.getUserPaymentId(), SQLHelper.getCreditData()[0]));
    }

    @Test
    @DisplayName("Тест 5 Оплата DECLINED картой")
    void payTurDECLINEDCard() {
        var declinedCardInfo = DataHelper.getCardInfo(getDeclinedCard, usualName, cvc, month, year);
        formPage.getUser(declinedCardInfo);
        assertAll(() -> formPage.negativeNotification(),
                () -> assertEquals("DECLINED", SQLHelper.getCreditData()[1]),
                () -> assertEquals(SQLHelper.getUserPaymentId(), SQLHelper.getCreditData()[0]));
    }

    @Test
    @DisplayName("Тест 6 Отправка незаполненных полей формы")
    void sendingBlankFormFields() {
        var approvedCardInfo = DataHelper.getCardInfo(" ", " ", " ", " ", " ");
        formPage.getUser(approvedCardInfo);
        assertAll(() -> formPage.getValidationMessage(0),
                () -> formPage.getValidationMessage(1),
                () -> formPage.getValidationMessage(2),
                () -> formPage.getValidationMessage(3),
                () -> formPage.getValidationMessage(4));
    }

    @Test
    @DisplayName("Тест 7 Отправка незаполненного поля Номер карты")
    void sendingAnEmptyFieldCardNumber() {
        var approvedCardInfo = DataHelper.getCardInfo(" ", usualName, cvc, month, year);
        formPage.getUser(approvedCardInfo);
        formPage.getValidationMessage(0);
    }

    @Test
    @DisplayName("Тест 8 Отправка незаполненного поля Месяц")
    void sendingAnEmptyFieldMonth() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, usualName, cvc, " ", year);
        formPage.getUser(approvedCardInfo);
        formPage.getValidationMessage(1);
    }

    @Test
    @DisplayName("Тест 9 Отправка незаполненного поля Год")
    void sendingAnEmptyFieldYear() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, usualName, cvc, month, " ");
        formPage.getUser(approvedCardInfo);
        formPage.getValidationMessage(2);
    }

    @Test
    @DisplayName("Тест 10 Отправка незаполненного поля Владелец")
    void sendingAnEmptyFieldOwner() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, " ", cvc, month, year);
        formPage.getUser(approvedCardInfo);
        formPage.getValidationMessage(3);
    }

    @Test
    @DisplayName("Тест 11 Отправка незаполненного поля CVC/CVV")
    void sendingAnEmptyFieldCVC() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, usualName, " ", month, year);
        formPage.getUser(approvedCardInfo);
        formPage.getValidationMessage(4);
    }

    @Test
    @DisplayName("Тест 12 Оплата просроченной картой, срок действия истек месяц назад")
    void paymentExpiredMonthAgo() {
        String lastMonth = DataHelper.generateDate(-1, "MM");
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, usualName, cvc, lastMonth, year);
        formPage.getUser(approvedCardInfo);
        formPage.getCardHasExpired(1);
    }

    @Test
    @DisplayName("Тест 13 Ввод значения в поле Месяц недопустимого формата (X вместо XX)")
    void enteringValueInMonthFieldIsNotValidFormat() {
        String invalidMonth = DataHelper.generateСVC(1);
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, usualName, cvc, invalidMonth, year);
        formPage.getUser(approvedCardInfo);
        formPage.getWrongFormat(1);
    }

    @Test
    @DisplayName("Тест 14 Ввод несуществующего значения в поле Месяц (20)")
    void eteringNonExistentValueInMonthField() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, usualName, cvc, "20", year);
        formPage.getUser(approvedCardInfo);
        formPage.getWrongFormat(1);
    }

    @Test
    @DisplayName("Тест 15 Оплата просроченной картой, срок действия истек год назад")
    void cardExpiredYearAgo() {
        String lastYear = DataHelper.generateDate(-12, "yy");
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, usualName, cvc, month, lastYear);
        formPage.getUser(approvedCardInfo);
        formPage.getCardHasExpired(2);
    }

    @Test
    @DisplayName("Тест 16 Ввод недопустимого значения в поле Год (X вместо XX)")
    void enteringAnInvalidValueInYearField() {
        String invalidYear = DataHelper.generateСVC(1);
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, usualName, cvc, month, invalidYear);
        formPage.getUser(approvedCardInfo);
        formPage.getWrongFormat(2);
    }

    @Test
    @DisplayName("Тест 17 Ввод значения в поле Владелец на кириллице")
    void enteringValueInOwnerFieldInCyrillic() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, cyrillicName, cvc, month, year);
        formPage.getUser(approvedCardInfo);
        formPage.getWrongFormat(3);
    }

    @Test
    @DisplayName("Тест 18 Ввод значения в поле Владелец строчными латинскими буквами")
    void enteringValueInOwnerFieldInLowercaseLatinLetters() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, lowercaseName, cvc, month, year);
        formPage.getUser(approvedCardInfo);
        formPage.getWrongFormat(3);
    }

    @Test
    @DisplayName("Тест 19 Ввод значения в поле Владелец цифрами")
    void enteringValueInOwnerFieldInNumbers() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, numbersName, cvc, month, year);
        formPage.getUser(approvedCardInfo);
        formPage.getWrongFormat(3);
    }

    @Test
    @DisplayName("Тест 20 Ввод значения в поле Владелец длинной менее допустимого")
    void enteringValueInOwnerFieldLengthLessThanAllowed() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, lessThanMinName, cvc, month, year);
        formPage.getUser(approvedCardInfo);
        formPage.getWrongFormat(3);
    }

    @Test
    @DisplayName("Тест 21 Ввод буквенного значения в поле CVC/CVV")
    void enteringLiteralValueInCVCField() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, usualName, lettersInCVC, month, year);
        formPage.getUser(approvedCardInfo);
        formPage.getValidationMessage(4);
    }

    @Test
    @DisplayName("Тест 22 Ввод значения в поле CVC/CVV длинной менее, чем допустимое")
    void enteringValueInCVCLongerThanAllowed() {
        var approvedCardInfo = DataHelper.getCardInfo(getApprovedCard, usualName, lessThanCVC, month, year);
        formPage.getUser(approvedCardInfo);
        formPage.getWrongFormat(4);
    }
}
