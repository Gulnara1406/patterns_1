package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 3;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        String city = DataGenerator.generateCity("ru");
        String name = DataGenerator.generateName("ru");
        String phone = DataGenerator.generatePhone("ru");

        Configuration.holdBrowserOpen = true;
        $("span[data-test-id = city]").click();
        $("input[placeholder=Город]").setValue(city);
        $("span[class=menu-item__control]").click();
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(firstMeetingDate);
        $("input[name=name]").setValue(name);
        $("span[data-test-id=phone]").click();
        $("input[name=phone]").setValue(phone);
        $("label[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(11))
                .shouldBe(Condition.visible);
        refresh();
        $("span[data-test-id = city]").click();
        $("input[placeholder=Город]").setValue(city);
        $("span[class=menu-item__control]").click();
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(secondMeetingDate);
        $("input[name=name]").setValue(name);
        $("span[data-test-id=phone]").click();
        $("input[name=phone]").setValue(phone);
        $("label[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $x("//*[contains(text(), 'У вас уже запланирована встреча на другую дату. Перепланировать?!");
        $x("//span[contains(text(), 'Перепланировать')]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(11))
                .shouldBe(Condition.visible);
        $x("//div[contains(text(), 'Успешно!')]");
    }
}