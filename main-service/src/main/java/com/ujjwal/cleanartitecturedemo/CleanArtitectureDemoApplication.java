package com.ujjwal.cleanartitecturedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages ={"com.ujjwal.*"})
@Import({PersistenceModuleConfiguration.class})
public class CleanArtitectureDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CleanArtitectureDemoApplication.class, args);
	}

}
