package com.myspot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "jwt.secret=DbxMXfma5n2Z9fx5LFKtXT8oTiS7RTxjS/9rzFJaZsc=",  // Use your generated key
        "twilio.phone.number=+1234567890",
        "twilio.account.sid=ACb0bd111b35429a6fa44b643bd747db47",
        "twilio.auth.token=5ea9d0481c2a0834d1f069611bd83261",
        "spring.jpa.hibernate.ddl-auto=update"
})
class MyspotApplicationTests {
    @Test
    void contextLoads() {
    }
}