package com.minkov.heroes.web.base;

import com.minkov.heroes.base.TestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTestBase extends TestBase {
    @LocalServerPort
    protected int port;

    protected String getFullUrl(String route) {
        return "http://localhost:" + port + route;
    }

    protected TestRestTemplate getRestTemplate() {
        return new TestRestTemplate();
    }

    protected TestRestTemplate getRestTemplate(String username, String password) {
        if(username != null && password !=null) {
            return new TestRestTemplate(username, password);
        }

        return getRestTemplate();
    }
}
