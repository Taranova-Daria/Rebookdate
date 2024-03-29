package ru.netology.service;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static Faker faker = new Faker(new Locale("ru"));

    public static String generateDate(int shift) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        return date.plusDays(shift).format(dateFormat);
    }

    public static String generateCity() {
        var cities = new String[]{"Москва", "Екатеринбург", "Казань", "Санкт-Петербург", "Уфа", "Краснодар"};
        return cities[new Random().nextInt(cities.length)];
    }

    public static String generateName(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();

    }

    public static String generatePhone(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(), generateName(locale), generatePhone(locale));
        }

        @Value
        public static class UserInfo {
            String city;
            String name;
            String phone;
        }

    }
}