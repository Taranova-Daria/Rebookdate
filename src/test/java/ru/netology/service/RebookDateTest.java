package ru.netology.service;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class RebookDateTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @BeforeAll
    static void setupAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
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
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(firstMeetingDate);
        $("[name='name']").setValue(validUser.getName());
        $("[name='phone']").setValue(validUser.getPhone());
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id=success-notification]>.notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(secondMeetingDate);
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Необходимо подтверждение")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("div.notification__content > button").click();
        $("[data-test-id=success-notification]>.notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

}