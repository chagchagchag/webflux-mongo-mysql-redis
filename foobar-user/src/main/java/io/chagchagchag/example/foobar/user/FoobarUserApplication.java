package io.chagchagchag.example.foobar.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
		"io.chagchagchag.example.foobar.dataaccess",
		"io.chagchagchag.example.foobar.core",
		"io.chagchagchag.example.foobar.user",
})
@EnableR2dbcRepositories(
		basePackages = {
				"io.chagchagchag.example.foobar.dataaccess",
		}
)
public class FoobarUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoobarUserApplication.class, args);
	}

}

