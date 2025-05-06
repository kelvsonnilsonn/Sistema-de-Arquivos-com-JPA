import model.User;
import model.userdata.Email;
import model.userdata.Password;
import model.userdata.UserAccess;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        User user = new User(1,
                new UserAccess("kelvson", new Password("1234512"),
                        new Email("abacate1234K@gmail.com")), Date.valueOf("2025-10-02"));
    }
}
