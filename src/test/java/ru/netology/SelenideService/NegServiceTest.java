package ru.netology.SelenideService;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class NegServiceTest {
    public static String setLocalDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy",
                new Locale("ru")));
    }


    @Test
    void shouldIncorrectCity() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Изумрудный город");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Смирнов Василий");
        $("[data-test-id=phone] input").setValue("+79263451212");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    void shoulLostCity() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Смирнов Василий");
        $("[data-test-id=phone] input").setValue("+79263451212");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldUnavailableData() {
        String date = setLocalDate(2);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Смирнов Василий");
        $("[data-test-id=phone] input").setValue("+79263451212");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

    @Test
    void shouldIncorrectName() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Smirnov Vasiliy");
        $("[data-test-id=phone] input").setValue("+79263451212");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible);
    }

    @Test
    void shouldIncorrectLimitNumMin() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Смирнов Василий");
        $("[data-test-id=phone] input").setValue("+7926345");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test
    void shouldIncorrectLimitNumMax() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Смирнов Василий");
        $("[data-test-id=phone] input").setValue("+792634512121212");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test
    void shouldResultAgreementTest() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Смирнов Василий");
        $("[data-test-id=phone] input").setValue("+79263451212");
        $(byText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).shouldBe(visible);
    }
}
