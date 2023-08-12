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

    public static String getApprovedCard() {
        String getApprovedCard = "1111 2222 3333 4444";
        return getApprovedCard;
    }

    public static String getDeclinedCard() {
        String getDeclinedCard = "5555 6666 7777 8888";
        return getDeclinedCard;
    }

    public static String generateСVC(Integer length) {
        Faker faker = new Faker(new Locale("en"));
        return faker.number().digits(length);
    }

    public static String generateUsualName() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName().toUpperCase() + " " + faker.name().lastName().toUpperCase();
    }

    public static String generateNameWithDot() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName().toUpperCase() + "." + generateUsualName();
    }
  public static String generateNameWithDash() {
      Faker faker = new Faker(new Locale("en"));
    return generateUsualName() + "-" + faker.name().lastName().toUpperCase();
  }
    public static String generateMinName() {
        Faker faker = new Faker(new Locale("en"));
        return faker.regexify("[A-Z]{1}") + faker.regexify("[A-Z]{1}") + " " + faker.regexify("[A-Z]{1}");
    }
    public static String generateInCyrillicName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName().toUpperCase() + " " + faker.name().lastName().toUpperCase();
    }
    public static String generateLowercaseName() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName().toLowerCase() + " " + faker.name().lastName().toLowerCase();
    }
    public static String generateLessThanMinName() {
        Faker faker = new Faker(new Locale("en"));
        return faker.regexify("[A-Z]{1}")+" " + faker.regexify("[A-Z]{1}");
    }
    public static String generateLettersInCVC() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.regexify("[а-я]{3}");
    }
}
