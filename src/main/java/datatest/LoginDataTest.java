package datatest;

import org.apache.commons.lang3.RandomStringUtils;
import pojo.Courier;
import pojo.Login;

public class LoginDataTest {
    private static final String LOGIN = RandomStringUtils.randomAlphanumeric(5);
    private static final String PASSWORD = RandomStringUtils.randomAlphanumeric(5);

    public static Login from(Courier courier) {
        Login login = new Login();
        login.setLogin(courier.getLogin());
        login.setPassword(courier.getPassword());
        return login;

    }

    public static Login wrongLoginPassword() {
        Login login = new Login();
        login.setLogin(LOGIN);
        login.setPassword(PASSWORD);
        return login;

    }

    public static Login requestWithoutLogin() {
        Login login = new Login();
        login.setPassword(PASSWORD);
        return login;
    }
}