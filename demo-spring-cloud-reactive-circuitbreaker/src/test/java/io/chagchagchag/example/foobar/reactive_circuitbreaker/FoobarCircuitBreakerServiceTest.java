package io.chagchagchag.example.foobar.reactive_circuitbreaker;

import io.chagchagchag.example.foobar.reactive_circuitbreaker.common.Ready;
import io.chagchagchag.example.foobar.reactive_circuitbreaker.healthcheck.ReadyHealthcheckCBService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import reactor.test.StepVerifier;

@Slf4j
@Import(TestCircuitBreakerConfig.class)
//@ImportAutoConfiguration(
//    classes = {
//        ReactiveResilience4JAutoConfiguration.class,
//        Resilience4JAutoConfiguration.class,
//        CircuitBreakerAutoConfiguration.class,
//        TimeLimiterAutoConfiguration.class
//    }
//)
@SpringBootTest
@ContextConfiguration(classes = ReadyHealthcheckCBService.class)
public class FoobarCircuitBreakerServiceTest {
    @Autowired
    private ReadyHealthcheckCBService readyHealthcheckCBService;
    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;
    @SpyBean
    private Ready ready;

    @BeforeEach
    public void reset(){
        circuitBreakerRegistry.getAllCircuitBreakers()
            .forEach(CircuitBreaker::reset);
    }

    @Test
    public void contextLoads(){

    }

    @Test
    void readyWithoutDelay(){
        var serviceName = "order-service";

        var mono = readyHealthcheckCBService.ready(serviceName, 0L);
        StepVerifier.create(mono)
            .expectNext(ready.ok(serviceName))
            .verifyComplete();

        Mockito.verify(ready).ok(serviceName);
    }
}
