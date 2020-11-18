package com.sorint.demo.service;

import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private HazelcastInstance hazelcastInstance;

//    @Bean
//    public TestRestTemplate restTemplate(TestRestTemplateBuilder builder){
//        return builder.build();
//    }


    @Test
    public void useCachedValue() {
        // given
        String isbn = "12345";
        String cachedValue = "cached-value";
        hazelcastInstance.getMap("service/books").put(isbn, cachedValue);

        String response = restTemplate.getForObject(String.format("http://localhost:%s/service%Fbooks/%s", port, isbn), String.class);

        // then
        assertThat(response).isEqualTo(cachedValue);
    }
}