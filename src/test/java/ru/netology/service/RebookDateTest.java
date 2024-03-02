package ru.netology.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;

class RebookDateTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful book and rebook meeting")
    void shouldSuccessfulBookAndRebookMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 5;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").setValue(validUser.getCity());
        //sleep(2000);
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        //sleep(2000);
        $("[placeholder='Дата встречи']").setValue(firstMeetingDate);
        //sleep(2000);
        $("[name='name']").setValue(validUser.getName());
        //sleep(2000);
        $("[name='phone']").setValue(validUser.getPhone());
        //sleep(2000);
        $(".checkbox__box").click();
        //sleep(2000);
        $$("button").find(Condition.exactText("Запланировать")).click();
        //sleep(2000);

        if ($(withText("Доставка в выбранный город недоступна")).isDisplayed()) {
            int i = validUser.getCity().length() - 2;
            while (i > 0) {
                $("[data-test-id=city] .input__control").sendKeys(Keys.BACK_SPACE);
                i--;
            }
            $(".menu-item__control").click();
            $(".button__text").click();
        }
        $("[data-test-id=success-notification]>.notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        //sleep(2000);
        $("[placeholder='Дата встречи']").setValue(secondMeetingDate);
        //sleep(2000);
        $$("button").find(Condition.exactText("Запланировать")).click();
        //sleep(2000);
        $(withText("Необходимо подтверждение")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("div.notification__content > button").click();
        $("[data-test-id=success-notification]>.notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

}