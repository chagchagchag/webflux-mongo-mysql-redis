package io.chagchagchag.example.foobar.spring_webflux.error_handling;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ErrorToAnotherError_Example1 {
  public static void main(String[] args) {
    Flux.error(new IllegalArgumentException("잘못된 요청이에요"))
        .onErrorResume(
            e -> Flux.error(new ArgumentAmbiguousException("필수파라미터가 누락되었습니다.", e))
        )
        .subscribe(
            v -> {
              log.info("v ::: " + v);
            },
            err -> {
              log.info("error ::: " + err);
            }
        );
  }

  static class ArgumentAmbiguousException extends IllegalArgumentException{

    public ArgumentAmbiguousException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
