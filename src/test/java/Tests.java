import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.uwl.cs.Database.*;

public class Tests {

    @Test
    public void testLogin() {
        getOrCreateUser("test", "test", "test@test.com", "testing", "123123123");
        int id = existsID("test@test.com", "testing");
        HashMap<String, Boolean> cases = new HashMap<>();
        cases.put("test@test.com,testing", true);
        cases.put("tesasdt@test.com,testing", false);
        cases.put("test@test.com,test", false);
        for (String key : cases.keySet()) {
            String[] data = key.split(",");
            String email = data[0];
            String pwd = data[1];
            Assert.assertEquals(login(email, pwd), cases.get(key));
        }
        deleteUser(id);
    }

    @Test
    public void testGetCreateUser() {
        HashMap<Boolean, Boolean> cases = new HashMap<>();
        cases.put(getOrCreateUser(
                "test", "test", "testing@gmail.com", "testing123", "123123123"
                ),
                true);
        cases.put(getOrCreateUser(
                "test", "test", "testing@gmail.com", "testing123", "123123123"
                ),
                false);
        for (boolean key : cases.keySet()) {
            Assert.assertEquals(cases.get(key), cases.get(key));
        }
        int id = existsID("testing@gmail.com", "testing123");
        deleteUser(id);
    }
}
