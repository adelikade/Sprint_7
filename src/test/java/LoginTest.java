import datatest.LoginDataTest;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Test;
import pojo.Courier;
import pojo.Login;

import static configuration.Configuration.getBaseURI;
import static datatest.CourierDataTest.getCourierAllField;
import static datatest.LoginDataTest.requestWithoutLogin;
import static datatest.LoginDataTest.wrongLoginPassword;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest {
    public static final String COURIER_LOGIN = "courier/login";
    private static final String COURIER = "courier";

    @AfterClass
    public static void setId() {

        Login loginRequest = LoginDataTest.from(getCourierAllField());

        int id = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseURI())
                .body(loginRequest)
                .post("courier/login")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue())
                .extract()
                .path("id");

        given()
                .header("Content-type", "application/json")
                .baseUri(getBaseURI())
                .delete("courier/" + id);
    }

    @Test
    @DisplayName("Авторизация курьера с корректными логином и паролем")
    @Step("Курьер с корректными логин и паролем может авторизоваться")
    public void courierCorrectAuth() {
        Courier courierRequest = getCourierAllField();

        given()
                .header("Content-type", "application/json")
                .baseUri(getBaseURI())
                .body(courierRequest)
                .post(COURIER);

        Login loginRequest = LoginDataTest.from(courierRequest);

        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseURI())
                .body(loginRequest)
                .post(COURIER_LOGIN);
        response.then()
                .statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());

    }

    @Test
    @DisplayName("Авторизация курьера под некорректным логинои и паролем")
    @Step("Курьер не может авторизоваться, если введены некорректные логин и пароль")
    public void courierAuthWithIncorrectLoginPassword() {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseURI())
                .body(wrongLoginPassword())
                .post(COURIER_LOGIN);
        // проверяем, что код равен 404
        response.then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("Авторизация пользователя без обязательного поля в запросе")
    @Step("Если в запросе нет обязательного поля, то в ответе вернется ошибка")
    public void courierAuthWithoutRequiredField() {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseURI())
                .body(requestWithoutLogin())
                .post(COURIER_LOGIN);
        response.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }


}