package io.chagchagchag.example.foobar.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
		"io.chagchagchag.example.foobar.core",
		"io.chagchagchag.example.foobar.dataaccess"
})
@SpringBootApplication(scanBasePackages = {
		"io.chagchagchag.example.foobar.core",
		"io.chagchagchag.example.foobar.dataaccess"
})
public class FoobarUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoobarUserApplication.class, args);
	}

}

