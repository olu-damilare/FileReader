package com.dotpay.dotpay;

import com.dotpay.dotpay.services.BlockedIPServices;
import com.dotpay.dotpay.services.impl.FileServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class DotPayApplication implements CommandLineRunner {

	private final FileServices fileServices;

	public static void main(String[] args) {
		SpringApplication.run(DotPayApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Loading data from file ================================== ");

		/* The command line argument received are passed to the fileServices bean
		 * to load the content of the user_access.txt file.
		 */
		fileServices.loadUserAccessFileToDB(args);
	}
}
