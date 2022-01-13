package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BlockedStatusRegistrationTest {

    RegistrationInfo info = DataGenerator.generateUser("blocked");

    @BeforeEach
    public void setUpTest() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void shouldSendFormBlockedUserWithValidData() {
        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login'] .button__content ").click();
        $("[data-test-id='error-notification'] .notification__title").shouldHave(text("Ошибка"), Duration.ofSeconds(15));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Пользователь заблокирован"), Duration.ofSeconds(15));
    }

    @Test
    void shouldSendFormBlockedUserWithInvalidLogin() {
        $("[data-test-id='login'] input").setValue(DataGenerator.generateLogin("en"));
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login'] .button__content ").click();
        $("[data-test-id='error-notification'] .notification__title").shouldHave(text("Ошибка"), Duration.ofSeconds(15));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }

    @Test
    void shouldSendFormBlockedUserWithInvalidPassword() {
        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(DataGenerator.generatePassword("en"));
        $("[data-test-id='action-login'] .button__content ").click();
        $("[data-test-id='error-notification'] .notification__title").shouldHave(text("Ошибка"), Duration.ofSeconds(15));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }
}
