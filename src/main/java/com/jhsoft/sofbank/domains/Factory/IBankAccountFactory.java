package com.jhsoft.sofbank.domains.Factory;

import com.jhsoft.sofbank.domains.entities.BankAccount;
import com.jhsoft.sofbank.utils.enums.TypeAccount;

public interface IBankAccountFactory {
    BankAccount createAccount(String numberAccount, double balance, double rateInterest, TypeAccount typeAccount);
}
