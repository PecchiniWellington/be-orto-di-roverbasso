package com.ortoroverbasso.ortorovebasso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.sentry.Sentry;

@SpringBootApplication(scanBasePackages = "com.ortoroverbasso.ortorovebasso")
@EnableJpaRepositories("com.ortoroverbasso.ortorovebasso.repository")
@EntityScan("com.ortoroverbasso.ortorovebasso.entity")
public class OrtorovebassoApplication {

	public static void main(String[] args) {
		String dsn = "https://a6e64b704e8cd288804c82bb75bfd530@o4509140047101952.ingest.de.sentry.io/4509140086030416";
		Sentry.init(options -> {
			options.setDsn(dsn);
			// altre opzioni possono essere configurate qui
		});

		SpringApplication.run(OrtorovebassoApplication.class, args);
	}

}
