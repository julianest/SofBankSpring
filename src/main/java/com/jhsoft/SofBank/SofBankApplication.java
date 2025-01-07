package com.jhsoft.SofBank;

import com.jhsoft.SofBank.domains.Factory.BankAccountFactory;
import com.jhsoft.SofBank.domains.entities.BankAccount;
import com.jhsoft.SofBank.domains.entities.TypeAccount;
import com.jhsoft.SofBank.domains.services.BankAccountService;
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
	private BankAccountService bankAccountService;

	private static final Logger logger = LoggerFactory.getLogger(SofBankApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SofBankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		runApplication();
	}

	public void runApplication() {
		BankAccount savingAccount = bankAccountFactory.createAccount("11111111", 800.0, 0.087, TypeAccount.AHORRO);

		logger.info("Aplicacion SofBank Iniciada.");

		bankAccountService.deposit(savingAccount,200);
		bankAccountService.withdraw(savingAccount,500);
		bankAccountService.showAccountStatement(savingAccount);

	}

}
