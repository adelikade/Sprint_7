import datatest.LoginDataTest;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Test;
import pojo.Login;

import static configuration.Configuration.getBaseURI;
import static datatest.CourierDataTest.getCourierAllField;
import static datatest.CourierDataTest.getCourierWithoutLogin;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class CourierTest {
    public static final String COURIER_LOGIN = "courier/login";
    private static final String COURIER = "courier";

    @AfterClass
    public static void setId() {

        Login login = LoginDataTest.from(getCourierAllField());

        int id = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseURI())
                .body(login)
                .post(COURIER_LOGIN)
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
                .delete(COURIER + '/' + id);
    }

    @Test
    @DisplayName("Создание курьера")
    @Step("При создании курьера возвращается код 201 и в теле ответа = 'ok: true'")
    public void createCourierOriginal() {

        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseURI())
                .body(getCourierAllField())
                .post(COURIER);
        response.then().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));

    }


    @Test
    @DisplayName("Создание клиента без логина или пароля")
    @Step("Если одного из обязательных полей нет, запрос возвращает код 400 и в теле ответа message = \"Недостаточно данных для создания учетной записи\"")
    public void createCourierWithoutLogin() {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseURI())
                .body(getCourierWithoutLogin())
                .post(COURIER);
        response.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание уже существующего курьера")
    @Step("Если курьер уже был создан, то запрос возвращает код 409 и в теле ответа message = \"Этот логин уже используется\" ")
    public void createCourierRepeating() {

        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseURI())
                .body(getCourierAllField())
                .post(COURIER);
        response.then().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется"));
    }
}