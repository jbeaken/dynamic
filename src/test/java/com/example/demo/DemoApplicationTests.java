package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PulsarContainer;
import org.testcontainers.utility.DockerImageName;

class DemoApplicationTests {

    public static void main(String[] args) {
        SpringApplication.from(DemoApplication::main)
                .with(ContainerConfiguration.class)
                .run(args);
    }

    @TestConfiguration
    static class ContainerConfiguration {

        @Bean
        PulsarContainer pulsarContainer(DynamicPropertyRegistry registry) {
            PulsarContainer pulsar = new PulsarContainer(DockerImageName.parse("apachepulsar/pulsar:2.11.0"))
                    .withReuse(true);
            registry.add("spring.pulsar.client.service-url", pulsar::getPulsarBrokerUrl);
            return pulsar;
        }

    }

}
