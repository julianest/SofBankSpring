package com.jhsoft.SofBank.domains.Factory;

import com.jhsoft.SofBank.domains.entities.BankAccount;
import com.jhsoft.SofBank.domains.entities.TypeAccount;

public interface IBankAccountFactory {
    BankAccount createAccount(String numberAccount, double balance, double rateInterest, TypeAccount typeAccount);
}
