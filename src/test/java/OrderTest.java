import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.Order;

import static configuration.Configuration.getBaseURI;
import static datatest.OrderDataTest.getOrderRequestAllField;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest {

    public static String ORDERS_CREATE = "orders";
    private String[] color;

    public OrderTest(String[] color) {
        this.color = color;
    }


    @Parameterized.Parameters
    public static Object[][] getTextData() {
        return new Object[][]{
                {new String[]{"GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Step("Можно создать заказ с корректными данными")
    public void createOrder() {
        Order order = new Order();
        order.setFirstName(getOrderRequestAllField().getFirstName());
        order.setLastName(getOrderRequestAllField().getLastName());
        order.setAddress(getOrderRequestAllField().getAddress());
        order.setMetroStation(getOrderRequestAllField().getMetroStation());
        order.setPhone(getOrderRequestAllField().getPhone());
        order.setRentTime(getOrderRequestAllField().getRentTime());
        order.setDeliveryDate(getOrderRequestAllField().getDeliveryDate());
        order.setComment(getOrderRequestAllField().getComment());
        order.setColor(color);

        int track = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseURI())
                .body(order)
                .post("orders")
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue())
                .extract()
                .path("track");

    }
}