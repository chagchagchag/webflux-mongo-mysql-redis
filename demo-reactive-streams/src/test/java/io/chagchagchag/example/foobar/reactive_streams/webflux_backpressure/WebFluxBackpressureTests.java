package io.chagchagchag.example.foobar.reactive_streams.webflux_backpressure;

import java.util.concurrent.Flow.Subscription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class WebFluxBackpressureTests {
  @DisplayName("REQUEST_CHUNK")
  @Test
  public void TEST_REQUEST_CHUNK(){
    // given
    Flux<Integer> request = Flux.range(1,50);

    // when
    request
        .subscribe(
            System.out::println,
            throwable -> throwable.printStackTrace(),
            () -> System.out.println("50개 요청 모두 완료"),
            subscription -> {
              for(int i=0; i<5; i++){
                System.out.println(">>> request 10 element ");
                subscription.request(10);
              }
            }
        );

    // then
    StepVerifier.create(request)
        .expectSubscription()
        .thenRequest(10)
        .expectNext(1,2,3,4,5,6,7,8,9,10)
        .thenRequest(10)
        .expectNext(11,12,13,14,15,16,17,18,19,20)
        .thenRequest(10)
        .expectNext(21,22,23,24,25,26,27,28,29,30)
        .thenRequest(10)
        .expectNext(31,32,33,34,35,36,37,38,39,40)
        .thenRequest(10)
        .expectNext(41,42,43,44,45,46,47,48,49,50)
        .verifyComplete();
  }


  @DisplayName("LIMIT_RATE")
  @Test
  public void TEST_LIMIT_RATE(){
    // given
    Flux<Integer> flux = Flux.range(1, 25);

    // when
    flux.limitRate(10);
    flux.subscribe(
        System.out::println,
        err -> err.printStackTrace(),
        () -> System.out.println("Finished"),
        subscription -> subscription.request(15)
    );

    // then
    StepVerifier.create(flux)
        .expectSubscription()
        .thenRequest(15)
        .expectNext(1,2,3,4,5,6,7,8,9,10) // limit 10 수행
        .expectNext(11,12,13,14,15) // 15 중 남은 5개 수행
        .thenRequest(10) // limit 10 수행
        .expectNext(16,17,18,19,20,21,22,23,24,25)
        .verifyComplete();
  }

  @DisplayName("CANCEL")
  @Test
  public void TEST_CANCEL(){
    // given
    Flux<Integer> flux = Flux.range(1,10).log();

    // when
    flux.subscribe(new BaseSubscriber<Integer>() {
      @Override
      protected void hookOnNext(Integer value) {
        request(3);
        System.out.println(value);
        cancel();
      }
    });

    // then
    StepVerifier.create(flux)
        .expectNext(1,2,3)
        .thenCancel()
        .verify();
  }
}
