package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class RootPage {
  private SelenideElement heading = $("[id='root']");
  private ElementsCollection button = $$(".button__content");
  private SelenideElement payContent = $(byText("Оплата по карте"));
  private SelenideElement creditContent = $(byText("Кредит по данным карты"));

  public RootPage() {
    heading.shouldBe(visible);
  }

  public FormPage openPayPage(int index) {
     button.get(index).click();
    payContent.shouldHave(visible, Duration.ofSeconds(15));
    return new FormPage();
  }
  public FormPage openCreditPage(int index) {
    button.get(index).click();
    creditContent.shouldHave(visible, Duration.ofSeconds(15));
    return new FormPage();
  }
}
