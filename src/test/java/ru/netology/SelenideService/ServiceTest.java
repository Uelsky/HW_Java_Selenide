package ru.netology.SelenideService;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class ServiceTest {
    public static String setLocalDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy",
                new Locale("ru")));
    }


    @Test
    void shouldApplicationTest() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        //$(byText("Выберите дату встречи с представителем банка")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Смирнов Василий");
        $("[data-test-id=phone] input").setValue("+79263451212");
        //$x("//*[text()='Я соглашаюсь с условиями обработки и использования моих персональных данных']").click();
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void shouldAnotherCityTest() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Смирнов Василий");
        $("[data-test-id=phone] input").setValue("+7926345");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void shouldMoreThreeDaysTimestampTest() {
        String date = setLocalDate(4);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        //$(byText("Выберите дату встречи с представителем банка")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("asdfasf");
        $("[data-test-id=phone] input").setValue("+79263451212");
        //$x("//*[text()='Я соглашаюсь с условиями обработки и использования моих персональных данных']").click();
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void shouldDoubleSurnameTest() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Сева");
        //$(byText("Выберите дату встречи с представителем банка")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Смирнов-Пупкин Василий");
        $("[data-test-id=phone] input").setValue("+79263451212");
        //$x("//*[text()='Я соглашаюсь с условиями обработки и использования моих персональных данных']").click();
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }
}
