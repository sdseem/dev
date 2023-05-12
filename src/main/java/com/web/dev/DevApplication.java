package com.web.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class DevApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(DevApplication.class, args);
	}

}
