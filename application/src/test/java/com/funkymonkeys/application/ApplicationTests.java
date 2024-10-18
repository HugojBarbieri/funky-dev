package com.funkymonkeys.application;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"spring.mail.host=smtp.example.com",
		"spring.mail.port=587",
		"spring.mail.username=your-email@example.com",
		"spring.mail.password=your-password",
		"spring.mail.properties.mail.smtp.auth=true",
		"spring.mail.properties.mail.smtp.starttls.enable=true"
})
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
