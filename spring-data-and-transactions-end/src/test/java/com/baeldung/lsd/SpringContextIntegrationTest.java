package com.baeldung.lsd;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
class SpringContextIntegrationTest {

    @Test
    void whenContextIsLoaded_thenNoExceptions() {
    }
}