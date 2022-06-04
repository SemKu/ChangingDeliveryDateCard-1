package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.usergenerator.RegistrationInfo;
import ru.netology.utils.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

    }

    @Test
    void shouldReschedulingMeeting() {

        String PlanningDate = DataGenerator.generateDate(3);
        String RedevelopedDate = DataGenerator.generateDate(10);

        RegistrationInfo firstMeeting = DataGenerator.Registration.generateByCard("ru");
        Configuration.holdBrowserOpen = true;


        $("[data-test-id='city'] input").setValue(firstMeeting.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(PlanningDate);
        $("[data-test-id='name'] input").setValue(firstMeeting.getName());
        $("[data-test-id='phone'] input").setValue(firstMeeting.getPhone());
        $x("//label[@data-test-id='agreement']").click();
        $$(".button__text").find(exactText("Запланировать")).click();
        $(".notification__content")
                .should(exactText("Встреча успешно запланирована на " + PlanningDate),
                        Duration.ofSeconds(15));

        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(RedevelopedDate);
        $$(".button__text").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content")
                .should(visible)
                .should(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));

        $$(".button__text").find(exactText("Перепланировать")).click();

        $(".notification__content")
                .should(exactText("Встреча успешно запланирована на " + RedevelopedDate),
                        Duration.ofSeconds(15));
    }
}