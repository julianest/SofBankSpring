package com.jhsoft.SofBank;

import com.jhsoft.SofBank.domains.Factory.BankAccountFactory;
import com.jhsoft.SofBank.domains.services.CheckingAccountService;
import com.jhsoft.SofBank.domains.services.SavingsAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SofBankApplication implements CommandLineRunner {

	@Autowired
	private BankAccountFactory bankAccountFactory;

	@Autowired
	private SavingsAccountService savingsAccountService;

	@Autowired
	private CheckingAccountService ChekingAccountService;

	private static final Logger logger = LoggerFactory.getLogger(SofBankApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SofBankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		runApplication();
	}

	public void runApplication() {
		logger.info("Aplicacion SofBank Iniciada.");

	}
}
