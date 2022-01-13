package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@UtilityClass
public class DataGenerator {

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void setUser(RegistrationInfo info) {
        given()
                .spec(requestSpec)
                .body(info)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String generateLogin(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().username();
    }

    public static String generatePassword(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.internet().password();
    }

    public static RegistrationInfo generateUser(String status) {
        RegistrationInfo info = new RegistrationInfo(generateLogin("en"),
                generatePassword("en"),
                status);
        setUser(info);
        return info;
    }
}





