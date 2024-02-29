package io.chagchagchag.example.foobar.dataaccess;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories(
    basePackages = {
        "io.chagchagchag.example.foobar.dataaccess"
    }
)
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableCaching
@ComponentScan(basePackages = {
    "io.chagchagchag.example.foobar.dataaccess"
})
@EnableAutoConfiguration
public class FoobarDataAccessConfig {

}
