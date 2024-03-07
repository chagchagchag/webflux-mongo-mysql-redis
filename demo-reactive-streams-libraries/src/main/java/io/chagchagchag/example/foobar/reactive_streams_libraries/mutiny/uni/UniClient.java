package io.chagchagchag.example.foobar.reactive_streams_libraries.mutiny.uni;

import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UniClient {
  public static void main(String[] args) {
    getItems()
        .subscribe()
        .withSubscriber(
            new SimpleUniSubscriber<>(1024)
        );
  }

  public static Uni<Integer> getItems(){
    return Uni.createFrom().item(-1);
  }
}
