package io.chagchagchag.example.foobar.reactive_streams.reactor.server;

import java.nio.ByteBuffer;

public class MsgCodec {
  ByteBuffer encode(final String msg){
    return ByteBuffer.wrap(msg.getBytes());
  }

  String decode(final ByteBuffer buffer){
    return new String(
        buffer.array(),
        buffer.arrayOffset(),
        buffer.remaining()
    );
  }
}
