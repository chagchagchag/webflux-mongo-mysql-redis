package io.chagchagchag.example.foobar.reactive_streams_libraries.mutiny.multi;

import io.smallrye.mutiny.Multi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiClient {
  public static void main(String[] args) {
    getItems()
        .subscribe()
        .withSubscriber(new SimpleMultiSubscriber<>(1024));
  }

  public static Multi<Integer> getItems(){
    return Multi.createFrom().items(1,2,3,4,5);
  }
}
