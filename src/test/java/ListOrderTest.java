import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static configuration.Configuration.getBaseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ListOrderTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Step("Запрос возвращает код 200 и в тело ответа возвращается верный список заказов")
    public void getListOrder() {
        Response response = given()
                .baseUri(getBaseURI())
                .header("Content-type", "application/json")
                .get("orders");
        response.then().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());

    }

}