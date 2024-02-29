package io.chagchagchag.example.foobar.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
    "io.chagchagchag.example.foobar.core"
})
@EnableAutoConfiguration
public class FoobarCoreConfig {

}
