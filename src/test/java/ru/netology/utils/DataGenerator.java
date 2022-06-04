package ru.netology.utils;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import ru.netology.usergenerator.RegistrationInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    @UtilityClass
    public static class Registration {

        public RegistrationInfo generateByCard(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new RegistrationInfo(
                    faker.address().city(),
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber()
            );
        }
    }

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
