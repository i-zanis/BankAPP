import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import java.util.HashMap;
import static org.uwl.cs.Database.*;
public class Tests {

    @Test
    public void LOGINtest() {
        HashMap<String, Boolean> cases = new HashMap<>();
        cases.put("test@test.com,testing", true);
        cases.put("tesasdt@test.com,testing", false);
        cases.put("test@test.com,test", false);
        for (String key: cases.keySet()) {
            String[] data = key.split(",");
            String email = data[0];
            String pwd = data[1];
            Assert.assertEquals(login(email, pwd), cases.get(key));
        }

    }
}
