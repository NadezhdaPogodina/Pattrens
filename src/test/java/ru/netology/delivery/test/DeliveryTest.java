package ru.netology.delivery.test;


import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.data.generator.DataGenerator;


import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan meeting")
    void shouldSuccessfulPlanMeeting() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");

        int daysToAddForFirstMeeting = 4;

        String firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);

        int daysToAddForSecondMeeting = 7;

        String secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());

        $("[name='phone']").setValue(validUser.getPhone());
        $(By.className("checkbox__box")).click();
        $(byText("Забронировать")).click();
        //$(By.className("button__text")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));

        $(By.className("notification__title"));
        $x("//div[contains@class, 'notification__content')]").shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $x("//div[contains@class, 'notification__content')]").shouldBe(visible);

        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $(byText("Забронировать")).click();
        // $(By.className("button__text")).click();

        $("data-test-id='replan-notification].notification__content");
        $("[data-test-id='notification'].notification__content").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $x("//div[contains@class, 'notification__content')]").shouldBe(visible);

        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id='notification'].notification__content");
        $x("//div[contains@class, 'notification__content')]").shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));
        $x("//div[contains@class, 'notification__content')]").shouldBe(visible);


        //var daysToAddForFirstMeeting = 4;
        //var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        //var daysToAddForSecondMeeting = 7;
        //var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
    }
}


