package ru.netology.web.data;

import lombok.Value;


public class DataHelper {
  private DataHelper() {}

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

}
