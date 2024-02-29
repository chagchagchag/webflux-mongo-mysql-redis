package io.chagchagchag.example.foobar.dataaccess;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories(
    basePackages = {
        "io.chagchagchag.example.foobar.dataaccess"
    }
)
@ComponentScan(basePackages = {
    "io.chagchagchag.example.foobar.dataaccess"
})
@EnableAutoConfiguration
public class FoobarDataAccessConfig {

}
