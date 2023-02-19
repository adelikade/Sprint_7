package datatest;

import org.apache.commons.lang3.RandomStringUtils;
import pojo.Order;

public class OrderDataTest {
    public static final String FIRST_NAME = RandomStringUtils.randomAlphanumeric(5);
    public static final String LAST_NAME = RandomStringUtils.randomAlphanumeric(8);
    public static final String ADDRESS = "Mira 90";
    public static final String METRO_STATION = "2";
    public static final String PHONE = "+7 933 444 09 85";
    public static final int RENT_TIME = 4;
    public static final String DELIVERY_DATE = "2023-02-17";
    public static final String COMMENT = "Можно розовый самокат?";


    public static Order getOrderRequestAllField() {
        Order order = new Order();
        order.setFirstName(FIRST_NAME);
        order.setLastName(LAST_NAME);
        order.setAddress(ADDRESS);
        order.setMetroStation(METRO_STATION);
        order.setPhone(PHONE);
        order.setRentTime(RENT_TIME);
        order.setDeliveryDate(DELIVERY_DATE);
        order.setComment(COMMENT);
        return order;
    }
}