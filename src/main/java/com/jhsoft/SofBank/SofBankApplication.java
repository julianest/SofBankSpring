package com.jhsoft.SofBank;

import com.jhsoft.SofBank.domains.entities.CheckingAccount;
import com.jhsoft.SofBank.domains.entities.SavingsAccount;
import com.jhsoft.SofBank.domains.entities.TypeAccount;
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
		/*SavingsAccount savingAccount = (SavingsAccount) bankAccountFactory.createAccount("11111111", 800.0, 0.087, TypeAccount.AHORRO);
		CheckingAccount checkingAccount = (CheckingAccount) bankAccountFactory.createAccount("22222222", 600.0, 0.066, TypeAccount.CORRIENTE);
*/
		logger.info("Aplicacion SofBank Iniciada.");
/*
		savingsAccountService.deposit(savingAccount,200);
		savingsAccountService.withdraw(savingAccount,500);
		savingsAccountService.showAccountStatement(savingAccount);

		ChekingAccountService.deposit(checkingAccount,800);
		ChekingAccountService.withdraw(checkingAccount,500);
		ChekingAccountService.showAccountStatement(checkingAccount);
*/
	}

}
