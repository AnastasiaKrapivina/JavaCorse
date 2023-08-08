package ru.netology.web.data;

import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.github.javafaker.Faker;

import java.util.Locale;


public class DataHelper {
    private DataHelper() {
    }

    public static CardInfo getCardInfo(String number, String holder, String cvc, String month, String year) {
        return new CardInfo(number, holder, cvc, month, year);
    }

    @Value
    public static class CardInfo {
        String number;
        String holder;
        String cvc;
        String month;
        String year;
    }

    public static String generateDate(Integer addMonth, String pattern) {
        return LocalDate.now().plusMonths(addMonth).format(DateTimeFormatter.ofPattern(pattern));
    }

    private static Faker faker = new Faker(new Locale("en"));

    public static String getApprovedCard() {
        String getApprovedCard = "1111 2222 3333 4444";
        return getApprovedCard;
    }

    public static String getDeclinedCard() {
        String getDeclinedCard = "5555 6666 7777 8888";
        return getDeclinedCard;
    }

    public static String generateСVC(Integer length) {
        return faker.number().digits(length);
    }

    public static String generateUsualName() {
        return faker.name().firstName().toUpperCase() + " " + faker.name().lastName().toUpperCase();
    }

    public static String generateNameWithDot() {
        return faker.name().firstName().toUpperCase() + "." + generateUsualName();
    }
  public static String generateNameWithDash() {
    return generateUsualName() + "-" + faker.name().lastName().toUpperCase();
  }
    public static String generateMinName() {
        return faker.regexify("[A-Z]{1}") + faker.regexify("[A-Z]{1}") + " " + faker.regexify("[A-Z]{1}");
    }
}
