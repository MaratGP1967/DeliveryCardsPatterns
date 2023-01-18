package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $x("//*[@data-test-id='city']//input").setValue(validUser.getCity());
        $x("//*[@data-test-id='date']//input[@class='input__control']").sendKeys(Keys.CONTROL + "a");
        $x("//*[@data-test-id='date']//input[@class='input__control']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@data-test-id='date']//input[@class='input__control']").sendKeys(firstMeetingDate);
        $x("//*[@data-test-id='name']//input").setValue(validUser.getName());
        $x("//*[@data-test-id='phone']//input").setValue(validUser.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//div[contains (@class, 'grid-row')]//button").click();
        $x("//div[contains (text(), 'Успешно!')]").shouldBe(Condition.visible);
        $x("//*[@data-test-id='success-notification']//*[@class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate)).shouldBe(Condition.visible);
        $x("//*[contains (@class, 'icon_name_close')]").click();
        $x("//*[@data-test-id='date']//input[@class='input__control']").sendKeys(Keys.CONTROL + "a");
        $x("//*[@data-test-id='date']//input[@class='input__control']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@data-test-id='date']//input[@class='input__control']").sendKeys(secondMeetingDate);
        $x("//div[contains (@class, 'grid-row')]//button").click();
        $x("//div[contains (text(), 'Необходимо подтверждение')]").shouldBe(Condition.visible);
        $x("//*[@data-test-id='replan-notification']//*[@class='notification__content']").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(Condition.visible);
        $x("//*[@data-test-id='replan-notification']//*[@class='notification__content']//button").click();
        $x("//div[contains (text(), 'Успешно!')]").shouldBe(Condition.visible);
        $x("//*[@data-test-id='success-notification']//*[@class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate)).shouldBe(Condition.visible);

    }
}